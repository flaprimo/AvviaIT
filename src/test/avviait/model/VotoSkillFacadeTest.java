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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class VotoSkillFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(VotoSkill.class, Startupper.class, VotoSkillFacade.class, Skill.class,
                        SkillFacade.class, StartupperFacade.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private VotoSkillFacade votoSkillFacade;
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
    public void setUpAnnuncio() {
        startupper1 = startupperFacade.createStartupper("Alessandro", "Arcangeli", "alessandro@arcangeli.it", "asd");
        startupper2 = startupperFacade.createStartupper("Mario", "Rossi", "mario.rossi@gmail.com", "qwerty");
        startupper3 = startupperFacade.createStartupper("Luigi", "Verdi", "luigi.verdi@gmail.com", "ytrewq");
        skill1 = skillFacade.createSkill("Unity");
        skill2 = skillFacade.createSkill("Ogre3d");
        skill3 = skillFacade.createSkill("Dark Basic");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getIdSkillTest() {
        setUpAnnuncio();
        VotoSkill voto = votoSkillFacade.createVotoSkill("Titolo", "Commento", startupper1, startupper2, skill1);

        assertNotNull("ID should not be null", voto.getId());
    }

    public static boolean searchVotoSkill(List<VotoSkill> list, VotoSkill searchFor) {
        for (VotoSkill test : list)
            if (test.getTitolo().equals(searchFor.getTitolo()) &&
                    test.getTesto().equals(searchFor.getTesto()))
                return true;
        return false;
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getVotoSkillFromAutore() {
        setUpAnnuncio();
        VotoSkill voto1 = votoSkillFacade.createVotoSkill("Titolo1", "Commento1", startupper1, startupper2, skill1);
        VotoSkill voto2 = votoSkillFacade.createVotoSkill("Titolo2", "Commento2", startupper1, startupper3, skill2);
        VotoSkill voto3 = votoSkillFacade.createVotoSkill("Titolo3", "Commento3", startupper2, startupper3, skill3);

        List<VotoSkill> lista;

        lista = votoSkillFacade.getAllVotoSkillForAutore(startupper1);
        assertTrue(searchVotoSkill(lista, voto1));
        assertTrue(searchVotoSkill(lista, voto2));
        assertFalse(searchVotoSkill(lista, voto3));

        lista = votoSkillFacade.getAllVotoSkillForAutore(startupper2);
        assertFalse(searchVotoSkill(lista, voto1));
        assertFalse(searchVotoSkill(lista, voto2));
        assertTrue(searchVotoSkill(lista, voto3));

        lista = votoSkillFacade.getAllVotoSkillForAutore(startupper3);
        assertFalse(searchVotoSkill(lista, voto1));
        assertFalse(searchVotoSkill(lista, voto2));
        assertFalse(searchVotoSkill(lista, voto3));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getVotoSkillFromGiudicato() {
        setUpAnnuncio();
        VotoSkill voto1 = votoSkillFacade.createVotoSkill("Titolo1", "Commento1", startupper1, startupper2, skill1);
        VotoSkill voto2 = votoSkillFacade.createVotoSkill("Titolo2", "Commento2", startupper1, startupper3, skill2);
        VotoSkill voto3 = votoSkillFacade.createVotoSkill("Titolo3", "Commento3", startupper2, startupper3, skill3);

        List<VotoSkill> lista;

        lista = votoSkillFacade.getAllVotoSkillForGiudicato(startupper1);
        assertFalse(searchVotoSkill(lista, voto1));
        assertFalse(searchVotoSkill(lista, voto2));
        assertFalse(searchVotoSkill(lista, voto3));

        lista = votoSkillFacade.getAllVotoSkillForGiudicato(startupper2);
        assertTrue(searchVotoSkill(lista, voto1));
        assertFalse(searchVotoSkill(lista, voto2));
        assertFalse(searchVotoSkill(lista, voto3));

        lista = votoSkillFacade.getAllVotoSkillForGiudicato(startupper3);
        assertFalse(searchVotoSkill(lista, voto1));
        assertTrue(searchVotoSkill(lista, voto2));
        assertTrue(searchVotoSkill(lista, voto3));
    }

}
