#!/bin/bash

# Configuration for AvviaIT EC2 instance
# (db is an external PostgreSQL Amazon AWS RDS instance)
#
# tested with Amazon AWS EC2 instance:
# "ubuntu/images/hvm-ssd/ubuntu-utopic-14.10-amd64-server-20150612 - ami-1240780f"

# INSTRUCTIONS
# to make this file executable run "chmod +x thisfile.sh"
# to run the tomee webserver, reboot EC2 instance
# remember to change db configuration accordingly to your deployment settings

# POSTGRESQL
# if you don't want to use AWS RDS and want to install postgresql:
# sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
# wget —quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
# sudo apt-get update
# sudo apt-get install postgresql
#
# sudo -u postgres psql
# -- give the following 2 lines below commands on psql console --
# CREATE DATABASE avviait;
# \q

# VARIABLES
EC2_INSTANCE_IP=52.28.153.175
GIT_REPO=https://github.com/flaprimo/AvviaIT.git
GIT_REPO_NAME=AvviaIT

# update and install softwares
sudo apt-get update && sudo apt-get dist-upgrade -y
sudo apt-get install nginx openjdk-8-jdk maven git ufw

# Install and configure ufw
sudo sed -i 's/^DEFAULT_FORWARD_POLICY="DROP"$/DEFAULT_FORWARD_POLICY="ACCEPT"/' /etc/default/ufw
sudo ufw allow ssh
sudo ufw allow 80/tcp
sudo ufw enable

# Configure Nginx
sudo rm -f /etc/nginx/sites-enabled/default
sudo echo "# TOMEE CONFIGURATION

server {
  listen          80;
  server_name     $EC2_INSTANCE_IP;
  root            /;

  location / {
        proxy_set_header X-Forwarded-Host \$host;
        proxy_set_header X-Forwarded-Server \$host;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_pass http://127.0.0.1:8080/;
  }
}" >> /etc/nginx/sites-enabled/default

# clone project
git clone $GIT_REPO

# update project and start tomee on boot
sudo echo "#!/bin/sh

### BEGIN INIT INFO
# Provides:       tomee
# Required-Start:    \$network
# Required-Stop:     \$network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: starts tomee web server
### END INIT INFO

PATH=/sbin:/bin:/usr/sbin:/usr/bin

start() {
 cd /home/ubuntu/$GIT_REPO
 git pull
 mvn clean package tomee:run
}

case \$1 in
  start) $1;;
  *) echo \"Run as \$0 <start>\"; exit 1;;
esac
" >> /etc/init.d/tomee
chmod 755 /etc/init.d/tomee
update-rc.d tomee defaults
