package avviait.controller;

import avviait.model.Startup;
import avviait.model.StartupFacade;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class StartupListController {
    @Inject
    private StartupFacade startupFacade;

    private List<Startup> startupList;

    @PostConstruct
    private void init() {
        startupList = startupFacade.getAllStartup();
    }

    public List<Startup> getStartupList() {
        return startupList;
    }

    public void setStartupList(List<Startup> startupList) {
        this.startupList = startupList;
    }
}
