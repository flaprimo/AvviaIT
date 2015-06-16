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
public class AnnuncioMembriController {
    @Inject
    private StartupController startupController;
    @Inject
    private AnnuncioMembriFacade annuncioMembriFacade;
    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupFacade startupFacade;


    private Long id;
    private String mansione;
    private String descrizione;
    private Calendar dataCreazione;
    private Startup autrice;
    private List<Skill> skillRichieste;
    private AnnuncioMembri annuncioMembri;
    private List<Skill> allSkills;
    private List<AnnuncioMembri> allAnnunci;

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            id = Long.valueOf(request.getParameter("id"));
            annuncioMembri = annuncioMembriFacade.getAnnuncioMembri(id);
            mansione = annuncioMembri.getMansione();
            descrizione = annuncioMembri.getDescrizione();
            dataCreazione = annuncioMembri.getDataCreazione();
            autrice = annuncioMembri.getAutrice();
            skillRichieste = annuncioMembri.getSkillRichieste();

        } catch (Exception e) {}
        if (request.getParameter("name") != null) {
            allSkills = skillFacade.getAllSkill();
            autrice = startupFacade.getStartup(request.getParameter("name"));
        }
        allAnnunci = annuncioMembriFacade.getAllAnnuncioMembri();
    }

    public String createAnnuncio() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        annuncioMembri = annuncioMembriFacade.createAnnuncioMembri(mansione, descrizione, autrice, skillRichieste);
        flash.put("notification", "Annuncio pubblicato");
        flash.put("notificationType", "success");
        return "done";
    }

    public String getMansione() {
        return mansione;
    }

    public void setMansione(String mansione) {
        this.mansione = mansione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Calendar getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Startup getAutrice() {
        return autrice;
    }

    public void setAutrice(Startup autrice) {
        this.autrice = autrice;
    }

    public List<Skill> getSkillRichieste() {
        return skillRichieste;
    }

    public void setSkillRichieste(List<Skill> skillRichieste) {
        this.skillRichieste = skillRichieste;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnnuncioMembri getAnnuncioMembri() {
        return annuncioMembri;
    }

    public void setAnnuncioMembri(AnnuncioMembri annuncioMembri) {
        this.annuncioMembri = annuncioMembri;
    }

    public List<Skill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<Skill> allSkills) {
        this.allSkills = allSkills;
    }

    public List<AnnuncioMembri> getAllAnnunci() {
        return allAnnunci;
    }

    public void setAllAnnunci(List<AnnuncioMembri> allAnnunci) {
        this.allAnnunci = allAnnunci;
    }
}
