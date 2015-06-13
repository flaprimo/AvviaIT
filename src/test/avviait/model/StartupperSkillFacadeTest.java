package avviait.model;

import avviait.exceptions.AlreadyExists;
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

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class StartupperSkillFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(StartupperSkill.class, Startupper.class, StartupperSkillFacade.class, Skill.class,
                        SkillFacade.class, StartupperFacade.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private StartupperSkillFacade startupperSkillFacade;
    @EJB
    private StartupperFacade startupperFacade;
    @EJB
    private SkillFacade skillFacade;

    private Startupper startupper1;
    private Startupper startupper2;
    private Startupper startupper3;
    private Skill skill1;
    private Skill skill2;
    private Skill skill3;
    public void setUp() {
        startupper1 = startupperFacade.createStartupper("Alessandro", "Arcangeli", "alessandro@arcangeli.it", "asd");
        startupper2 = startupperFacade.createStartupper("Mario", "Rossi", "mario.rossi@gmail.com", "qwerty");
        startupper3 = startupperFacade.createStartupper("Luigi", "Verdi", "luigi.verdi@gmail.com", "ytrewq");
        skill1 = skillFacade.createSkill("Unity");
        skill2 = skillFacade.createSkill("Ogre3d");
        skill3 = skillFacade.createSkill("Dark Basic");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllSkillVotate() throws AlreadyExists {
        setUp();

        startupperFacade.addSkillAppresa(startupper2, skill1);
        startupperFacade.addSkillAppresa(startupper3, skill2);

        StartupperSkill strSkill1 = startupperSkillFacade.vota(startupper1, startupper2, skill1);
        StartupperSkill strSkill2 = startupperSkillFacade.vota(startupper1, startupper3, skill2);
        StartupperSkill strSkill3 = startupperSkillFacade.vota(startupper2, startupper3, skill2);

        List<StartupperSkill> lista;

        lista = startupperFacade.getSkillVotate(startupper1);
        assertTrue(lista.contains(strSkill1));
        assertTrue(lista.contains(strSkill2));
        assertTrue(lista.contains(strSkill3));

        lista = startupperFacade.getSkillVotate(startupper2);
        assertFalse(lista.contains(strSkill1));
        assertTrue(lista.contains(strSkill2));
        assertTrue(lista.contains(strSkill3));

        lista = startupperFacade.getSkillVotate(startupper3);
        assertFalse(lista.contains(strSkill1));
        assertFalse(lista.contains(strSkill2));
        assertFalse(lista.contains(strSkill3));
    }

}
