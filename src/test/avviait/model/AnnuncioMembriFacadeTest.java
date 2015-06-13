package avviait.model;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AnnuncioMembriFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(AnnuncioMembriFacade.class, AnnuncioMembri.class,
                        StartupFacade.class, Startup.class, StartupperFacade.class, Startupper.class,
                        SkillFacade.class, Skill.class, StartupperSkillFacade.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private AnnuncioMembriFacade annuncioMembriFacade;
    @EJB
    private StartupperFacade startupperFacade;
    @EJB
    private StartupFacade startupFacade;
    @EJB
    private SkillFacade skillFacade;

    private Startupper startupper;
    private Startup startup;
    private AnnuncioMembri annuncioMembri;
    private Skill skill1;
    private Skill skill2;
    private List<Skill> skillRichieste;

    public void setUp() {
        startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        skill1 = skillFacade.createSkill("JSF");
        skill2 = skillFacade.createSkill("JavaEE");
        skillRichieste = new LinkedList<Skill>();
        skillRichieste.add(skill1);
        skillRichieste.add(skill2);
        annuncioMembri =
                annuncioMembriFacade.createAnnuncioMembri("Developer", "conoscenze javaEE", startup, skillRichieste);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testCreateAnnuncioMembri() throws Exception {
        setUp();
        assertNotNull(annuncioMembri.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAnnuncioMembri() throws Exception {
        setUp();
        AnnuncioMembri persistedAnnuncio = annuncioMembriFacade.getAnnuncioMembri(annuncioMembri.getId());
        assertEquals(persistedAnnuncio.getMansione(), annuncioMembri.getMansione());
        assertEquals(persistedAnnuncio.getDescrizione(), annuncioMembri.getDescrizione());
        assertNotNull(persistedAnnuncio.getDataCreazione());
        assertNotNull(persistedAnnuncio.getAutrice());
        assertEquals(persistedAnnuncio.getSkillRichieste().get(0).getId(),skill1.getId());
        assertEquals(persistedAnnuncio.getSkillRichieste().get(1).getId(),skill2.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllAnnuncioMembri() throws Exception {
        setUp();
        List<AnnuncioMembri> annuncioMembriList = annuncioMembriFacade.getAllAnnuncioMembri();
        assertFalse(annuncioMembriList.isEmpty());
        assertTrue(annuncioMembriList.contains(annuncioMembriFacade.getAnnuncioMembri(annuncioMembri.getId())));
    }
}