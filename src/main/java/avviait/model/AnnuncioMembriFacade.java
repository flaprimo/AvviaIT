package avviait.model;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Stateless(name = "annuncioMembriFacade")
public class AnnuncioMembriFacade {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;
    @Inject
    private SkillFacade skillFacade;

    public AnnuncioMembriFacade() {
    }

    public AnnuncioMembri createAnnuncioMembri(String mansione, String descrizione, Startup autrice,
                                               List<String> skillRichieste){
        AnnuncioMembri annuncioMembri = null;
        List<Skill> skills = new LinkedList<Skill>();
        for(String nomeSkill : skillRichieste) {
            skills.add(skillFacade.getOrCreateSkillNamed(nomeSkill));
        }
        annuncioMembri = new AnnuncioMembri(mansione, descrizione, skills);
        Calendar data = Calendar.getInstance();
        data.setTime(new Date());
        annuncioMembri.setDataCreazione(data);
        autrice = em.merge(autrice);
        annuncioMembri.setAutrice(autrice);
        em.persist(annuncioMembri);
        em.merge(autrice);
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
