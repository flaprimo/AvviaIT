package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Named
public class StartupperProfileController {
    @Inject
    private StartupperFacade startupperFacade;
    @Inject
    private StartupperLoginController startupperLoginController;

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Calendar dataIscrizione;
    private String descrizione;
    private Boolean attivo;

    @PostConstruct
    public void initStartupper() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = null;
        }

        Startupper startupper;

        if (id == null) {
            // initialize logged Startupper
            startupper = startupperLoginController.getStartupper();
        } else {
            // initialize other Startupper
            startupper = startupperFacade.getStartupper(id);
        }

        if (startupper != null) {
            id = startupper.getId();
            nome = startupper.getNome();
            cognome = startupper.getCognome();
            email = startupper.getEmail();
            dataIscrizione = startupper.getDataIscrizione();
            descrizione = startupper.getDescrizione();
            attivo = startupper.getAttivo();
        }
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
}