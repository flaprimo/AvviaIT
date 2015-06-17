package avviait.model;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class StartupFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(StartupFacade .class, Startup.class, StartupperFacade.class, Startupper.class,
                        StartupperSkillFacade.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private StartupFacade startupFacade;
    @EJB
    private StartupperFacade startupperFacade;

    private Startupper startupper;
    private Startup startup;

    public void setUp() {
        startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testCreateStartup() throws Exception {
        setUp();
        assertNotNull(startup.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetStartupById() throws Exception {
        setUp();
        Startup persistedStartup = startupFacade.getStartup(startup.getId());
        assertNotNull(persistedStartup);
        assertEquals(persistedStartup.getNome(), startup.getNome());
        assertEquals(persistedStartup.getDescrizione(), startup.getDescrizione());
        assertEquals(persistedStartup.getDataFondazione(), startup.getDataFondazione());
        assertTrue(persistedStartup.getAttiva());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetStartupByName() throws Exception {
        setUp();
        Startup persistedStartup = startupFacade.getStartup(startup.getNome());
        assertNotNull(persistedStartup);
        assertEquals(persistedStartup.getNome(), startup.getNome());
        assertEquals(persistedStartup.getDescrizione(), startup.getDescrizione());
        assertEquals(persistedStartup.getDataFondazione(), startup.getDataFondazione());
        assertTrue(persistedStartup.getAttiva());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllStartup() throws Exception {
        setUp();
        List<Startup> startupList = startupFacade.getAllStartup();
        assertFalse(startupList.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllAmministratori() throws Exception {
        setUp();
        List<Startupper> amministratori = startupFacade.getAmministratori(startup);
        assertFalse(amministratori.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public  void testGetAllMembri() throws Exception {
        setUp();
        List<Startupper> membri = startupFacade.getMembri(startup);
        assertFalse(membri.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testUpdateStartup() throws Exception {
        setUp();
        startup.setDescrizione("nuova descrizione");
        startup.setAttiva(false);
        startupFacade.updateStartup(startup);
        Startup startupAggiornata = startupFacade.getStartup("AvviaIT");
        assertTrue(startupAggiornata.getDescrizione().equals("nuova descrizione"));
        assertFalse(startupAggiornata.getAttiva());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testAddMembro() throws  Exception {
        setUp();
        Startupper altroStartupper = startupperFacade.createStartupper("Altro", "Startupper", "altrostartupper@avviait.it", "thjbrty");
        altroStartupper = startupperFacade.getStartupperByEmail("altrostartupper@avviait.it");
        assertTrue(startupFacade.addMembro(startup, altroStartupper));
        assertTrue(startupFacade.getMembri(startup).contains(altroStartupper));
        assertTrue(altroStartupper.getStartupAttuali().contains(startup));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testRemoveMembro() throws Exception {
        setUp();
        Startupper startupper1 = startupperFacade.createStartupper("Mario", "Rossi", "mrossi@sdfs.it","sdfg");
        startupFacade.addMembro(startup, startupper1);
        assertTrue(startupFacade.removeMembro(startup, startupper1));
        assertFalse(startup.getMembri().contains(startupper1));
        assertFalse(startupper1.getStartupAttuali().contains(startup));
        assertTrue(startup.getMembriPassati().contains(startupper1));
        assertTrue(startupper1.getStartupPassate().contains(startup));
    }
}