package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Stateless(name = "startupFacade")
public class StartupFacade {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public StartupFacade() {
    }

    public Startup createStartup(String nome, String descrizione, Calendar dataFondazione,
                                 Startupper startupper){
        Startup startup = null;
        startupper = em.merge(startupper);
        try {
            startup = new Startup(nome,descrizione,dataFondazione);
            startup.getAmministratori().add(startupper);
            startup.getMembri().add(startupper);
            startupper.getStartupAmministrate().add(startup);
            startupper.getStartupAttuali().add(startup);
            em.persist(startup);
            em.merge(startupper);
        } catch (Exception e) {
            System.out.println("ERRORE: Persistenza nuova Startup fallita");
            e.printStackTrace();
        }
        return startup;
    }

    public Startup getStartup(Long id) {
        Startup startup = null;
        try {
            startup = em.find(Startup.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getStartup fallito");
            e.printStackTrace();
        }
        return startup;
    }

    public Startup getStartup(String nome) {
        Startup startup = null;
        try {
            Query query = em.createNamedQuery("findStartupByName").setParameter("nome", nome);
            startup = (Startup) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findStartupByName\" fallita");
            e.printStackTrace();
        }
        return startup;
    }

    public List<Startup> getAllStartup() {
        List<Startup> startupList = null;
        try {
            Query query = em.createNamedQuery("findAllStartup");
            startupList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllStartup\" fallita");
            e.printStackTrace();
        }
        return startupList;
    }

    public List<Startupper> getAmministratori(Startup startup) {
        List<Startupper> amministratori = null;
        try {
            Query query = em.createNamedQuery("findAllAmministratori");
            query.setParameter("startup", startup);
            amministratori = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllAmministratori\" fallita");
            e.printStackTrace();
        }
        return  amministratori;
    }

    public  List<Startupper> getMembri(Startup startup) {
        List<Startupper> membri = null;
        try {
            Query query = em.createNamedQuery("findAllMembri");
            query.setParameter("startup",startup);
            membri = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllMembri\" fallita");
            e.printStackTrace();
        }
        return  membri;
    }

    public List<Startupper> getMembriPassati(Startup startup) {
        List<Startupper> membriPassati = null;
        try {
            Query query = em.createNamedQuery("findAllMembriPassati");
            query.setParameter("startup", startup);
            membriPassati = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllMembriPassati\" fallita");
            e.printStackTrace();
        }
        return  membriPassati;
    }

    public void updateStartup(Startup startup) {
        try {
            em.merge(startup);
        } catch (Exception e ) {
            System.out.println("ERRORE: Aggiornamento Startup fallito.");
            e.printStackTrace();
        }
    }

    public boolean addMembro(Startup startup, Startupper startupper) {
        startup = em.merge(startup);
        startupper= em.merge(startupper);
        if(startup.getMembri().add(startupper) && startupper.getStartupAttuali().add(startup)) {
            em.merge(startup);
            em.merge(startupper);
            return true;
        }
        return false;
    }

    public boolean removeMembro(Startup startup, Startupper startupper) {
        startupper = em.merge(startupper);
        startup = em.merge(startup);
        if (startup.getMembri().remove(startupper) && startupper.getStartupAttuali().remove(startup) &&
                startup.getMembriPassati().add(startupper) && startupper.getStartupPassate().add(startup)) {
            startup.getMembriPassati().add(startupper);
            startupper.getStartupPassate().add(startup);
            em.merge(startup);
            em.merge(startupper);
            return true;
        }
        return false;
    }

    public boolean addAmministratore(Startup startup, Startupper startupper) {
        startup = em.merge(startup);
        startupper = em.merge(startupper);
        if (startup.getAmministratori().add(startupper) && startupper.getStartupAmministrate().add(startup)) {
            em.merge(startup);
            em.merge(startupper);
            return true;
        }
        return false;
    }

    public boolean removeAmministratore(Startup startup, Startupper startupper) {
        startup = em.merge(startup);
        startupper = em.merge(startupper);
        if (startup.getAmministratori().remove(startupper) && startupper.getStartupAmministrate().remove(startup)) {
            em.merge(startup);
            em.merge(startupper);
            return true;
        }
        return false;
    }
}
