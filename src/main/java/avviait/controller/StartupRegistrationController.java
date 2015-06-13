package avviait.controller;

import avviait.model.StartupFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Date;

@Named
public class StartupRegistrationController {

    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private StartupFacade startupFacade;
    @Inject
    private StartupperFacade startupperFacade;

    private String nome;
    private String descrizione;
    private Date data;
    private Startupper startupper;

    public String createStartup() {
        startupper = startupperSessionController.getStartupper();
        Calendar dataFondazione = Calendar.getInstance();
        dataFondazione.setTime(data);
        startupFacade.createStartup(nome, descrizione, dataFondazione, startupper);
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("notification", "Registrazione della startup avvenuta con successo");
        flash.put("notificationType", "success");
        return "success";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDescrizione(String descrzione) {
        this.descrizione = descrzione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
