package avviait.controller;

import avviait.model.Startup;
import avviait.model.StartupFacade;
import avviait.model.Startupper;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Named
public class StartupProfileController {

    @Inject
    private StartupFacade startupFacade;
    @Inject
    private StartupperSessionController startupperSessionController;

    private String nome;
    private String descrizione;
    private Date data;
    private Startup startup;
    private Boolean attiva;


    @PostConstruct
    public void init() {
        HttpServletRequest httpServletRequest = (HttpServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        startup = startupFacade.getStartup(httpServletRequest.getParameter("name"));
        if (startup != null) {
            nome = startup.getNome();
            descrizione = startup.getDescrizione();
            data = startup.getDataFondazione().getTime();
            attiva = startup.isAttiva();
        }
    }

    public boolean isLoggedStartupperAmministratore() {
        List<Startupper> amministratori = startupFacade.getAmministratori(startup);
        Startupper startupper = startupperSessionController.getStartupper();
        return amministratori.contains(startupper);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }


}
