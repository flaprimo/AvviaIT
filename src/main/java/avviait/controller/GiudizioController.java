package avviait.controller;

import avviait.model.Giudizio;
import avviait.model.GiudizioFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Named
public class GiudizioController {
    @Inject
    private GiudizioFacade giudizioFacade;
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private StartupperFacade startupperFacade;

    private Giudizio giudizio;

    private Long id;
    private String titolo;
    private String testo;
    private int voto;

    @PostConstruct
    private void initGiudizioControllerByRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Map reqParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        // check for giudizio in url (GET)
        try {
            id = Long.valueOf(req.getParameter("giudizio"));
        } catch (NumberFormatException e1) {

            // check for giudizio in html form
            try {
                id = Long.valueOf((String) reqParameterMap.get("id"));
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }

        if (id != null) {
            giudizio = giudizioFacade.getGiudizio(id);

            titolo = giudizio.getTitolo();
            testo = giudizio.getTesto();
            voto = giudizio.getVoto();
        }
    }

    public void createGiudizio() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        Long idGiudicato = (Long) flash.get("idStartupper");

        Startupper autore = startupperSessionController.getStartupper();
        Startupper giudicato = startupperFacade.getStartupper(idGiudicato);

        try {
            giudizioFacade.createGiudizio(voto, titolo, testo, autore, giudicato);

            flash.put("notification", "giudizio aggiunto con successo");
            flash.put("notificationType", "success");
        } catch (Exception e) {
            flash.put("notification", "errore: aggiunta giudizio fallita");
            flash.put("notificationType", "alert");
        }
    }

    public String updateGiudizio() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        if (giudizio != null) {
            if (giudizioFacade.isAutoreGiudizio(startupperSessionController.getStartupper(), giudizio)) {
                giudizio.setTitolo(titolo);
                giudizio.setTesto(testo);
                giudizio.setVoto(voto);

                giudizioFacade.updateGiudizio(giudizio);

                flash.put("notification", "giudizio aggiornato con successo");
                flash.put("notificationType", "success");
            } else {
                flash.put("notification", "errore: non puoi modificare un giudizio che non hai creato tu");
                flash.put("notificationType", "alert");
            }
        } else {
            flash.put("notification", "errore: giudizio da modificare non esistente");
            flash.put("notificationType", "alert");
        }

        return "success";
    }

    public String deleteGiudizio() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        if (giudizio != null) {
            if (giudizioFacade.isAutoreGiudizio(startupperSessionController.getStartupper(), giudizio)) {

                giudizioFacade.deleteGiudizio(id);
                flash.put("notification", "Giudizio cancellato con successo");
                flash.put("notificationType", "success");
            } else {
                flash.put("notification", "errore: non puoi cancellare un giudizio che non hai creato tu");
                flash.put("notificationType", "alert");
            }
        } else {
            flash.put("notification", "errore: giudizio da cancellare non esistente");
            flash.put("notificationType", "alert");
        }

        return "success";
    }

    /**
     * Getter & Setter
     * */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }
}