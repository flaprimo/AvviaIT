package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import avviait.helper.StringHashing;

@Stateless
public class StartupperFacade  implements Serializable {
    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public StartupperFacade() {
    }

    public Startupper createStartupper(String nome, String cognome, String email, String password) {
        Startupper startupper = null;

        String saltPassword = StringHashing.getSalt();
        String hashedPassword = StringHashing.getSecuredString(password, saltPassword);

        Calendar dataIscrizione = new GregorianCalendar();

        try {
            startupper = new Startupper(nome, cognome, email, hashedPassword, saltPassword, dataIscrizione);
            em.persist(startupper);
        } catch (Exception e) {
            System.out.println("ERRORE: Persistenza nuovo Startupper fallita");
            e.printStackTrace();
        }

        return startupper;
    }

    public Startupper getStartupper(Long id) {
        Startupper startupper = null;

        try {
            startupper = em.find(Startupper.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getStartupper fallito");
            e.printStackTrace();
        }

        return startupper;
    }

    public Startupper getStartupperByEmail(String email) {
        Startupper startupper;

        try {
            Query query = em.createNamedQuery("findStartupperByEmail").setParameter("email", email);
            startupper = (Startupper)query.getSingleResult();

        } catch (Exception e) {
            startupper = null;
        }

        return startupper;
    }

    @SuppressWarnings("unchecked")
    public List<Startupper> getAllStartupper() {
        List<Startupper> startupperList = null;

        try {
            Query query = em.createNamedQuery("findAllStartupper");
            startupperList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllStartupper\" fallita");
            e.printStackTrace();
        }

        return startupperList;
    }

    public String getNomeCompleto(Startupper startupper) {
        return startupper.getNome()+" "+startupper.getCognome();
    }

    public boolean checkPassword(Startupper startupper, String attemptPassword) {
        String storedPassword = startupper.getPassword();
        String storedSaltPassword = startupper.getSaltPassword();

        String hashedAttemptPassword = StringHashing.getSecuredString(attemptPassword, storedSaltPassword);

        return storedPassword.equals(hashedAttemptPassword);
    }

    public boolean changePassword(Startupper startupper, String password) {
        try {
            String saltPassword = StringHashing.getSalt();

            startupper.setPassword(StringHashing.getSecuredString(password, saltPassword));
            startupper.setSaltPassword(saltPassword);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateStartupper(Startupper startupper) {
        try {
            em.merge(startupper);
        } catch (Exception e) {
            System.out.println("ERRORE: Aggiornamento Giudizio fallito");
            e.printStackTrace();
        }
    }

    public void deleteStartupper(Long id) {
        try {
            Startupper startupper = em.find(Startupper.class, id);
            em.remove(startupper);
        } catch (Exception e) {
            System.out.println("ERRORE: Eliminazione Startupper fallita");
            e.printStackTrace();
        }
    }
}

