import LCGraphe.Graphe;
import org.junit.Test;
import static org.junit.Assert.*;

public class LCGrapheTest {
     
     private Graphe graphe;
    public LCGrapheTest(){
          graphe = new Graphe();

     }
     
     @Test
     public void testAjouterSommet() {
       
          assertFalse(graphe.existeCentre("S35"));
          assertFalse(graphe.existeCentre("S2"));
          assertFalse(graphe.existeCentre("S32"));
     }
     @Test
     public void testChercheMaillon() {
          
          assertNull(graphe.getCentre("S1"));
     }
     
     @Test
     public void testExisteCentre() {
          
          assertTrue("Sommet existant", graphe.existeCentre("S5"));
          assertTrue("Sommet inexistant", graphe.existeCentre("S31"));
     }
     
     @Test
     public void testVoisin() {
          
          assertTrue(graphe.existeVoisin("S1", "S12"));
          
     }
     @Test
     public void testCentreListe(){
          
          
          assertNotNull(graphe.tousLesCentresToList());
     }
     @Test
     public void testgetNomSommet(){
          
          
          //assertEquals(new Boolean[]{graphe.getNomSommet(2).equals("S2")});
     }
     
     
     
}
