package avviait.model;

import javax.persistence.*;
import java.util.Calendar;

@Table(
        uniqueConstraints = @UniqueConstraint(columnNames={"autore_id", "giudicato_id"})
)
@Entity
@NamedQueries({
        @NamedQuery(name="findAllGiudizi", query="SELECT g FROM Giudizio g"),
        @NamedQuery(name="findGiudizioByAutoreGiudicato",
                query="SELECT g FROM Giudizio g WHERE g.autore = :autore AND g.giudicato = :giudicato")
})
public class Giudizio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int voto;

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

    public Giudizio() {
    }

    public Giudizio(int voto, String titolo, String testo, Calendar dataCreazione, Startupper autore, Startupper giudicato) {
        this.voto = voto;
        this.titolo = titolo;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
        this.giudicato = giudicato;
    }

    public Long getId() {
        return id;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
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

    public Startupper getAutore() {
        return autore;
    }

    public Startupper getGiudicato() {
        return giudicato;
    }

    @Override
    public String toString() {
        return "Giudizio [\n" +
                "id: " + id + "\n" +
                "voto: " + voto + "\n" +
                "titolo: " + titolo + "\n" +
                "testo: " + testo + "\n" +
                "dataCreazione: " + dataCreazione.getTime() + "\n" +
                "autore: " + autore.getId() + " " + autore.getNome() + "\n" +
                "giudicato: " + giudicato.getId() + " " + giudicato.getNome() + "\n" +
                "]\n";
    }
}
