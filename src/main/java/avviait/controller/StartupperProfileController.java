package avviait.controller;

import avviait.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Named
@RequestScoped
public class StartupperProfileController {
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private SkillFacade skillFacade;

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Calendar dataIscrizione;
    private String descrizione;
    private Boolean attivo;

    private List giudiziDati;
    private List giudiziRicevuti;

    private List<StartupperSkill> startupperSkills;

    private List<Skill> listaCompletaSkill;

    @PostConstruct
    private void initStartupper() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            id = Long.valueOf(req.getParameter("id"));

            // da usare per operazioni su startupper del profilo visualizzato
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("idStartupper", id);
        } catch (NumberFormatException e) {
            id = null;
        }

        Startupper startupper;

        if (id == null) {
            // initialize logged Startupper
            startupper = startupperSessionController.getStartupper();
            id = startupper.getId();
        } else {
            // initialize other Startupper
            startupper = startupperFacade.getStartupper(id);
        }

        if (startupper != null) {
            id = startupper.getId();
            nome = startupper.getNome();
            cognome = startupper.getCognome();
            email = startupper.getEmail();
            dataIscrizione = startupper.getDataIscrizione();
            descrizione = startupper.getDescrizione();
            attivo = startupper.getAttivo();
            giudiziDati = startupperFacade.getGiudiziDati(startupper);
            giudiziRicevuti = startupperFacade.getGiudiziRicevuti(startupper);
            startupperSkills = startupperFacade.getSkillApprese(startupper);
        }

        if (startupperSessionController.isLoggedStartupper(id)) {
            listaCompletaSkill = skillFacade.getAllSkillNotAcquired(startupper);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Calendar dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }

    public List getGiudiziDati() {
        return giudiziDati;
    }

    public List getGiudiziRicevuti() {
        return giudiziRicevuti;
    }

    public List getStartupperSkills() {
        return startupperSkills;
    }

    public List<Skill> getListaCompletaSkill() {
        return listaCompletaSkill;
    }
}
