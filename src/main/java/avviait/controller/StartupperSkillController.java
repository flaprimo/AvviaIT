package avviait.controller;

import avviait.exceptions.AlreadyExists;
import avviait.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
@RequestScoped
public class StartupperSkillController {

    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private StartupperSkillFacade startupperSkillFacade;

    private String nomeSkill;

    private Long idVotoSkill;
    private boolean hasVoted;

    @PostConstruct
    private void initGiudizioController() {
        HttpServletRequest req =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            idVotoSkill = Long.valueOf(req.getParameter("startupperSkill"));
        } catch (NumberFormatException e) {
        }
        System.out.println("idVotoSkill="+idVotoSkill);
    }

    public String addSkill() {
        Skill s = skillFacade.getOrCreateSkillNamed(nomeSkill);
        Startupper startupper = startupperSessionController.getStartupper();
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        try {
            startupperFacade.addSkillAppresa(startupper, s);
            flash.put("notification", "Skill aggiunta");
            flash.put("notificationType", "success");
            return "success";

        } catch (AlreadyExists alreadyExists) {
            flash.put("notification", "Skill già aggiunta");
            flash.put("notificationType", "alert");
            return "failure";
        }
    }

    private Long idStartupperProfile;
    public String votaSkill() {
        StartupperSkill startupperSkill = startupperSkillFacade.getStartupperSkill(idVotoSkill);
        idStartupperProfile = startupperSkill.getStartupperProprietario().getId();
        startupperSkillFacade.vota(startupperSessionController.getStartupper(),
                startupperSkill.getStartupperProprietario(),
                startupperSkill.getSkillAssociata());
        return "selfRedirect";
    }


    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
    }

    public Long getIdStartupperProfile() {
        return idStartupperProfile;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public boolean hasVoted(Startupper startupper, StartupperSkill startupperSkill) {
        return startupperSkillFacade.hasVoted(startupper, startupperSkill);
    }
    public int contaRiscontri(StartupperSkill startupperSkill) {
        List<Startupper> votanti = startupperSkillFacade.getVotanti(startupperSkill);
        return votanti.size();
    }
}
