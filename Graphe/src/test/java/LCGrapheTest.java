import junit.framework.TestCase;
import org.junit.Test;

public class LCGrapheTest extends TestCase {

    @Test
<<<<<<< HEAD
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
=======
    public void chercheMaillon() {
        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
        assertNotNull(graphe.chercherMaillon("S1"));
        assertNull(graphe.chercherMaillon("S32"));
    }

    @Test
    public void testExisteCentre() {
        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
        assertTrue("Sommet existant", graphe.existeCentre("S5"));
        assertTrue("Sommet inexistant", graphe.existeCentre("S31"));


    }

    @Test
    public void testVoisin() {
        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
        assertTrue(graphe.existeVoisin("S1", "S12"));
>>>>>>> testUnitaire

    }


<<<<<<< HEAD

=======
>>>>>>> testUnitaire
}
