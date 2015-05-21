package avviait.controller;

import avviait.model.Startupper;
import avviait.model.StartupperFacade;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;

@Named
@SessionScoped
public class StartupperController implements Serializable {
    @Inject
    private StartupperFacade startupperFacade;

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Calendar dataIscrizione;
    private String descrizione;
    private Boolean attivo;

    private Startupper startupper;

    private void initStartupper() {
        this.id = startupper.getId();
        this.nome = startupper.getNome();
        this.cognome = startupper.getCognome();
        this.email = startupper.getEmail();
        this.password = "";
        this.dataIscrizione = startupper.getDataIscrizione();
        this.descrizione = startupper.getDescrizione();
        this.attivo = startupper.getAttivo();
    }

    public String createStartupper() {
        if (startupperFacade.getStartupperByEmail(email) == null) {
            this.startupper = startupperFacade.createStartupper(nome, cognome, email, password);
            initStartupper();
            return "createStartupperSuccess";
        } else {
            return "createStartupperFailure";
        }
    }

    public String loginStartupper() {
        Startupper startupperAttempt = startupperFacade.getStartupperByEmail(email);
        if (startupperAttempt != null && startupperFacade.checkPassword(startupperAttempt, password)) {
            this.startupper = startupperAttempt;

            initStartupper();

            return "loginStartupperSuccess";
        } else {
            return "loginStartupperFailure";
        }
    }

    public String logoutStartupper() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logoutStartupperSuccess";
    }

    public String updateStartupper() {
        startupper.setEmail(email);
        if (password != null && !password.equals(""))
            startupperFacade.changePassword(startupper, password);
        startupper.setDescrizione(descrizione);
        startupper.setAttivo(attivo);

        startupperFacade.updateStartupper(startupper);
        return "updateStartupperSuccess";
    }

    /**
     * Getter & Setter
     * */
    public Long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Startupper getStartupper() {
        return startupper;
    }

    public void setStartupper(Startupper startupper) {
        this.startupper = startupper;
    }
}
