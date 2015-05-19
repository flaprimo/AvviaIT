package avviait.model;


import javax.persistence.*;
import java.util.Calendar;

@Entity
public class VotoSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String testo;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCreazione;

    @ManyToOne(cascade={CascadeType.ALL})
    private Startupper autore;

    @ManyToOne(cascade={CascadeType.ALL})
    private Startupper giudicato;

    @ManyToOne(cascade={CascadeType.ALL})
    private Skill skillAssociata;

    public VotoSkill() {
    }

    public VotoSkill(String titolo, String testo, Calendar dataCreazione, Startupper autore, Startupper giudicato,
                     Skill skillAssociata) {
        this.titolo = titolo;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
        this.giudicato = giudicato;
        this.skillAssociata = skillAssociata;
    }

    public Long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Calendar getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Startupper getAutore() {
        return autore;
    }

    public void setAutore(Startupper autore) {
        this.autore = autore;
    }

    public Startupper getGiudicato() {
        return giudicato;
    }

    public void setGiudicato(Startupper giudicato) {
        this.giudicato = giudicato;
    }

    public Skill getSkillAssociata() {
        return skillAssociata;
    }

    public void setSkillAssociata(Skill skillAssociata) {
        this.skillAssociata = skillAssociata;
    }

    @Override
    public String toString() {
        return "VotoSkill{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", testo='" + testo + '\'' +
                ", dataCreazione=" + dataCreazione +
                ", autore=" + autore +
                ", giudicato=" + giudicato +
                '}';
    }
}
