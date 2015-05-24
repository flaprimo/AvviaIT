package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class StartupperLoginController implements Serializable {
    @Inject
    private StartupperFacade startupperFacade;

    private String email;
    private String password;

    private Startupper startupper;

    public String loginStartupper() {
        Startupper startupperAttempt = startupperFacade.getStartupperByEmail(email);
        if (startupperAttempt != null && startupperFacade.checkPassword(startupperAttempt, password)) {
            this.startupper = startupperAttempt;

            return "success";
        } else {
            return "failure";
        }
    }

    public String logoutStartupper() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "success";
    }

    public boolean isStartupperLoggedIn() {
        return startupper != null;
    }

    public boolean isLoggedStartupper(Long id) {
        return startupper.getId().equals(id);
    }

    /**
     * Getter & Setter
     * */
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
