package avviait.controller;

import avviait.model.Skill;
import avviait.model.SkillFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class StartupperSkillController implements Serializable {

    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperLoginController startupperLoginController;

    private String nomeSkill;

    public String addSkill() {
        Skill s = skillFacade.getOrCreateSkillNamed(nomeSkill);
        Startupper startupper = startupperLoginController.getStartupper();
        startupperFacade.addSkillAppresa(startupper, s);
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("notification", "Skill aggiunta");
        flash.put("notificationType", "success");
        return "addSkillOK";
    }


    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
    }
}
