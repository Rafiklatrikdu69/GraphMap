package LCGraphe;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    private Graphe graphe;

    DijkstraTest(){
        try{
            graphe = new Graphe();
            graphe.chargementFichier("src/fichiersGraphe/graphe30Som74Arete.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @org.junit.jupiter.api.Test
    void getCheminsFiabiliteTo() {
        LinkedHashMap<String, Double> rep1 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep2 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep3 = new LinkedHashMap();
        rep1.put("S21", 1.0);
        rep1.put("S4", 0.8);
        rep1.put("S25", 0.7200000000000001);
        rep1.put("S11", 0.7200000000000001);
        rep1.put("S29", 0.7200000000000001);
        rep1.put("S14", 0.5760000000000001);
        rep1.put("S30", 0.51840000000000010);

        rep2.put("S30", 1.0);
        rep2.put("S14", 0.9);
        rep2.put("S29", 0.7200000000000001);
        rep2.put("S11", 0.7200000000000001);
        rep2.put("S25", 0.7200000000000001);
        rep2.put("S4", 0.6480000000000001);
        rep2.put("S21", 0.5184000000000001);

        rep3.put("S4", 1.0);

        assertEquals(graphe.getCheminDijkstra().get("S21").getCheminsFiabiliteTo("S30"), rep1);

        assertEquals(graphe.getCheminDijkstra().get("S30").getCheminsFiabiliteTo("S21"), rep2);

        assertEquals(graphe.getCheminDijkstra().get("S4").getCheminsFiabiliteTo("S4"), rep3);
    }

    @org.junit.jupiter.api.Test
    void getCheminsDistanceTo() {
        LinkedHashMap<String, Double> rep1 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep2 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep3 = new LinkedHashMap();
        rep1.put("S14", 1.0);
        rep1.put("S29", 0.8);
        rep1.put("S11", 0.8);
        rep1.put("S25", 0.8);
        rep1.put("S4", 0.7200000000000001);
        rep1.put("S3", 0.7200000000000001);
        rep1.put("S1", 0.7200000000000001);

        rep2.put("S6", 1.0);
        rep2.put("S8", 0.7);
        rep2.put("S25", 0.5599999999999999);
        rep2.put("S11", 0.5599999999999999);
        rep2.put("S29", 0.5599999999999999);
        rep2.put("S18", 0.44799999999999995);

        rep3.put("S8", 1.0);

        assertEquals(graphe.getCheminDijkstra().get("S14").getCheminsFiabiliteTo("S1"), rep1);

        assertEquals(graphe.getCheminDijkstra().get("S6").getCheminsFiabiliteTo("S18"), rep2);

        assertEquals(graphe.getCheminDijkstra().get("S8").getCheminsFiabiliteTo("S8"), rep3);
    }

    @org.junit.jupiter.api.Test
    void getCheminsDureeTo() {
        LinkedHashMap<String, Double> rep1 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep2 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep3 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep4 = new LinkedHashMap();
        LinkedHashMap<String, Double> rep5 = new LinkedHashMap();
        rep1.put("S14", 1.0);
        rep1.put("S29", 0.8);
        rep1.put("S11", 0.8);
        rep1.put("S25", 0.8);
        rep1.put("S4", 0.7200000000000001);
        rep1.put("S3", 0.7200000000000001);
        rep1.put("S1", 0.7200000000000001);

        rep2.put("S6", 1.0);
        rep2.put("S8", 0.7);
        rep2.put("S25", 0.5599999999999999);
        rep2.put("S11", 0.5599999999999999);
        rep2.put("S29", 0.5599999999999999);
        rep2.put("S18", 0.44799999999999995);

        rep3.put("S8", 1.0);

        rep4.put("S6", 1.0);
        rep4.put("S8", 0.7);
        rep4.put("S25", 0.5599999999999999);
        rep4.put("S11", 0.5599999999999999);
        rep4.put("S29", 0.5599999999999999);
        rep4.put("S18", 0.44799999999999995);


        assertEquals(graphe.getCheminDijkstra().get("S14").getCheminsFiabiliteTo("S1"), rep1);

        assertEquals(graphe.getCheminDijkstra().get("S6").getCheminsFiabiliteTo("S18"), rep2);

        assertEquals(graphe.getCheminDijkstra().get("S8").getCheminsFiabiliteTo("S8"), rep3);

        assertNotEquals(graphe.getCheminDijkstra().get("S7").getCheminsFiabiliteTo("S18"), rep4);
    }

    @org.junit.jupiter.api.Test
    void getSommetDepart() {
        assertEquals(graphe.getCheminDijkstra().get("S1").getSommetDepart(), "S1");
        assertEquals(graphe.getCheminDijkstra().get("S20").getSommetDepart(), "S20");
        assertNotEquals(graphe.getCheminDijkstra().get("S20").getSommetDepart(), "S17");
    }
}