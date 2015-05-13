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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GiudizioFacadeTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(GiudizioFacade.class, Giudizio.class, StartupperFacade.class, Startupper.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private StartupperFacade startupperFacade;
    @EJB
    private GiudizioFacade giudizioFacade;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getIdGiudizioTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");

        System.out.println(autore.toString());
        System.out.println(giudicato.toString());
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        System.out.println(autore.toString());
        System.out.println(giudicato.toString());
        System.out.println(giudizio.toString());

        assertNotNull("ID should not be null", autore.getId());
        assertNotNull("ID should not be null", giudicato.getId());
        assertNotNull("ID should not be null", giudizio.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getGiudizioTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        Giudizio persistedGiudizio = giudizioFacade.getGiudizio(giudizio.getId());
        assertNotNull(persistedGiudizio);

        assertEquals(persistedGiudizio.getVoto(), giudizio.getVoto());
        assertEquals(persistedGiudizio.getTitolo(), giudizio.getTitolo());
        assertEquals(persistedGiudizio.getTesto(), giudizio.getTesto());
        assertNotEquals(persistedGiudizio.getDataCreazione(), "");
        assertNotNull(persistedGiudizio.getAutore());
        assertNotNull(persistedGiudizio.getGiudicato());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void updateGiudizioTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        giudizio.setTitolo("Pessimo collaboratore");
        giudizioFacade.updateGiudizio(giudizio);
        assertEquals(giudizio.getTitolo(), "Pessimo collaboratore");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getGiudiziDatiTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        List<Giudizio> giudiziDatiList = autore.getGiudiziDati();

        assertNotNull(giudiziDatiList);
        assertTrue(giudiziDatiList.size()>0);

        // check that returned list contains created giudizio
        int i = 0;
        boolean foundGiudizio = false;
        while (i<giudiziDatiList.size() && !foundGiudizio) {
            if (giudiziDatiList.get(i).getId().equals(giudizio.getId()))
                foundGiudizio = true;
            i++;
        }
        assertTrue(foundGiudizio);

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getGiudiziRicevutiTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        List<Giudizio> giudiziRicevutiList = giudicato.getGiudiziRicevuti();

        assertNotNull(giudiziRicevutiList);
        assertTrue(giudiziRicevutiList.size()>0);

        // check that returned list contains created giudizio
        int i = 0;
        boolean foundGiudizio = false;
        while (i<giudiziRicevutiList.size() && !foundGiudizio) {
            if (giudiziRicevutiList.get(i).getId().equals(giudizio.getId()))
                foundGiudizio = true;
            i++;
        }
        assertTrue(foundGiudizio);

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteGiudizioTest() {
        Startupper autore = startupperFacade.createStartupper("Bill", "Gates", "bill.gates@outlook.com", "i<3opensource");
        Startupper giudicato = startupperFacade.createStartupper("Steve", "Jobs", "steve.jobs@me.com", "i<3pears");
        Giudizio giudizio = giudizioFacade.createGiudizio(1, "Ottimo collaboratore", "Competente e affidabile, " +
                "ho lavorato con lui in diversi progetti.", autore, giudicato);

        giudizioFacade.deleteGiudizio(giudizio.getId());
        assertNull(giudizioFacade.getGiudizio(giudizio.getId()));
    }
}
