package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StartupperController {
    @Inject
    private StartupperFacade startupperFacade;

    private String nome;
    private String cognome;
    private String email;
    private String password;

    private Startupper startupper;

    public String createStartupper() {
        if (startupperFacade.getStartupperByEmail(email) != null) {
            this.startupper = startupperFacade.createStartupper(nome, cognome, email, password);
            return "createStartupperSuccess";
        } else {
            return "createStartupperFailure";
        }
    }

    public String loginStartupper() {
        startupper = startupperFacade.getStartupperByEmail(email);
        if (startupper != null && startupperFacade.checkPassword(startupper, password)) {

            return "loginStartupperSuccess";
        } else {
            return "loginStartupperFailure";
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Startupper getStartupper() {
        return startupper;
    }

    public void setStartupper(Startupper startupper) {
        this.startupper = startupper;
    }
}
