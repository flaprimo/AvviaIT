package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless(name = "annuncioMembriFacade")
public class AnnuncioMembriFacade {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public AnnuncioMembriFacade() {
    }

    public AnnuncioMembri createAnnuncioMembri(String mansione, String descrizione, Startup autrice,
                                               List<Skill> skillRichieste){
        AnnuncioMembri annuncioMembri = null;
        try {
            annuncioMembri = new AnnuncioMembri(mansione, descrizione, skillRichieste);
            annuncioMembri.setAutrice(autrice);
            autrice.getAnnunci().add(annuncioMembri);
            em.persist(annuncioMembri);
            em.merge(autrice);
        } catch (Exception e) {
            System.out.println("ERRORE: Persistenza nuovo AnnuncioMembri fallito");
            e.printStackTrace();
        }
        return annuncioMembri;
    }

    public AnnuncioMembri getAnnuncioMembri(Long id){
        AnnuncioMembri annuncioMembri = null;
        try {
            annuncioMembri = em.find(AnnuncioMembri.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getAnnuncioMembri fallito");
            e.printStackTrace();
        }
        return annuncioMembri;
    }

    public List<AnnuncioMembri> getAllAnnuncioMembri(){
        List<AnnuncioMembri> annuncioMembriList = null;
        try {
            Query query = em.createNamedQuery("findAllAnnuncioMembri");
            annuncioMembriList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllAnnuncioMembri\" fallita");
            e.printStackTrace();
        }
        return annuncioMembriList;
    }

}
