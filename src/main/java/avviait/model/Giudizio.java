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
    private Integer voto;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String testo;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCreazione;

    @ManyToOne
    private Startupper autore;

    @ManyToOne
    private Startupper giudicato;

    public Giudizio() {
    }

    public Giudizio(Integer voto, String titolo, String testo, Calendar dataCreazione, Startupper autore, Startupper giudicato) {
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

    public void setVoto(Integer voto) {
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
        return "Giudizio{" +
                "id=" + id +
                ", voto=" + voto +
                ", titolo='" + titolo + '\'' +
                ", testo='" + testo + '\'' +
                ", dataCreazione=" + dataCreazione +
                ", autore=" + autore.getId() + " " + autore.getNome() +
                ", giudicato=" + autore.getId() + " " + giudicato.getNome() +
                '}';
    }
}
