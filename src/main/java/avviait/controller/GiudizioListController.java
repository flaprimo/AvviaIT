package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
public class GiudizioListController {
    @Inject
    private StartupperLoginController startupperLoginController;
    @Inject
    private StartupperFacade startupperFacade;

    private List giudiziDati;
    private List giudiziRicevuti;

    @PostConstruct
    public void initListGiudizi() {
        Long id;
        Startupper startupper;

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            id = Long.valueOf(req.getParameter("id"));
            startupper = startupperFacade.getStartupper(id);
        } catch (NumberFormatException e) {
            startupper = startupperLoginController.getStartupper();
        }

        giudiziDati = startupperFacade.getGiudiziRicevuti(startupper);
        giudiziRicevuti = startupperFacade.getGiudiziDati(startupper);
    }

    public List getGiudiziRicevuti() {
        return giudiziDati;
    }

    public List getGiudiziDati() {
        return giudiziRicevuti;
    }
}
