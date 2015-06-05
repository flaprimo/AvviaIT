package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Stateless(name = "skillFacade")
public class SkillFacade implements Serializable {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public SkillFacade() {
    }

    public Skill createSkill(String nome){
        Skill skill = null;
        try {
            skill = new Skill(nome);
            em.persist(skill);
        } catch (Exception e) {
            System.out.println("ERRORE: Persistenza nuova Skill fallita");
            e.printStackTrace();
        }
        return skill;
    }

    public Skill getOrCreateSkillNamed(String nome) {
        Skill ret = null;
        Query query = em.createNamedQuery("getNamedSkill");
        query.setParameter("nome", nome);
        try {
            ret = (Skill) query.getSingleResult();
        } catch (NoResultException e) {
            ret = createSkill(nome);
        }
        return ret;
    }

    public Skill getSkill(Long id){
        Skill skill = null;
        try {
            skill = em.find(Skill.class, id);
        } catch (Exception e) {
            System.out.println("ERRORE: getStartup fallito");
            e.printStackTrace();
        }
        return skill;
    }

    public List<Skill> getAllSkill(){
        List<Skill> skillList = null;
        try {
            Query query = em.createNamedQuery("findAllSkill");
            skillList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findAllSkill\" fallita");
            e.printStackTrace();
        }
        return skillList;
    }

    public List<Skill> getAllSkillOfStartupper(Startupper startupper){
        List<Skill> skillList = null;
        try {
            Query query = em.createNamedQuery("findSkillOfStartupper");
            query.setParameter("id", startupper.getId());
            skillList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findSkillOfStartupper\" fallita");
            e.printStackTrace();
        }
        return skillList;
    }

    public List<Skill> getAllSkillForAnnuncio(AnnuncioMembri annuncio) {
        List<Skill> skillList = null;
        try {
            Query query = em.createNamedQuery("findSkillOfAnnuncio");
            query.setParameter("id", annuncio.getId());
            skillList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ERRORE: Query \"findSkillOfAnnuncio\" fallita");
            e.printStackTrace();
        }
        return skillList;
    }
}
