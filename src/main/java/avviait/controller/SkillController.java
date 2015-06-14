package avviait.controller;

import avviait.model.SkillFacade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SkillController {
    @Inject
    private SkillFacade skillFacade;
    @Inject
    private StartupperSessionController startupperSessionController;

    public List getAllSkillNotAcquired() {
        return skillFacade.getAllSkillNotAcquired(startupperSessionController.getStartupper());
    }
}
