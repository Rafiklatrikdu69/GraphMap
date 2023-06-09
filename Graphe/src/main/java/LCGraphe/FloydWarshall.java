package LCGraphe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FloydWarshall {
	private List<String> sommet;
	private List<Double> sommetDonnees;
	private Map<String, Map<String, Double>> fiabilites;
	
	private Map<String, Map<String, String>> predecesseurs;
	private Graphe graphe;
	
	
	FloydWarshall(Graphe graphe){
		this.graphe = graphe;
	}
	private void  initMap(){
		fiabilites = new LinkedHashMap<>();
		
		predecesseurs = new LinkedHashMap<>();
	}
	private void copieListeToMap(Map<String, Map<String, Double>> copieFiabilite){
		Graphe.MaillonGraphe tmp = graphe.getPremier();
		
		// cffecte les valeurs de fiabilités dans la map avec le sommet source et les sommets destination associés
		while (tmp != null) {
			Map<String, Double> mapVoisin = new LinkedHashMap<>();
			String nom = tmp.getNom();
			Graphe.MaillonGrapheSec tmp2 = tmp.getVoisin();
			
			while (tmp2 != null) {
				mapVoisin.put(tmp2.getDestination(), tmp2.getFiabilite() / 10);
				tmp2 = tmp2.getSuivantMaillonSec();
			}
			
			fiabilites.put(nom, mapVoisin);
			copieFiabilite.put(nom,mapVoisin);
			tmp = tmp.getSuivant();
		}
		
		for (String i : fiabilites.keySet()) {
			predecesseurs.put(i, new LinkedHashMap<>());
			for (String j : fiabilites.keySet()) {
				
				predecesseurs.get(i).put(j, i); // chaque sommet est son propre prédécesseur au départ
			}
		}
	}
	public Map<String, Map<String, Double>> floydMap() {
		initMap();
		Map<String, Map<String, Double>> copieFiabilite = new LinkedHashMap<>();
		
		copieListeToMap(copieFiabilite);
		
		for (String k : fiabilites.keySet()) {
			for (String i : fiabilites.keySet()) {
				for (String j : fiabilites.keySet()) {
					if (fiabilites.get(i).containsKey(k) && fiabilites.get(k).containsKey(j)) {
						double fiabiliteIK = fiabilites.get(i).get(k);
						double fiabiliteKJ = fiabilites.get(k).get(j);
						double fiabiliteIJ = fiabilites.getOrDefault(i, new LinkedHashMap<>()).getOrDefault(j, Double.NEGATIVE_INFINITY);
						
						if (fiabiliteIK * fiabiliteKJ > fiabiliteIJ) {
							fiabilites.get(i).put(j, fiabiliteIK * fiabiliteKJ);
							predecesseurs.get(i).put(j, predecesseurs.get(k).get(j)); // Mettre à jour le prédécesseur de j à i
						}
					}
				}
			}
		}
		
		
		rechercheChemin("S2","S19");
		
		return fiabilites;
		
		
		
	}
	
	
	public void rechercheChemin(String sommetDepart,String sommetArrivee){
		sommet = new ArrayList<>();
		sommetDonnees = new ArrayList<>();
		String chemin = sommetArrivee;
		String predecesseur = predecesseurs.get(sommetDepart).get(sommetArrivee);
		double fiabiliteTotale = fiabilites.get(predecesseur).get(sommetArrivee);
		
		while (!predecesseur.equals(sommetDepart)) {
			
			chemin = predecesseur + " -> " + chemin;
			String sommetPrecedent = predecesseurs.get(sommetDepart).get(predecesseur);
			fiabiliteTotale *= fiabilites.get(sommetPrecedent).get(predecesseur);
			predecesseur = sommetPrecedent;
		}
		
		chemin = sommetDepart + " -> " + chemin;
		System.out.println("Chemin de " + sommetDepart + " à " + sommetArrivee + ": " + chemin);
		
		
		String[] sommets = chemin.split(" -> ");
		sommetDonnees.add(1.0);
		for (int k = 0; k < sommets.length - 1; k++) {
			String sommetActuel = sommets[k];
			String sommetSuivant = sommets[k + 1];
			
			double fiabiliteSommet = fiabilites.get(sommetActuel).get(sommetSuivant);
			System.out.println("Fiabilité sommet " + sommetActuel + " -> " + sommetSuivant + ": " + fiabiliteSommet * 100);
			if(!sommet.contains(sommetActuel)){
				sommet.add(sommetActuel);
				
			}
			if(!sommet.contains(sommetSuivant)){
				sommet.add(sommetSuivant);
				sommetDonnees.add(fiabiliteSommet);
			}
		}
		
		System.out.println("Fiabilité totale : " + fiabiliteTotale * 100);
		for(String i : sommet){
			System.out.println("sommet : "+i);
		}
		for(Double i : sommetDonnees){
			System.out.println("fiabilite : "+i);
		}
	}
	public List<String> getSommet(){
		return sommet;
	}
	public List<Double> getSommetDonnees(){
		return sommetDonnees;
	}
}
