package avviait.controller;

import avviait.model.Skill;
import avviait.model.SkillFacade;
import avviait.model.StartupperSkill;
import avviait.model.StartupperSkillFacade;

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
public class SkillController {
    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupperSkillFacade startupperSkillFacade;
    @Inject
    private StartupperSessionController startupperSessionController;

    public List getAllSkillNotAcquired() {
        return skillFacade.getAllSkillNotAcquired(startupperSessionController.getStartupper());
    }

    private Long idSkill;

    private Skill skill;

    @PostConstruct
    private void initSkillController() {
        HttpServletRequest req =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            idSkill = Long.valueOf(req.getParameter("skill"));
            skill = skillFacade.getSkill(idSkill);
        } catch (NumberFormatException e) {
        }
    }

    public String deleteSkill() {
        System.out.println("deleteSkill:"+skill);
        startupperSkillFacade.deleteStartupperSkill(startupperSessionController.getStartupper(), skill);

        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("notification", "Skill rimossa");
        flash.put("notificationType", "success");

        return "MyProfile";
    }

    public Skill getSkill() {
        return skill;
    }

    public Long getIdSkill() {
        return idSkill;
    }
}
