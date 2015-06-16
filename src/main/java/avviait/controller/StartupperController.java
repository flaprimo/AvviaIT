package avviait.controller;

import avviait.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Named
@RequestScoped
public class StartupperController {
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperSessionController startupperSessionController;

    private Startupper startupper;

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Calendar dataIscrizione;
    private String plainPassword;
    private String descrizione;
    private Boolean attivo;

    private List<Giudizio> giudiziDati;
    private List<Giudizio> giudiziRicevuti;
    private List<StartupperSkill> skill;
    private List<Startup> startupAmministrate;
    private List<Startup> startupAttuali;
    private List<Startup> startupPassate;

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void initStartupperController() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        // check for startupper in request
        try {
            id = Long.valueOf(req.getParameter("startupper"));
        } catch (NumberFormatException e) {

            // load loggedin startupper
            if (startupperSessionController.isStartupperLoggedIn()) {
                id = startupperSessionController.getStartupper().getId();
            }
        }

        if (id != null) {
            startupper = startupperFacade.getStartupper(id);

            nome = startupper.getNome();
            cognome = startupper.getCognome();
            email = startupper.getEmail();
            dataIscrizione = startupper.getDataIscrizione();
            descrizione = startupper.getDescrizione();
            attivo = startupper.getAttivo();
            giudiziDati = startupperFacade.getGiudiziDati(startupper);
            giudiziRicevuti = startupperFacade.getGiudiziRicevuti(startupper);
            skill = startupperFacade.getSkillApprese(startupper);
            startupAmministrate = startupperFacade.getStartupAmministrate(startupper);
            startupAttuali = startupperFacade.getStartupAttuali(startupper);
            startupPassate = startupperFacade.getStartupPassate(startupper);

            // da usare per operazioni su startupper del profilo visualizzato
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("idStartupper", id);
        }
    }

    public String createStartupper() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        // check if startupper wih same email does exists
        if (startupperFacade.getStartupperByEmail(email) == null) {
            startupperSessionController.setStartupper(startupperFacade.createStartupper(nome, cognome, email, plainPassword));

            flash.put("notification", "registrazione avvenuta con successo");
            flash.put("notificationType", "success");

            return "success";
        } else {
            flash.put("notification", "errore: esiste già un utente con questa email");
            flash.put("notificationType", "alert");

            return "failure";
        }
    }

    public String updateStartupper() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        startupper = startupperSessionController.getStartupper();
        Startupper startupperWithSameEmail = startupperFacade.getStartupperByEmail(email);

        if (startupperWithSameEmail == null ||
                startupperWithSameEmail.getId().equals(startupperSessionController.getStartupper().getId())) {
            startupper.setEmail(email);
            startupper.setDescrizione(descrizione);
            startupper.setAttivo(attivo);
            if (plainPassword != null && !plainPassword.equals(""))
                startupperFacade.changePassword(startupper, plainPassword);

            startupperFacade.updateStartupper(startupper);

            // add success notification for Profile page
            flash.put("notification", "profilo aggiornato con successo");
            flash.put("notificationType", "success");

            return "success";
        } else {
            // add failure notification for Profile page
            flash.put("notification", "errore: un utente con questa email è già presente");
            flash.put("notificationType", "alert");

            return "failure";
        }
    }

    public List getStartupperList() {
        return startupperFacade.getAllStartupper();
    }

    /**
     * Getter & Setter
     * */
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Calendar dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }

    public List<Giudizio> getGiudiziDati() {
        return giudiziDati;
    }

    public void setGiudiziDati(List<Giudizio> giudiziDati) {
        this.giudiziDati = giudiziDati;
    }

    public List<Giudizio> getGiudiziRicevuti() {
        return giudiziRicevuti;
    }

    public void setGiudiziRicevuti(List<Giudizio> giudiziRicevuti) {
        this.giudiziRicevuti = giudiziRicevuti;
    }

    public List<StartupperSkill> getSkill() {
        return skill;
    }

    public void setSkill(List<StartupperSkill> skill) {
        this.skill = skill;
    }

    public List<Startup> getStartupAmministrate() {
        return startupAmministrate;
    }

    public void setStartupAmministrate(List<Startup> startupAmministrate) {
        this.startupAmministrate = startupAmministrate;
    }

    public List<Startup> getStartupAttuali() {
        return startupAttuali;
    }

    public void setStartupAttuali(List<Startup> startupAttuali) {
        this.startupAttuali = startupAttuali;
    }

    public List<Startup> getStartupPassate() {
        return startupPassate;
    }

    public void setStartupPassate(List<Startup> startupPassate) {
        this.startupPassate = startupPassate;
    }
}
