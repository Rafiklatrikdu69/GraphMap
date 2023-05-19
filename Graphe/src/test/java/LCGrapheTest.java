import junit.framework.TestCase;
import org.junit.Test;

public class LCGrapheTest extends TestCase {

    @Test
    public void chercheMaillon() {
        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
        assertNotNull(graphe.chercherMaillon("S1"));
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

    }


}
