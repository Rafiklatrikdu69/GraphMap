package LCGraphe;

import java.util.*;

public class Dijkstra {
	private String sommetDepart;
	private Map<String, LinkedHashMap<String, Double>> mapPlusCourtCheminsFiabilite;
	private Map<String, LinkedHashMap<String, Double>> mapPlusCourtCheminsDistance;
	private Map<String, LinkedHashMap<String, Double>> mapPlusCourtCheminsDuree;
	
	public Dijkstra(Graphe graphe, String nomSommet) {
		this.sommetDepart = nomSommet;
		plusCourtCheminDijkstraFiabilite(graphe, this.sommetDepart);
		plusCourtCheminDijkstraDistance(graphe, this.sommetDepart);
		plusCourtCheminDijkstraDuree(graphe, this.sommetDepart);
	}
	
	/**
	 * Cette methode trouve le chemin le plus fiable pour aller d'un SXX a un sommet SXX
	 *
	 * @param graphe    : Graphe
	 * @param nomSommet : String
	 * @return
	 */
	private void plusCourtCheminDijkstraFiabilite(Graphe graphe, String nomSommet) {
		mapPlusCourtCheminsFiabilite = new HashMap<>();//Hash map qui permet de stocker les chemins les plus fiables
		Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter
		mapPlusCourtCheminsFiabilite.put(nomSommet, new LinkedHashMap<>());
		mapPlusCourtCheminsFiabilite.get(nomSommet).put(nomSommet, 1.0);//le premier centre(sommet) a une fiabilite de 1
		//on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
		try {
			graphe.tousLesSommetToList().forEach(Centre -> {
				sommetsTraites.put(Centre.getNom(), false);
			});
		} catch (Exception e) {
			System.out.println("liste vide !");
		}
		
		sommetsTraites.put(nomSommet, true);//le premier centre est le debut donc il traiter -> true
		List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
		fileAttente.add(new String[]{nomSommet, "1"});//ajoute le premier sommet avec comme fiabilite 1
		String[] donnee;
		while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide
			donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la fiabilite et le centre de la fin de la file dans la variable donnee
			fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element
			String centre = donnee[0];//donne[0] vaut le nom du centre
			double fiab = Double.parseDouble(donnee[1]);//donne[1] vaut la fiabilite de type String convertie en double
			
			Graphe.MaillonGrapheSec voisin = graphe.getSommet(centre).getVoisin();
			while (voisin != null) {
				String nomVoisin = voisin.getDestination().getNom();
				if (!sommetsTraites.get(nomVoisin)) {
					if (!mapPlusCourtCheminsFiabilite.containsKey(nomVoisin)) {
						// On arrive à cette endroit si nomVoisin n'a pas encore de chemin
						mapPlusCourtCheminsFiabilite.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsFiabilite.get(centre)));
						mapPlusCourtCheminsFiabilite.get(nomVoisin).put(nomVoisin, (voisin.getFiabilite() / 10) * fiab);
						fileAttente.add(new String[]{nomVoisin, String.valueOf((voisin.getFiabilite() / 10) * fiab)});
						
					} else {
						// si nomVoisin a déjà un chemin
						LinkedHashMap<String, Double> chemin = mapPlusCourtCheminsFiabilite.get(nomVoisin);
						Double lastFiabCentreDansChemin = null;
						String lastNomCentreDansChemin = null;
						for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
							// Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
							lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
							lastFiabCentreDansChemin = centreChemin.getValue(); // la fiabilité de l'extrémité
						}
						if (lastFiabCentreDansChemin < (voisin.getFiabilite() / 10) * fiab) {
							// Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus fiable a ce moment la
							mapPlusCourtCheminsFiabilite.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsFiabilite.get(donnee[0])));
							mapPlusCourtCheminsFiabilite.get(nomVoisin).put(lastNomCentreDansChemin, (voisin.getFiabilite() / 10) * fiab);
							// Donc on créer le nouveau chemin le plus fiable entre centre1 et nomVoisin
							
							// Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
							// Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle fiabilité
							int i = 0;
							boolean check = false;
							while (!check && i < fileAttente.size()) {
								if (fileAttente.get(i)[0].equals(nomVoisin)) {
									check = true;
									fileAttente.remove(i);
									fileAttente.add(new String[]{nomVoisin, String.valueOf((voisin.getFiabilite() / 10) * fiab)});
								}
								i++;
							}
						}
						
					}
				}
				voisin = voisin.getSuivantMaillonSec();
			}
			if (fileAttente.size() >= 2) {
				// si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus fiable tout en haut de la liste pour la traité en premier
				int maxiFiabIndice = 0;
				for (int i = 1; i < fileAttente.size(); i++) {
					if (Double.parseDouble(fileAttente.get(i)[1]) > Double.parseDouble(fileAttente.get(maxiFiabIndice)[1])) {
						// si on arrive ici, ca veut dire qu'on a trouvé le plus fiable au ieme moment
						maxiFiabIndice = i;
					} else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiFiabIndice)[1])) {
						// si on arrive ici, ca veut dire que deux sommets ont la même fiabilité donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
						String[] listNomCentre = fileAttente.get(i)[0].split("");
						String[] listMaxiNomCentre = fileAttente.get(maxiFiabIndice)[0].split("");
						int nombre;
						int maxiNombre;
						if (listNomCentre.length == 2) {
							nombre = Integer.parseInt(listNomCentre[1]);
						} else {
							nombre = Integer.parseInt(listNomCentre[1] + listNomCentre[2]);
						}
						if (listMaxiNomCentre.length == 2) {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1]);
						} else {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1] + listMaxiNomCentre[2]);
						}
						if (maxiNombre > nombre) {
							maxiFiabIndice = i;
						}
					}
				}
				sommetsTraites.put(fileAttente.get(maxiFiabIndice)[0], true); // je marque le sommet qui va etre traité
				fileAttente.add(new String[]{fileAttente.get(maxiFiabIndice)[0], String.valueOf(fileAttente.get(maxiFiabIndice)[1])});
				fileAttente.remove(maxiFiabIndice);
			}
		}
	}
	
	private void plusCourtCheminDijkstraDistance(Graphe graphe, String nomSommet) {
		mapPlusCourtCheminsDistance = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
		Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter
		mapPlusCourtCheminsDistance.put(nomSommet, new LinkedHashMap<>());
		mapPlusCourtCheminsDistance.get(nomSommet).put(nomSommet, 0.0);//le premier centre(sommet) a une distance de 0
		//on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
		try {
			graphe.tousLesSommetToList().forEach(Centre -> {
				sommetsTraites.put(Centre.getNom(), false);
			});
		} catch (Exception e) {
			System.out.println("liste vide !");
		}
		sommetsTraites.put(nomSommet, true);//le premier centre est le debut donc il traiter -> true
		List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
		fileAttente.add(new String[]{nomSommet, "0"});//ajoute le premier sommet avec comme distance 0
		String[] donnee;
		while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide
			donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la distance et le centre de la fin de la file dans la variable donnee
			fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element
			String centre = donnee[0];//donne[0] vaut le nom du centre
			double distance = Double.parseDouble(donnee[1]);//donne[1] vaut la distance de type String convertie en double
			
			Graphe.MaillonGrapheSec voisin = graphe.getSommet(centre).getVoisin();
			while (voisin != null) {
				String nomVoisin = voisin.getDestination().getNom();
				if (!sommetsTraites.get(nomVoisin)) {
					if (!mapPlusCourtCheminsDistance.containsKey(nomVoisin)) {
						// On arrive à cette endroit si nomVoisin n'a pas encore de chemin
						mapPlusCourtCheminsDistance.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsDistance.get(centre)));
						mapPlusCourtCheminsDistance.get(nomVoisin).put(nomVoisin, voisin.getDistance() + distance);
						fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDistance() + distance)});
						
					} else {
						// si nomVoisin a déjà un chemin
						LinkedHashMap<String, Double> chemin = mapPlusCourtCheminsDistance.get(nomVoisin);
						Double lastDistCentreDansChemin = null;
						String lastNomCentreDansChemin = null;
						for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
							// Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
							lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
							lastDistCentreDansChemin = centreChemin.getValue(); // la distance de l'extrémité
						}
						if (lastDistCentreDansChemin > voisin.getDistance() + distance) {
							// Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus court à ce moment la
							mapPlusCourtCheminsDistance.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsDistance.get(donnee[0])));
							mapPlusCourtCheminsDistance.get(nomVoisin).put(lastNomCentreDansChemin, voisin.getDistance() + distance);
							// Donc on créer le nouveau chemin le plus court entre centre1 et nomVoisin
							
							// Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
							// Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle distance
							int i = 0;
							boolean check = false;
							while (!check && i < fileAttente.size()) {
								if (fileAttente.get(i)[0].equals(nomVoisin)) {
									check = true;
									fileAttente.remove(i);
									fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDistance() + distance)});
								}
								i++;
							}
						}
						
					}
				}
				voisin = voisin.getSuivantMaillonSec();
			}
			if (fileAttente.size() >= 2) {
				// si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus court tout en haut de la liste pour la traité en premier
				int maxiDistIndice = 0;
				for (int i = 1; i < fileAttente.size(); i++) {
					if (Double.parseDouble(fileAttente.get(i)[1]) < Double.parseDouble(fileAttente.get(maxiDistIndice)[1])) {
						// si on arrive ici, ca veut dire qu'on a trouvé le plus court au ieme moment
						maxiDistIndice = i;
					} else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiDistIndice)[1])) {
						// si on arrive ici, ca veut dire que deux sommets ont la même distance donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
						String[] listNomCentre = fileAttente.get(i)[0].split("");
						String[] listMaxiNomCentre = fileAttente.get(maxiDistIndice)[0].split("");
						int nombre;
						int maxiNombre;
						if (listNomCentre.length == 2) {
							nombre = Integer.parseInt(listNomCentre[1]);
						} else {
							nombre = Integer.parseInt(listNomCentre[1] + listNomCentre[2]);
						}
						if (listMaxiNomCentre.length == 2) {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1]);
						} else {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1] + listMaxiNomCentre[2]);
						}
						if (maxiNombre > nombre) {
							maxiDistIndice = i;
						}
					}
				}
				sommetsTraites.put(fileAttente.get(maxiDistIndice)[0], true); // je marque le sommet qui va etre traité
				fileAttente.add(new String[]{fileAttente.get(maxiDistIndice)[0], String.valueOf(fileAttente.get(maxiDistIndice)[1])});
				fileAttente.remove(maxiDistIndice);
			}
		}
	}
	
	private void plusCourtCheminDijkstraDuree(Graphe graphe, String nomSommet) {
		mapPlusCourtCheminsDuree = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
		Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter
		mapPlusCourtCheminsDuree.put(nomSommet, new LinkedHashMap<>());
		mapPlusCourtCheminsDuree.get(nomSommet).put(nomSommet, 0.0);//le premier centre(sommet) a une durée de 0
		//on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
		try {
			graphe.tousLesSommetToList().forEach(Centre -> {
				sommetsTraites.put(Centre.getNom(), false);
			});
		} catch (Exception e) {
			System.out.println("liste vide !");
		}
		sommetsTraites.put(nomSommet, true);//le premier centre est le debut donc il traiter -> true
		List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
		fileAttente.add(new String[]{nomSommet, "0"});//ajoute le premier sommet avec comme durée 0
		String[] donnee;
		while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide
			donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la durée et le centre de la fin de la file dans la variable donnee
			fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element
			String centre = donnee[0];//donne[0] vaut le nom du centre
			double duree = Double.parseDouble(donnee[1]);//donne[1] vaut la durée de type String convertie en double
			
			Graphe.MaillonGrapheSec voisin = graphe.getSommet(centre).getVoisin();
			while (voisin != null) {
				String nomVoisin = voisin.getDestination().getNom();
				if (!sommetsTraites.get(nomVoisin)) {
					if (!mapPlusCourtCheminsDuree.containsKey(nomVoisin)) {
						// On arrive à cette endroit si nomVoisin n'a pas encore de chemin
						mapPlusCourtCheminsDuree.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsDuree.get(centre)));
						mapPlusCourtCheminsDuree.get(nomVoisin).put(nomVoisin, voisin.getDuree() + duree);
						fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDuree() + duree)});
						
					} else {
						// si nomVoisin a déjà un chemin
						LinkedHashMap<String, Double> chemin = mapPlusCourtCheminsDuree.get(nomVoisin);
						Double lastDurCentreDansChemin = null;
						String lastNomCentreDansChemin = null;
						for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
							// Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
							lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
							lastDurCentreDansChemin = centreChemin.getValue(); // la durée de l'extrémité
						}
						if (lastDurCentreDansChemin > voisin.getDuree() + duree) {
							// Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus court à ce moment la
							mapPlusCourtCheminsDuree.put(nomVoisin, new LinkedHashMap<>(mapPlusCourtCheminsDuree.get(donnee[0])));
							mapPlusCourtCheminsDuree.get(nomVoisin).put(lastNomCentreDansChemin, voisin.getDuree() + duree);
							// Donc on créer le nouveau chemin le plus court entre centre1 et nomVoisin
							
							// Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
							// Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle durée
							int i = 0;
							boolean check = false;
							while (!check && i < fileAttente.size()) {
								if (fileAttente.get(i)[0].equals(nomVoisin)) {
									check = true;
									fileAttente.remove(i);
									fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDuree() + duree)});
								}
								i++;
							}
						}
						
					}
				}
				voisin = voisin.getSuivantMaillonSec();
			}
			if (fileAttente.size() >= 2) {
				// si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus court tout en haut de la liste pour la traité en premier
				int maxiDurIndice = 0;
				for (int i = 1; i < fileAttente.size(); i++) {
					if (Double.parseDouble(fileAttente.get(i)[1]) < Double.parseDouble(fileAttente.get(maxiDurIndice)[1])) {
						// si on arrive ici, ca veut dire qu'on a trouvé le plus court au ieme moment
						maxiDurIndice = i;
					} else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiDurIndice)[1])) {
						// si on arrive ici, ca veut dire que deux sommets ont la même distance donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
						String[] listNomCentre = fileAttente.get(i)[0].split("");
						String[] listMaxiNomCentre = fileAttente.get(maxiDurIndice)[0].split("");
						int nombre;
						int maxiNombre;
						if (listNomCentre.length == 2) {
							nombre = Integer.parseInt(listNomCentre[1]);
						} else {
							nombre = Integer.parseInt(listNomCentre[1] + listNomCentre[2]);
						}
						if (listMaxiNomCentre.length == 2) {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1]);
						} else {
							maxiNombre = Integer.parseInt(listMaxiNomCentre[1] + listMaxiNomCentre[2]);
						}
						if (maxiNombre > nombre) {
							maxiDurIndice = i;
						}
					}
				}
				sommetsTraites.put(fileAttente.get(maxiDurIndice)[0], true); // je marque le sommet qui va etre traité
				fileAttente.add(new String[]{fileAttente.get(maxiDurIndice)[0], String.valueOf(fileAttente.get(maxiDurIndice)[1])});
				fileAttente.remove(maxiDurIndice);
			}
		}
	}
	
	public LinkedHashMap<String, Double> getCheminsFiabiliteTo(String nomSommet) {
		if (!(mapPlusCourtCheminsFiabilite.containsKey(nomSommet))) {
			return null;
		}
		return mapPlusCourtCheminsFiabilite.get(nomSommet);
	}
	
	public LinkedHashMap<String, Double> getCheminsDistanceTo(String nomSommet) {
		if (!(mapPlusCourtCheminsDistance.containsKey(nomSommet))) {
			return null;
		}
		return mapPlusCourtCheminsDistance.get(nomSommet);
	}
	
	public LinkedHashMap<String, Double> getCheminsDureeTo(String nomSommet) {
		if (!(mapPlusCourtCheminsDuree.containsKey(nomSommet))) {
			return null;
		}
		return mapPlusCourtCheminsDuree.get(nomSommet);
	}
	
	public String getSommetDepart() {
		return sommetDepart;
	}
	
}
