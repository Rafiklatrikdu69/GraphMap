import org.junit.Test;
import static org.junit.Assert.*;

public class LCGrapheTest {



    @Test
    public void testAjouterSommet() {
        LCGraphe graphe = new LCGraphe();


        assertTrue(graphe.existeCentre("S1"));
        assertTrue(graphe.existeCentre("S2"));
        assertFalse(graphe.existeCentre("S3"));
    }
    @Test
   public void testChercheMaillon() {

        LCGraphe graphe = new LCGraphe();
        graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
       // assertNotNull(graphe.chercherMaillon("S1"));
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
