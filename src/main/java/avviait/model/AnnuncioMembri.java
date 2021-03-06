package avviait.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQuery(name = "findAllAnnuncioMembri", query = "SELECT a FROM AnnuncioMembri a")
public class AnnuncioMembri {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String mansione;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCreazione;

    @ManyToOne
    private Startup autrice;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Skill> skillRichieste;

    public AnnuncioMembri() {
    }

    public AnnuncioMembri(String mansione, String descrizione, List<Skill> skillRichieste) {
        this.mansione = mansione;
        this.descrizione = descrizione;
        this.dataCreazione = new GregorianCalendar();
        this.skillRichieste = skillRichieste;
    }

    public Long getId() {
        return id;
    }

    public String getMansione() {
        return mansione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Calendar getDataCreazione() {
        return dataCreazione;
    }

    public Startup getAutrice() {
        return autrice;
    }

    public void setMansione(String mansione) {
        this.mansione = mansione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDataCreazione(Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public void setAutrice(Startup autrice) {
        this.autrice = autrice;
    }

    public List<Skill> getSkillRichieste() {
        return skillRichieste;
    }
}
