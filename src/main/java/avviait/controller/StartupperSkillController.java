package avviait.controller;

import avviait.exceptions.AlreadyExists;
import avviait.model.Skill;
import avviait.model.SkillFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import java.io.Serializable;

@Named
@SessionScoped
public class StartupperSkillController implements Serializable {

    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperSessionController startupperSessionController;

    private String nomeSkill;

    public String addSkill() {
        Skill s = skillFacade.getOrCreateSkillNamed(nomeSkill);
        Startupper startupper = startupperSessionController.getStartupper();
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        try {
            startupperFacade.addSkillAppresa(startupper, s);
            flash.put("notification", "Skill aggiunta");
            flash.put("notificationType", "success");
            return "addSkillOK";

        } catch (AlreadyExists alreadyExists) {
            flash.put("notification", "Skill già aggiunta");
            flash.put("notificationType", "alert");
            return "addSkillFAIL";
        }
    }


    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
    }
}
