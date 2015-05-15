package avviait.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllStartup", query = "SELECT s FROM Startup s"),
        @NamedQuery(name = "findAllAmministratori", query = "SELECT s FROM Startupper s " +
                "JOIN s.startupAmministrate sa WHERE sa = :startup"),
        @NamedQuery(name = "findAllMembri", query = "SELECT s FROM Startupper s " +
                "JOIN s.startupAttuali sa WHERE sa = :startup")
})
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String nome;

    @Column(nullable=false)
    private String descrizione;

    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private Calendar dataFondazione;

    @Column(nullable=false)
    private Boolean attiva;

    @ManyToMany(mappedBy = "startupAmministrate")
    private List<Startupper> amministratori;

    @ManyToMany(mappedBy = "startupAttuali")
    private List<Startupper> membri;

    @ManyToMany(mappedBy = "startupPassate")
    private List<Startupper> membrPassati;

    public Startup() {}

    public Startup(String nome, String descrizione , Calendar dataFondazione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataFondazione = dataFondazione;
        this.attiva = true;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Calendar getDataFondazione() {
        return dataFondazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Boolean isAttiva() {
        return attiva;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataFondazione(Calendar dataFondazione) {
        this.dataFondazione = dataFondazione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public void addAmministratore(Startupper startupper) {
        if (this.amministratori == null)
            this.amministratori = new LinkedList<Startupper>();
        this.amministratori.add(startupper);
    }

    public void addMembro(Startupper startupper) {
        if (this.membri == null)
            this.membri = new LinkedList<Startupper>();
        this.membri.add(startupper);
    }

    public void addMembriPassati(Startupper startupper) {
        //TODO
    }
}
