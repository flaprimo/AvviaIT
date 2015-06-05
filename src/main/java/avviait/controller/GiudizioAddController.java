package avviait.controller;

import avviait.model.Giudizio;
import avviait.model.GiudizioFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GiudizioAddController {
    @Inject
    private GiudizioFacade giudizioFacade;
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperLoginController startupperLoginController;
    @Inject
    private StartupperProfileController startupperProfileController;

    private int voto;
    private String titolo;
    private String testo;

    public void createGiudizio() {
        Startupper autore = startupperLoginController.getStartupper();
        Startupper giudicato = startupperFacade.getStartupper(startupperProfileController.getId());

        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        Giudizio giudizio = giudizioFacade.createGiudizio(voto, titolo, testo, autore, giudicato);

        if (giudizio != null) {
            // add success notification for Profile page
            flash.put("notification", "giudizio aggiunto con successo");
            flash.put("notificationType", "success");
        } else {
            // add success notification for Profile page
            flash.put("notification", "errore: aggiunta giudizio fallita");
            flash.put("notificationType", "alert");
        }
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
