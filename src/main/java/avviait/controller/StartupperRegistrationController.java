package avviait.controller;

import avviait.model.StartupperFacade;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StartupperRegistrationController {
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperLoginController startupperLoginController;

    private String nome;
    private String cognome;
    private String email;
    private String password;

    public String createStartupper() {
        if (startupperFacade.getStartupperByEmail(email) == null) {
            startupperLoginController.setStartupper(startupperFacade.createStartupper(nome, cognome, email, password));

            // add notification for Profile page
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("notification", "registrazione avvenuta con successo");
            flash.put("notificationType", "success");

            return "success";
        } else {
            return "failure";
        }
    }

    /**
     * Getter & Setter
     * */
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
}
