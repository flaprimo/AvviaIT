package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless(name = "votoSkillFacade")
public class VotoSkillFacade {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public VotoSkillFacade() {
    }

    public VotoSkill createVotoSkill(String titolo, String testo, Startupper autore, Startupper giudicato,
                                 Skill skillAssociata){
        VotoSkill voto = null;
        try {
            Calendar now = new GregorianCalendar();
            voto = new VotoSkill(titolo, testo, now, autore, giudicato, skillAssociata);
            em.persist(voto);
        } catch (Exception e) {
            System.out.println("ERRORE: Persistenza nuova Skill fallita");
            e.printStackTrace();
        }
        return voto;
    }


    public List<VotoSkill> getAllVotoSkillForAutore(Startupper autore){
        List<VotoSkill> ret = null;
        try {
            Query query = em.createNamedQuery("findAllVotoSkillForAutore");
            query.setParameter("id", autore.getId());
            ret = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllVotoSkillForAutore\" fallita");
            e.printStackTrace();
        }
        return ret;
    }

    public List<VotoSkill> getAllVotoSkillForGiudicato(Startupper giudicato) {
        List<VotoSkill> ret = null;
        try {
            Query query = em.createNamedQuery("findAllVotoSkillForGiudicato");
            query.setParameter("id", giudicato.getId());
            ret = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllVotoSkillForGiudicato\" fallita");
            e.printStackTrace();
        }
        return ret;
    }
}
