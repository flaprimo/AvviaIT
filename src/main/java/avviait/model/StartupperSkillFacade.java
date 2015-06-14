package avviait.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless
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

    public StartupperSkill getStartupperSkill(Long id) {
        StartupperSkill startupper = null;
        startupper = em.find(StartupperSkill.class, id);
        return startupper;
    }

    public StartupperSkill vota(Startupper da, Startupper a, Skill skillAssociata) {
        StartupperSkill startupperSkill = getStartupperSkill(a, skillAssociata);
        startupperSkill.getStartupperVotanti().add(da);
        //da.getSkillVotate().add(startupperSkill);
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

    public boolean hasVoted(Startupper startupper, StartupperSkill startupperSkill) {
        startupperSkill = getStartupperSkill(startupperSkill.getId());
        return startupperSkill.getStartupperVotanti().contains(startupper);
    }
    public List<Startupper> getVotanti(StartupperSkill startupperSkill) {
        startupperSkill = getStartupperSkill(startupperSkill.getId());
        return startupperSkill.getStartupperVotanti();
    }
}
