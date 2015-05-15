package avviait.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    @Temporal(TemporalType.TIME)
    private Calendar dataCreazione;

    public AnnuncioMembri() {
    }

    public AnnuncioMembri(String mansione, String descrizione) {
        this.mansione = mansione;
        this.descrizione = descrizione;
        this.dataCreazione = new GregorianCalendar();
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

    public void setMansione(String mansione) {
        this.mansione = mansione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDataCreazione(Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

}
