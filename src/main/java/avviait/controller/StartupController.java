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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
public class StartupController {
    @Inject
    private StartupperSessionController startupperSessionController;
    @Inject
    private StartupFacade startupFacade;

    private Long id;
    private String nome;
    private String descrizione;
    private Date dataFondazione;
    private Boolean attiva;
    private Startup startup;

    @PostConstruct
    private void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            startup = startupFacade.getStartup(request.getParameter("name"));
            nome = startup.getNome();
            descrizione = startup.getDescrizione();
            dataFondazione = startup.getDataFondazione().getTime();
            attiva = startup.isAttiva();
        } catch (NullPointerException e) {
        }
    }

    public String createStartup() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (startupFacade.getStartup(nome) == null) {
            Startupper startupper = startupperSessionController.getStartupper();
            Calendar data = Calendar.getInstance();
            data.setTime(dataFondazione);
            startupFacade.createStartup(nome, descrizione, data, startupper);
            flash.put("notification", "Startup registrata con successo");
            flash.put("notificationType", "success");
            return "success";
        }
        else {
            flash.put("notification", "Esiste già una Startup con questo nome");
            flash.put("notificationType", "alert");
            return "failure";
        }
    }

    public boolean isLoggedStartupperAmministratore() {
        List<Startupper> amministratori = startupFacade.getAmministratori(startup);
        Startupper startupper = startupperSessionController.getStartupper();
        return amministratori.contains(startupper);
    }

    public String update() {
        startup.setDescrizione(descrizione);
        startup.setAttiva(attiva);
        startupFacade.updateStartup(startup);
        return "success";
    }

    public List<Startup> getStartupsList() {
        return startupFacade.getAllStartup();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDataFondazione() {
        return dataFondazione;
    }

    public void setDataFondazione(Date dataFondazione) {
        this.dataFondazione = dataFondazione;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }
}
