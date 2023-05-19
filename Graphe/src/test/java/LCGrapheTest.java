import junit.framework.TestCase;
import org.junit.Test;

public class LCGrapheTest extends TestCase {

    @Test
    public void testExisteCentre() {
        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("chemin_vers_le_fichier.csv");

        assertFalse(graphe.existeCentre("S30"));
        assertNull(graphe.existeCentre("S33"));
        // Autres assertions pour tester différents cas
    }

    @Test
    public void testAjouterSommet() {
        LCGraphe graphe = new LCGraphe();


        assertTrue(graphe.existeCentre("S1"));
        assertTrue(graphe.existeCentre("S2"));
        assertFalse(graphe.existeCentre("S3"));

    }



}
