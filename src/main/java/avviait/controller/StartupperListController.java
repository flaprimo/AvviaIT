package avviait.controller;

import avviait.model.StartupperFacade;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class StartupperListController {
    @Inject
    private StartupperFacade startupperFacade;

    public List getStartupperList() {
        return startupperFacade.getAllStartupper();
    }
}
