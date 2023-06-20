package LCGraphe;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class GrapheTest {

    private Graphe graphe;
    public GrapheTest() {
        try{
            graphe = new Graphe();
            graphe.chargementFichier("src/fichiersGraphe/graphe30Som74Arete.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getNombreOperatoire() {
        assertEquals(graphe.getNombreOperatoire(), 6, "Mauvais nombre d'opératoire !");
    }

    @Test
    void getNombreMaternite() {
        assertEquals(graphe.getNombreMaternite(), 18, "Mauvais nombre de maternité !");
    }

    @Test
    void getNombreCentreNutrition() {
        assertEquals(graphe.getNombreSommetNutrition(), 6, "Mauvais nombre de centre de nutrition !");

    }

    @Test
    void getNombreDispensaire() {
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 60);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 20);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 10);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 50);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 39);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 31);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 29);
        Assertions.assertNotEquals(graphe.getNombreDispensaire(), 100);

        Assertions.assertEquals(graphe.getNombreDispensaire(), 30);
    }

    @Test
    void getNombreRoute() {
        // il y a 74 routes/aretes
        Assertions.assertNotEquals(graphe.getNombreRoute(), 0);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 31);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 43);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 74);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 76);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 100);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 30);
        Assertions.assertNotEquals(graphe.getNombreRoute(), 20);

        Assertions.assertEquals(graphe.getNombreRoute(), 75);
    }

    @Test
    void existeVoisin() {
        System.out.println(graphe.getSommet("S28").getVoisin("S7"));
        Assertions.assertTrue(graphe.existeVoisin("S28", "S7"), "S28-S7 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S7", "S28"), "S7-S28 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S11", "S29"), "S11-S29 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S29", "S11"), "S29-S11 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S10", "S25"), "S10-S25 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S25", "S10"), "S25-S10 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S9", "S17"), "S9-S17 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S17", "S9"), "S9-S17 voisin inexistant");
        Assertions.assertTrue(graphe.existeVoisin("S1", "S2"), "S1-S2 voisin inexistant");


    }

    @Test
    void existeCentre() {
        // Il a 30 sommets entre S1 et S30
        Assertions.assertFalse(graphe.existeSommet("S35"), "S35 existant ?");
        Assertions.assertTrue(graphe.existeSommet("S2"), "S2 inexistant");
        Assertions.assertFalse(graphe.existeSommet("S32"), "S31 existant ?");
        Assertions.assertTrue(graphe.existeSommet("S5"), "S5 inexistant");
        Assertions.assertFalse(graphe.existeSommet("S31"), "S31 existant ?");
    }
}