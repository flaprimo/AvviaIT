package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
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

    public Startup getStartup(Long id){
        Startup startup = null;
        try {
            startup = em.find(Startup.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getStartup fallito");
            e.printStackTrace();
        }
        return startup;
    }

    public List<Startup> getAllStartup(){
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
            query.setParameter("startup",startup);
            amministratori = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: getAmministratori fallito");
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
            System.out.println("ERRORE: getMembri fallito");
            e.printStackTrace();
        }
        return  membri;
    }

}
