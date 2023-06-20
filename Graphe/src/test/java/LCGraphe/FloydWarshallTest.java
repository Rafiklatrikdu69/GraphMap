package LCGraphe;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class FloydWarshallTest {
	
	
	private Graphe graphe;

	public FloydWarshallTest() {
		try {
			graphe = new Graphe();
			graphe.chargementFichier("src/fichiersGraphe/graphe30Som74Arete.csv");
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
		//
	}
	@Test
	public void DonnesFiabiites(){
		FloydWarshall f = new FloydWarshall(graphe);
		double fiabiliteTest = 	0.0;
		fiabiliteTest = 	graphe.rechercheChemin("S11","S25");
	
		assertEquals(fiabiliteTest,100.0);
		fiabiliteTest = 	graphe.rechercheChemin("S1","S12");
		assertEquals(fiabiliteTest,44.8);
		
		fiabiliteTest = 	graphe.rechercheChemin("S15","S1");
		assertEquals(fiabiliteTest,58.320000000000014);
		
		fiabiliteTest = 	graphe.rechercheChemin("S16","S30");
		assertEquals(fiabiliteTest,81.0);
		
		
		
	}
	@Test
	public void fiabiliteDiagonale(){
		FloydWarshall f = new FloydWarshall(graphe);
		double fiabiliteTest = 	0.0;
		fiabiliteTest = 	graphe.rechercheChemin("S1","S1");
		
		assertEquals(fiabiliteTest,100.0);
		fiabiliteTest = 	graphe.rechercheChemin("S2","S2");
		assertEquals(fiabiliteTest,100.0);
		
		fiabiliteTest = 	graphe.rechercheChemin("S3","S3");
		assertEquals(fiabiliteTest,100.0);
		
		fiabiliteTest = 	graphe.rechercheChemin("S4","S4");
		assertEquals(fiabiliteTest,100.0);
		
		
		
	}
	
	
}