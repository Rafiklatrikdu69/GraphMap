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
	
	
	public FloydWarshall(Graphe graphe){
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
				mapVoisin.put(tmp2.getDestination().getNom(), tmp2.getFiabilite() / 10);//affecte les fiabilites des voisins dans la map
				tmp2 = tmp2.getSuivantMaillonSec();
			}
			
			fiabilites.put(nom, mapVoisin);//affecte le nom du sommet source avec la map des voisins
			copieFiabilite.put(nom,mapVoisin);
			tmp = tmp.getSuivant();
		}
		
		for (String i : fiabilites.keySet()) {
			predecesseurs.put(i, new LinkedHashMap<>());
			for (String j : fiabilites.keySet()) {
					if(!i.equals(j)){
						//gerer les circuits !!!
					}
				predecesseurs.get(i).put(j, i); // chaque sommet est son propre prédécesseur au départ
			}
		}
	}
	public Map<String, Map<String, Double>> floydWarshallFiabilite() {
		initMap();
		Map<String, Map<String, Double>> copieFiabilite = new LinkedHashMap<>();
		
		copieListeToMap(copieFiabilite);//appel de la methode pour copier les donnes de la liste chainée dans la map fiabilite afin d'utliser les donnes
		//Objectif Optimisation avec des maps
		/*Debut de l'algo */
		//met la fiabilite a 100 % sur la diagonale
		for (String k : fiabilites.keySet()) {
			for (String i : fiabilites.keySet()) {
				if (k.equals(i)){
					fiabilites.get(k).put(i, 1.0);
				}
				
			}
		}
		for (String k : fiabilites.keySet()) {
			for (String i : fiabilites.keySet()) {
				for (String j : fiabilites.keySet()) {
					if (fiabilites.get(i).containsKey(k) && fiabilites.get(k).containsKey(j)) {//verifie si les valeurs son null et qu'elles sont contenu dans la map
						double fiabiliteIK = fiabilites.get(i).get(k);
						double fiabiliteKJ = fiabilites.get(k).get(j);
						double fiabiliteIJ = fiabilites.getOrDefault(i, new LinkedHashMap<>()).getOrDefault(j, Double.NEGATIVE_INFINITY);
						/*
						S1[2,4,7]
						S2[4,4,9]
						S3[1,8,5]
						S4[2,4,0]
						Il faut voir la map comme un matrice ,pour chaque sommet(string) on a les fiabilites des voisin
						ensuite on projet ligne 1 colonne 1 puis ligne 2 colonne 2 pour verifier si la fiabilite est meilleur
						*/
						if (fiabiliteIK * fiabiliteKJ > fiabiliteIJ) {//Si la fiabilite 4 > 4*4 alors on la change sinon on ny touche pas(voir exemple dessus avec matrice)
							fiabilites.get(i).put(j, fiabiliteIK * fiabiliteKJ);//on ajoute la nouvelle fiabilite
							predecesseurs.get(i).put(j, predecesseurs.get(k).get(j)); // met à jour le prédécesseur de j à i
						}
					}
				}
			}
		}
	
		
		
		return fiabilites;
		
		
		
	}
	
	
	public double rechercheChemin(String sommetDepart,String sommetArrivee){
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
		
		
		String[] sommets = chemin.split(" -> ");
		sommetDonnees.add(1.0);
		for (int k = 0; k < sommets.length - 1; k++) {
			String sommetActuel = sommets[k];
			String sommetSuivant = sommets[k + 1];
			
			double fiabiliteSommet = fiabilites.get(sommetActuel).get(sommetSuivant);
		
			if(!sommet.contains(sommetActuel)){
				sommet.add(sommetActuel);
				
			}
			if(!sommet.contains(sommetSuivant)){
				sommet.add(sommetSuivant);
				sommetDonnees.add(fiabiliteSommet);
			}
		}
	
		//System.out.println("Fiabilité totale : " + fiabiliteTotale * 100);
		
		return fiabiliteTotale*100;
	
	}
	public List<String> getSommet(){
		return sommet;
	}
	public List<Double> getSommetDonnees(){
		return sommetDonnees;
	}
	
}
