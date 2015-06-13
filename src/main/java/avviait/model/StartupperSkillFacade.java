package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless(name = "votoSkillFacade")
public class StartupperSkillFacade {

    @PersistenceContext(unitName = "avviait-db")
    private EntityManager em;

    public StartupperSkillFacade() {
    }

    public void updateStartupperSkillFacade(StartupperSkill startupperSkill) {
        em.merge(startupperSkill);
    }

    public StartupperSkill createStartupperSkill(Startupper proprietario, Skill skillAssociata) {
        StartupperSkill startupperSkill = null;
        Calendar now = new GregorianCalendar();
        startupperSkill = new StartupperSkill(now, proprietario, skillAssociata);
        em.persist(startupperSkill);
        return startupperSkill;
    }

    public StartupperSkill vota(Startupper da, Startupper a, Skill skillAssociata) {
        StartupperSkill startupperSkill = getStartupperSkill(a, skillAssociata);
        //startupperSkill.getStartupperVotanti().add(da);
        da.getSkillVotate().add(startupperSkill);
        updateStartupperSkillFacade(startupperSkill);
        em.merge(da);
        em.merge(a);
        return startupperSkill;
    }

    private StartupperSkill getStartupperSkill(Startupper da, Skill skillAssociata) {
        Query query = em.createNamedQuery("findStartupperSkillSingleResult");
        query.setParameter("startupper", da);
        query.setParameter("skill", skillAssociata);
        return (StartupperSkill) query.getSingleResult();
    }

    public List<StartupperSkill> getAllVotoSkillForAutore(Startupper autore){
        List<StartupperSkill> ret = null;
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

    public List<StartupperSkill> getAllVotoSkillForGiudicato(Startupper giudicato) {
        List<StartupperSkill> ret = null;
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
