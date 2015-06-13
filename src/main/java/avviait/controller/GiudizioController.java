package avviait.controller;

import avviait.model.Giudizio;
import avviait.model.GiudizioFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
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
    private Integer voto;

    @PostConstruct
    private void initGiudizioController() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        // check for giudizio in request
        try {
            id = Long.valueOf(req.getParameter("giudizio"));

            giudizio = giudizioFacade.getGiudizio(id);

            titolo = giudizio.getTitolo();
            testo = giudizio.getTesto();
            voto = giudizio.getVoto();
        } catch (NumberFormatException e) {
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

    public Integer getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }
}