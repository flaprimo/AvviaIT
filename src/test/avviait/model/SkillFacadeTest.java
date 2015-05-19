package avviait.model;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SkillFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Skill.class, SkillFacade.class, StartupperFacade.class, Startupper.class,
                        AnnuncioMembriFacade.class, StartupFacade.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private SkillFacade skillFacade;
    @EJB
    private StartupperFacade startupperFacade;
    @EJB
    private AnnuncioMembriFacade annuncioMembriFacade;
    @EJB
    private StartupFacade startupFacade;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getIdSkillTest() {
        Skill skill = skillFacade.createSkill("HTML5");

        assertNotNull("ID should not be null", skill.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getSkillTest() {
        Skill skill = skillFacade.createSkill("CSS");

        Skill persistedSkill = skillFacade.getSkill(skill.getId());

        assertNotNull(persistedSkill);
        assertEquals(skill.getNome(), persistedSkill.getNome());
        assertNull(skill.getRichiestaDaAnnuncioMembri());
    }

    public static boolean searchSkill(List<Skill> list, Skill searchFor) {
        for (Skill test : list)
            if (test.getNome().equals(searchFor.getNome()))
                return true;
        return false;
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllSkillTest() {
        Skill[] skills = new Skill[] {
                skillFacade.createSkill("JavaScript"),
                skillFacade.createSkill("WebGL"),
                skillFacade.createSkill("jQuery")
        };

        List<Skill> skillList = skillFacade.getAllSkill();

        for(Skill test : skills) {
            assertTrue(searchSkill(skillList, test));
        }
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllSkillForStartupperTest() {
        Startupper user = startupperFacade.createStartupper("Mario", "Rossi", "mario.rossi@gmail.com", "abc");
        Skill[] skills = new Skill[] {
                skillFacade.createSkill("JPA"),
                skillFacade.createSkill("JSP"),
                skillFacade.createSkill("EJB")
        };

        user.getSkillApprese().add(skills[0]);
        user.getSkillApprese().add(skills[2]);

        List<Skill> returned = skillFacade.getAllSkillOfStartupper(user);

        assertTrue(searchSkill(returned, skills[0]));
        assertFalse(searchSkill(returned, skills[1]));
        assertTrue(searchSkill(returned, skills[2]));
    }

    private Startupper startupper;
    private Startup startup;
    private AnnuncioMembri annuncioMembri;
    public void setUpAnnuncio() {
        startupper = startupperFacade.createStartupper("Mark", "Zuckerberg", "mark.zuckerberg@fb.com", "wfb");
        startup = startupFacade.createStartup("Facebook", "Facebook ti aiuta a connetterti e" +
                        "rimanere in contatto con le persone della tua vita.", new GregorianCalendar(2004,2,4),
                startupper);
        annuncioMembri =
                annuncioMembriFacade.createAnnuncioMembri("Developer", "conoscenze LAMP", startup);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllSkillForAnnunciorTest() {
        setUpAnnuncio();

        Skill[] skills = new Skill[] {
                skillFacade.createSkill("PHP"),
                skillFacade.createSkill("MySQL"),
                skillFacade.createSkill("htaccess"),
                skillFacade.createSkill("Java")
        };

        annuncioMembri.getSkillRichieste().add(skills[0]);
        annuncioMembri.getSkillRichieste().add(skills[1]);
        annuncioMembri.getSkillRichieste().add(skills[2]);

        List<Skill> returned = skillFacade.getAllSkillForAnnuncio(annuncioMembri);

        assertTrue(searchSkill(returned, skills[0]));
        assertTrue(searchSkill(returned, skills[1]));
        assertTrue(searchSkill(returned, skills[2]));
        assertFalse(searchSkill(returned, skills[3]));
    }

}
