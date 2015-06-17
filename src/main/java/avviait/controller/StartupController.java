package avviait.controller;

import avviait.model.Startup;
import avviait.model.StartupFacade;
import avviait.model.Startupper;
import avviait.model.StartupperFacade;

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
    @Inject
    private StartupperFacade startupperFacade;

    private Long id;
    private String nome;
    private String descrizione;
    private Date dataFondazione;
    private Boolean attiva;
    private Startup startup;
    private List<Startupper> membri;
    private List<Startupper> membriPassati;
    private List<Startupper> amministratori;
    private String emailStartupper;

    @PostConstruct
    private void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getParameter("name") != null) {
            startup = startupFacade.getStartup(request.getParameter("name"));
            id = startup.getId();
            nome = startup.getNome();
            descrizione = startup.getDescrizione();
            dataFondazione = startup.getDataFondazione().getTime();
            attiva = startup.getAttiva();
            membri = startupFacade.getMembri(startup);
            membriPassati = startupFacade.getMembriPassati(startup);
            amministratori = startupFacade.getAmministratori(startup);
        }
    }

    public String createStartup() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (startupFacade.getStartup(nome) == null) {
            Startupper startupper = startupperSessionController.getStartupper();
            Calendar data = Calendar.getInstance();
            data.setTime(dataFondazione);
            startup = startupFacade.createStartup(nome, descrizione, data, startupper);
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

    public String addMembro() {
        Startupper startupper = startupperFacade.getStartupperByEmail(emailStartupper);
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (startupper != null ) {
            if (!membri.contains(startupper) && startupFacade.addMembro(startup, startupper)) {
                flash.put("notification", startupperFacade.getNomeCompleto(startupper) + " aggiunto come membro");
                flash.put("notificationType", "success");
            } else {
                flash.put("notification", startupperFacade.getNomeCompleto(startupper) + " è già membro");
                flash.put("notificationType", "alert");
            }
        } else {
            flash.put("notification", "Nessun utente registrato con " + emailStartupper);
            flash.put("notificationType", "alert");
        }
        return "done";
    }

    public String removeMembro() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        try {
            Long idStartupper = Long.valueOf(req.getParameter("startupper"));
            Long idStartup = Long.valueOf(req.getParameter("startup"));

            Startupper startupper = startupperFacade.getStartupper(idStartupper);
            startup = startupFacade.getStartup(idStartup);

            List<Startupper> membri = startupFacade.getMembri(startup);
            System.out.println("membri:"+membri);

            if(startupFacade.removeMembro(startup, startupper)) {
                flash.put("notification", startupperFacade.getNomeCompleto(startupper) + " rimosso da "
                        + startup.getNome());
                flash.put("notificationType", "success");
            } else {
                flash.put("notification", "Errore: lo startupper non è stato rimosso");
                flash.put("notificationType", "alert");
            }
        } catch (NumberFormatException e) {
            flash.put("notification", "Errore sconosciuto");
            flash.put("notificationType", "alert");
        }

        return "done";
    }

    public String addAmministratore() {
        Startupper startupper = startupperFacade.getStartupperByEmail(emailStartupper);
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (membri.contains(startupper)) {
            if (!amministratori.contains(startupper)) {
                if (startupFacade.addAmministratore(startup, startupper)) {
                    flash.put("notification", startupperFacade.getNomeCompleto(startupper) + " è ora " +
                            "amministratore di " + nome);
                    flash.put("notificationType", "success");
                } else {
                    flash.put("notification", "Errore: non è stato possibile aggiungere " +
                            startupperFacade.getNomeCompleto(startupper) + " come amministratore");
                    flash.put("notificationType", "alert");
                }
            } else {
                flash.put("notification", startupperFacade.getNomeCompleto(startupper) +
                        " è già amministratore di " + nome);
                flash.put("notificationType", "alert");
            }
        } else {
            flash.put("notification", startupperFacade.getNomeCompleto(startupper) +
                    " non è membro di " + nome);
            flash.put("notificationType", "alert");
        }
        return "done";
    }

    public String removeAmministratore() {
        Startupper startupper = startupperFacade.getStartupperByEmail(emailStartupper);
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (amministratori.size() > 1) {
            if (amministratori.contains(startupper)) {
                if (startupFacade.removeAmministratore(startup, startupper)) {
                    flash.put("notification", startupperFacade.getNomeCompleto(startupper) +
                            " non è più amministratore di " + nome);
                    flash.put("notificationType", "success");
                } else {
                    flash.put("notification", "Errore: non è stato possibile rimuovere " +
                            startupperFacade.getNomeCompleto(startupper) + " dal ruolo di amministratore");
                    flash.put("notificationType", "alert");
                }
            } else {
                flash.put("notification", startupperFacade.getNomeCompleto(startupper) +
                        " non è amministratore di " + nome);
                flash.put("notificationType", "alert");
            }
        } else {
            flash.put("notification", "Sei l'ultimo amministratore rimasto, non puoi rimuoverti.");
            flash.put("notificationType", "alert");
        }
        return "done";
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

    public String getEmailStartupper() {
        return emailStartupper;
    }

    public void setEmailStartupper(String emailStartupper) {
        this.emailStartupper = emailStartupper;
    }

    public List<Startupper> getMembri() {
        return membri;
    }

    public void setMembri(List<Startupper> membri) {
        this.membri = membri;
    }

    public List<Startupper> getAmministratori() {
        return amministratori;
    }

    public void setAmministratori(List<Startupper> amministratori) {
        this.amministratori = amministratori;
    }

    public List<Startupper> getMembriPassati() {
        return membriPassati;
    }
}
