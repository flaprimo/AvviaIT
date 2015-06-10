package avviait.model;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Stateless
public class GiudizioFacade {
    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    @Inject
    StartupperFacade startupperFacade;

    public GiudizioFacade() {
    }

    public Giudizio createGiudizio(int voto, String titolo, String testo, Startupper autore, Startupper giudicato)
            throws PersistenceException {
        Calendar dataCreazione = new GregorianCalendar();

        Giudizio giudizio = new Giudizio(voto, titolo, testo, dataCreazione, autore, giudicato);

        //attach entities to Entity Manager
        autore = em.merge(autore);
        giudicato = em.merge(giudicato);

        autore.getGiudiziDati().add(giudizio);
        giudicato.getGiudiziRicevuti().add(giudizio);

        em.persist(giudizio);
        em.flush();

        return giudizio;
    }

    public Giudizio getGiudizio(Long id) {
        Giudizio giudizio = null;

        try {
            giudizio = em.find(Giudizio.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getGiudizio fallito");
            e.printStackTrace();
        }

        return giudizio;
    }

    public Giudizio getGiudizioByAutoreGiudicato(Startupper autore, Startupper giudicato) {
        Giudizio giudizio;

        try {
            Query query = em.createNamedQuery("findGiudizioByAutoreGiudicato")
                    .setParameter("autore", autore)
                    .setParameter("giudicato", giudicato);
            giudizio = (Giudizio)query.getSingleResult();

        } catch (Exception e) {
            giudizio = null;
        }

        return giudizio;
    }

    public void updateGiudizio(Giudizio giudizio) {
        try {
            em.merge(giudizio);
        } catch (Exception e) {
            System.out.println("ERRORE: Aggiornamento Giudizio fallito");
            e.printStackTrace();
        }
    }

    public void deleteGiudizio(Long id) {
        try {
            Giudizio giudizio = em.find(Giudizio.class, id);
            em.remove(giudizio);
        } catch (Exception e) {
            System.out.println("ERRORE: Eliminazione Giudizio fallita");
            e.printStackTrace();
        }
    }
}
