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
                .addClasses(StartupFacade .class, Startup.class, StartupperFacade.class, Startupper.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private StartupFacade startupFacade;
    @EJB
    private StartupperFacade startupperFacade;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testCreateStartup() throws Exception {
        Startupper startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        Startup startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        assertNotNull(startup.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetStartup() throws Exception {
        Startupper startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        Startup startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        Startup persistedStartup = startupFacade.getStartup(startup.getId());
        assertNotNull(persistedStartup);
        assertEquals(persistedStartup.getNome(), startup.getNome());
        assertEquals(persistedStartup.getDescrizione(), startup.getDescrizione());
        assertEquals(persistedStartup.getDataFondazione(), startup.getDataFondazione());
        assertTrue(persistedStartup.isAttiva());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllStartup() throws Exception {
        Startupper startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        List<Startup> startupList = startupFacade.getAllStartup();
        assertFalse(startupList.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllAmministratori() throws Exception {
        Startupper startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        Startup startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        List<Startupper> amministratori = startupFacade.getAmministratori(startup);
        assertFalse(amministratori.isEmpty());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public  void testGetAllMembri() throws Exception {
        Startupper startupper = startupperFacade.createStartupper("Licio", "Chismi", "chismi.licio@tp.it","dcr5e6r");
        Startup startup = startupFacade.createStartup("AvviaIT", "Network sociale universitario " +
                        "nel quale gli studenti possono iscriversi e semplificare la possibilità " +
                        "di associazione per creare progetti innovativi (startup).", new GregorianCalendar(2015,5,4),
                startupper);
        List<Startupper> membri = startupFacade.getMembri(startup);
        assertFalse(membri.isEmpty());
    }
}