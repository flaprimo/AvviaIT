package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StartupperSettingsController {
    @Inject
    private StartupperLoginController startupperLoginController;
    @Inject
    private StartupperFacade startupperFacade;

    private String email;
    private String descrizione;
    private String password;
    private Boolean attivo;

    @PostConstruct
    private void initStartupper() {
        Startupper startupper = startupperLoginController.getStartupper();

        email = startupper.getEmail();
        descrizione = startupper.getDescrizione();
        attivo = startupper.getAttivo();
    }

    public String updateStartupper() {
        Startupper startupper = startupperLoginController.getStartupper();

        startupper.setEmail(email);
        if (password != null && !password.equals(""))
            startupperFacade.changePassword(startupper, password);
        startupper.setDescrizione(descrizione);
        startupper.setAttivo(attivo);

        startupperFacade.updateStartupper(startupper);
        return "success";
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }
}
