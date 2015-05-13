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

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@RunWith(Arquillian.class)
public class StartupperFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(StartupperFacade.class, Startupper.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private StartupperFacade startupperFacade;

    /**
     * Methods Tests
     */
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getIdStartupperTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        assertNotNull("ID should not be null", startupper.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getStartupperTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        Startupper persistedStartupper = startupperFacade.getStartupper(startupper.getId());
        assertNotNull(persistedStartupper);

        assertEquals(persistedStartupper.getNome(), startupper.getNome());
        assertEquals(persistedStartupper.getCognome(), startupper.getCognome());
        assertEquals(persistedStartupper.getAttivo(), startupper.getAttivo());
        assertEquals(persistedStartupper.getDescrizione(), startupper.getDescrizione());
        assertEquals(persistedStartupper.getEmail(), startupper.getEmail());
        assertNotEquals(persistedStartupper.getPassword(), "");
        assertNotEquals(persistedStartupper.getSaltPassword(), "");
        assertNotEquals(persistedStartupper.getDataIscrizione(), "");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getStartupperByEmailTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");
        Startupper persistedStartupper = startupperFacade.getStartupperByEmail("gino.paoli@gmail.com");

        assertNotNull("ID should not be null", persistedStartupper.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void nomeCompletoTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        assertEquals(startupperFacade.getNomeCompleto(startupper), "Gino Paoli");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void checkPasswordTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        assertFalse(startupperFacade.checkPassword(startupper, ""));
        assertFalse(startupperFacade.checkPassword(startupper, "siww"));
        assertTrue(startupperFacade.checkPassword(startupper, "siw"));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void changePasswordTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        assertTrue(startupperFacade.changePassword(startupper, "siw2"));

        assertTrue(startupperFacade.checkPassword(startupper, "siw2"));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getAllStartupperTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        List<Startupper> startupperList = startupperFacade.getAllStartupper();

        assertTrue(startupperList.size()>0);

        // check that returned list contains created startupper
        int i = 0;
        boolean foundStartupper = false;
        while (i<startupperList.size() && !foundStartupper) {
            if (startupperList.get(i).getId().equals(startupper.getId()))
                foundStartupper = true;
            i++;
        }
        assertTrue(foundStartupper);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteStartupperTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        startupperFacade.deleteStartupper(startupper.getId());
        assertNull("ID should not be null", startupperFacade.getStartupper(startupper.getId()));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void updateStartupperTest() {
        Startupper startupper = startupperFacade.createStartupper("Gino", "Paoli", "gino.paoli@gmail.com", "siw");

        startupper.setAttivo(false);
        startupperFacade.updateStartupper(startupper);
        assertFalse(startupper.getAttivo());
    }
}