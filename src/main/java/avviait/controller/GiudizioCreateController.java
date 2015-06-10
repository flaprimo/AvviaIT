package avviait.controller;

import avviait.model.GiudizioFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GiudizioCreateController {
    @Inject
    private GiudizioFacade giudizioFacade;
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private StartupperProfileController startupperProfileController;

    private int voto;
    private String titolo;
    private String testo;

    public void createGiudizio() {
        Long id;

        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        id = (Long) flash.get("idStartupper");

        Startupper autore = startupperSessionController.getStartupper();
        Startupper giudicato = startupperFacade.getStartupper(id);

        try {
            giudizioFacade.createGiudizio(voto, titolo, testo, autore, giudicato);

            flash.put("notification", "giudizio aggiunto con successo");
            flash.put("notificationType", "success");
        } catch (Exception e) {
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
