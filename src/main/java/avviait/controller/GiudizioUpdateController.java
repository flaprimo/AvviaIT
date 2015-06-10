package avviait.controller;

import avviait.model.Giudizio;
import avviait.model.GiudizioFacade;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class GiudizioUpdateController {
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private GiudizioFacade giudizioFacade;

    private Long id;
    private String titolo;
    private String testo;
    private int voto;

    @PostConstruct
    private void init() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Long id;

        try {
            id = Long.valueOf(req.getParameter("id"));
            Giudizio giudizio = giudizioFacade.getGiudizio(id);

            titolo = giudizio.getTitolo();
            testo = giudizio.getTesto();
            voto = giudizio.getVoto();

            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("idGiudizio", id);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public String updateGiudizio() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        String idString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        Long id;

        try {
            id = Long.valueOf(idString);
            Giudizio giudizio = giudizioFacade.getGiudizio(id);

            if (giudizio != null &&
                    giudizioFacade.isAutoreGiudizio(startupperSessionController.getStartupper(), giudizio)) {

                if (id != null) {
                    giudizio.setTitolo(titolo);
                    giudizio.setTesto(testo);
                    giudizio.setVoto(voto);

                    giudizioFacade.updateGiudizio(giudizio);

                    flash.put("notification", "giudizio aggiornato con successo");
                    flash.put("notificationType", "success");
                } else {
                    flash.put("notification", "errore: fallito aggiornamento giudizio");
                    flash.put("notificationType", "alert");
                }
            } else {
                flash.put("notification", "errore: Non puoi modificare un giudizio che non hai creato tu");
                flash.put("notificationType", "alert");
            }
        } catch (NumberFormatException e) {
            flash.put("notification", "Cancellazione giudizio fallita");
            flash.put("notificationType", "alert");
        }



        return "success";
    }

    /**
     * Getter & Setter
     * */
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

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }
}
