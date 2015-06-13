package avviait.controller;

import avviait.model.Startup;
import avviait.model.StartupFacade;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class StartupSettingsController {
    @Inject
    private StartupFacade startupFacade;
    @Inject
    private StartupProfileController startupProfileController;
    @Inject
    private StartupperSessionController startupperSessionController;

    private String descrizione;
    private Boolean attiva;
    private Startup startup;

    @PostConstruct
    public void init() {
        HttpServletRequest httpServletRequest = (HttpServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        startup = startupFacade.getStartup(httpServletRequest.getParameter("name"));
        descrizione = startup.getDescrizione();
        attiva = startup.isAttiva();
    }

    public String update() {
        startup.setDescrizione(descrizione);
        startup.setAttiva(attiva);
        startupFacade.updateStartup(startup);
        return "success";
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }
}
