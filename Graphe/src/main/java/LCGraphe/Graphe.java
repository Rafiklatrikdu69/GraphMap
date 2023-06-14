package LCGraphe;

import Exception.CentreException;
import Exception.VoisinException;
import Exception.ListeCentresNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * @author Rafik Bouchenna et Emmanuel Ardoin
 */
public class Graphe {
	
	private Map<String, Dijkstra> cheminDijkstra;//stocke tous les algos djikstra
	private FloydWarshall f = new FloydWarshall(this);//algo de recherche du chemin le plus fiable
	
	public Map<String, Dijkstra> getCheminDijkstra() {
		return cheminDijkstra;
	}
	
	public List<String> getListeSommetCheminGraphe() {
		return f.getSommet();
	}
	
	public List<Double> getSommetDonnees() {
		return f.getSommetDonnees();
	}
	
	public void rechercheChemin(String depart, String destination) {
		f.rechercheChemin(depart, destination);
	}
	
	
	//Liste Principale
	public class MaillonGraphe {
		
		private String nom;//nom du sommet
		private String type;//type du centre
		private MaillonGrapheSec lVois;//pointeur vers le maillon de liste secondaire
		private MaillonGraphe suiv;//pointeur vers le maillon suivant de la liste
		
		//Constructeur du maillon principale
		MaillonGraphe(String n, String t) {
			nom = n;
			type = t;
			lVois = null;
			suiv = null;
			
		}
		
		/**
		 * Getters et setters
		 **/
		public MaillonGrapheSec getVoisin() {
			return this.lVois;
		}
		
		public MaillonGraphe getSuivant() {
			return this.suiv;
		}
		
		/**
		 * @return String : nom du sommet
		 */
		public String getNom() {
			return nom;
		}
		
		/**
		 * @return String : type de sommet
		 */
		public String getType() {
			return type;
		}
		
		/**
		 * Cette methode renvoie une liste de voisin
		 *
		 * @return List<MaillonGrapheSec>
		 */
		public List<MaillonGrapheSec> voisinsToList() {
			List<MaillonGrapheSec> listeMaillon = new ArrayList<>();
			MaillonGrapheSec tmp = this.lVois;
			while (tmp != null) {
				listeMaillon.add(tmp);//ajoute les voisins a la liste
				tmp = tmp.getSuivantMaillonSec();
			}
			assert listeMaillon != null;//verifie si la liste est null -> test unitaires
			return listeMaillon;//renvoie la liste
		}
		
		/**
		 * @param nomVoisin
		 * @return voisin
		 */
		public MaillonGrapheSec getVoisin(String nomVoisin) {
			MaillonGrapheSec voisin = null;
			MaillonGrapheSec tmp = this.lVois;//pointe vers le maillon de la liste secondaires
			while (voisin == null && tmp != null) {
				if (tmp.getDestination().getNom().equals(nomVoisin)) {//si le voisin qui est le destinataire est egale au nom passer en parametre
					voisin = tmp;
				}
				tmp = tmp.getSuivantMaillonSec();
			}
			assert voisin == null;
			return voisin;
			
		}
		
		/**
		 * @param nomVoisin
		 * @return verife
		 */
		public boolean estVoisin(String nomVoisin) {
			boolean verife = false;
			MaillonGrapheSec tmp = this.lVois;
			while (!verife && tmp != null) {
				if (tmp.getDestination().getNom().equals(nomVoisin)) {
					verife = true;//verife = true si le sommet est voisin
				}
				tmp = tmp.getSuivantMaillonSec();//passe au maillon suivant de la liste secondaire
			}
			return verife;//renvoie le boolean
		}
		
		/**
		 * Renvoie une chaine contenant les voisins-> affiche les voisins
		 *
		 * @return String : s
		 */
		public String voisinsToString() {
			MaillonGrapheSec tmp = this.lVois;
			StringBuilder s = new StringBuilder();
			while (tmp != null) {
				s.append("Destination : ").append(tmp.getDestination().getNom()).append(" [Fiabilité : ").append(tmp.getFiabilite() * 10).append("%, Distance : ").append(tmp.getDistance()).append("Km, Durée : ").append(tmp.getDuree()).append(" minutes]\n");
				tmp = tmp.getSuivantMaillonSec();
				
			}
			return s.toString();
		}
	}
	
	/**
	 * Liste secondaire
	 **/
	public class MaillonGrapheSec {
		
		private double fiab;
		private double dist;
		private double dur;
		private MaillonGraphe dest;
		private MaillonGrapheSec suiv;//le maillon suivant de la liste secondaire
		
		private MaillonGrapheSec(double fiabilite, double distance, double duree, MaillonGraphe d) {
			fiab = fiabilite;
			dist = distance;
			dur = duree;
			dest = d;
			suiv = null;
		}
		/**Getters et setters**/
		
		
		/**
		 * @return String: destination
		 */
		public MaillonGraphe getDestination() {
			return dest;
		}
		
		/**
		 * @return double : distance
		 */
		public double getDistance() {
			return dist;
		}
		
		/**
		 * @return double : duree
		 */
		public double getDuree() {
			return dur;
		}
		
		/**
		 * @return double : fiabilite
		 */
		public double getFiabilite() {
			return fiab;
		}
		
		public MaillonGrapheSec getSuivantMaillonSec() {
			return this.suiv;
		}
		
		
		/**
		 * @param dest
		 */
		public void setDestination(MaillonGraphe dest) {
			this.dest = dest;
		}
		
		/**
		 * @param dist
		 */
		public void setDistance(double dist) {
			this.dist = dist;
		}
		
		/**
		 * @param duree
		 */
		public void setDuree(double duree) {
			this.dur = duree;
		}
		
		/**
		 * @param fiab
		 */
		public void setFiabilite(double fiab) {
			this.fiab = fiab;
		}
		
		public String toString() {
			StringBuilder chaine = new StringBuilder();
			chaine.append(this.dest).append(" [Fiabilité : ").append(this.fiab * 10).append("%, Distance : ").append(this.dist).append("Km, Durée : ").append(this.dur).append(" minutes]");
			return chaine.toString();
		}
	}
	
	private MaillonGraphe premier;//creer la tete
	
	public Graphe() {
		premier = null;
	}//au debut la tete est null
	
	/**
	 * @return premier : MaillonGraphe
	 */
	public MaillonGraphe getPremier() {
		return this.premier;
	}

	/**
	 * Cette methode ajoute au debut de la liste le sommet avec les parametres
	 *
	 * @param nomCentre
	 * @param typeCentre
	 * @see {@link  MaillonGraphe}
	 * return boolean
	 */
	public boolean ajoutCentre(String nomCentre, String typeCentre) {
		boolean resultat = false;
		if(!existeCentre(nomCentre)){ // verifie si le sommet existe deja ou pas
			//instancie un nouveau maillon
			MaillonGraphe nouv = new MaillonGraphe(nomCentre, typeCentre);
			nouv.suiv = this.getPremier();
			this.premier = nouv;//le nouveau maillon devient la tete de la liste
			resultat = true;
		}
		return resultat;
	}


	/**
	 * Cette methode ajoute les Voisins(Arretes) avec
	 * les parametres donner en entrée de la methode
	 *
	 * @param nomCentre
	 * @param nomDestinataire
	 * @param fiab
	 * @param dist
	 * @param dur
	 * @return boolean
	 */
	public boolean ajoutVoisin(String nomCentre, String nomDestinataire, Double fiab, Double dist, Double dur) {
		boolean resultat = false;
		if(existeCentre(nomCentre) && existeCentre(nomDestinataire)){
			if(!existeVoisin(nomCentre, nomDestinataire)){
				MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, getCentre(nomDestinataire));//cration du nouveau maillon de la seconde liste pour dire qu'il ya une arete
				MaillonGraphe tmp = this.getPremier();
				//parcourt de liste principale
				while (!tmp.getNom().equals(nomCentre)) {
					tmp = tmp.getSuivant();
				}
				//les aretes vont dans les deux sens -> allez/retour
				//ajout de l'arete pour le sommet source
				nouv.suiv = tmp.lVois;
				tmp.lVois = nouv;

				MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, getCentre(nomCentre));
				tmp = this.getPremier();
				while (!tmp.getNom().equals(nomDestinataire)) {
					tmp = tmp.getSuivant();
				}
				//ajout de l'arete pour le sommet destinataire
				nouv2.suiv = tmp.lVois;
				tmp.lVois = nouv2;
				resultat = true;
			}
		}

		return resultat;
	}
	
	/**
	 * @param centre1
	 * @param centre2
	 * @see {@link #getCentre(String)} , une methode qui permet de chercher
	 * le maillon grace a son nom passer en parametre
	 */
	public String voisinsVoisinsToString(String centre1, String centre2) {
		StringBuilder chaineVoisin = new StringBuilder();
		List<String> voisins1 = new LinkedList<>();//liste des voisin centre1
		List<String> voisins2 = new LinkedList<>();//liste des voisin centre2
		MaillonGraphe maillonCentre1 = getCentre(centre1);//
		MaillonGraphe maillonCentre2 = getCentre(centre2);
		
		MaillonGrapheSec tmp1 = maillonCentre1.lVois;
		//affecte tout les voisins du centre1 dans la liste voisin1
		while (tmp1 != null) {
			voisins1.add(tmp1.getDestination().getNom());
			tmp1 = tmp1.getSuivantMaillonSec();
		}
		MaillonGrapheSec tmp2 = maillonCentre2.lVois;
		//affecte tout les voisins du centre2 dans la liste voisin2
		while (tmp2 != null) {
			voisins2.add(tmp2.getDestination().getNom());
			tmp2 = tmp2.getSuivantMaillonSec();
		}
		List<String> voisinsCommuns = new ArrayList<>(voisins1);//creation de la liste pour les voisins en commun
		voisinsCommuns.retainAll(voisins2);//ne retiens que les elements communs au deux liste
		
		boolean distance2 = false;
		
		for (String voisin : voisinsCommuns) {
			MaillonGraphe maillonVoisin = getCentre(voisin);
			MaillonGrapheSec tmp3 = maillonVoisin.lVois;
			while (tmp3 != null) {
				if (voisins1.contains(tmp3.getDestination().getNom()) && voisins2.contains(tmp3.getDestination().getNom())) {
					chaineVoisin.append("Les sommets " + centre1 + " et " + centre2 + " sont à une distance de 2.");
					distance2 = true;
					break;
				}
				tmp3 = tmp3.getSuivantMaillonSec();
			}
			if (distance2) {
				break;
			}
		}
		if (!distance2) {
			chaineVoisin.append("Les sommets " + centre1 + " et " + centre2 + " ne sont pas à une distance de 2.");
		}
		return chaineVoisin.toString();
	}
	//En cours de developement !!!
	/*public List<MaillonGraphe> getVoisinPDistance(MaillonGraphe sommetDepart,int p){
		List<MaillonGraphe> listeMaillon = new ArrayList<>();
		MaillonGrapheSec voisinsSommetDepart = sommetDepart.getVoisin();
		MaillonGraphe sommetTemp;
		MaillonGrapheSec voisinTemp = null;
		assert p>=1;
		int i;
		if(p==1){
			while (voisinsSommetDepart !=null){
				listeMaillon.add(voisinsSommetDepart.getDestination());
				voisinsSommetDepart = voisinsSommetDepart.getSuivantMaillonSec();
			}
		} else {
			i=0;
			while (voisinsSommetDepart !=null){
				i = 0;
				sommetTemp = voisinsSommetDepart.getDestination();
				while(i<p){
					voisinTemp = sommetTemp.getVoisin();
					if (i==p-1){

					} else {
						voisinTemp = voisinTemp.getSuivantMaillonSec();
					}
					i++;
				}
				voisinTemp = sommetTemp.getVoisin();


				voisinsSommetDepart = voisinsSommetDepart.getSuivantMaillonSec();
			}
		}
		return listeMaillon;
	}
	*/
	
	/**
	 * methode qui compte le nombre de blocs operatoire
	 *
	 * @return
	 */
	
	public Integer getNombreOperatoire() {
		Integer res = 0;
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			if (tmp.type.equals("Opératoire")) {
				res++;
			}
			tmp = tmp.getSuivant();
		}
		return res;
	}
	
	/**
	 * methode qui compte le nombre de maternite
	 *
	 * @return
	 */
	public Integer getNombreMaternite() {
		Integer res = 0;
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			if (tmp.type.equals("Maternité")) {
				res++;
			}
			tmp = tmp.getSuivant();
		}
		return res;
	}
	
	/**
	 * methode qui compte le nombre de nutrition
	 *
	 * @return
	 */
	public Integer getNombreCentreNutrition() {
		Integer res = 0;
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			if (tmp.type.equals("Centre de nutrition")) {
				res++;
			}
			tmp = tmp.getSuivant();
		}
		return res;
	}
	
	/**
	 * methode qui compte le nombre de dispensaires totales
	 *
	 * @return
	 */
	public Integer getNombreDispensaire() {
		Integer res = 0;
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			res++;
			tmp = tmp.getSuivant();
		}
		return res;
	}
	
	/**
	 * methode qui compte le nombre d'aretes
	 *
	 * @return
	 */
	public Integer getNombreRoute() {
		Integer res = 0;
		MaillonGraphe tmp = this.getPremier();
		MaillonGrapheSec tmp2 = null;
		while (tmp != null) {
			tmp2 = tmp.getVoisin();
			while (tmp2 != null) {
				res++;
				tmp2 = tmp2.getSuivantMaillonSec();
			}
			tmp = tmp.getSuivant();
		}
		return res / 2;
	}
	
	/**
	 * Cette methode permet de modifier les donnee d'un voisin(Arrete) avec
	 * les parmatres donnés en entrée de la methode
	 *
	 * @param nomCentre
	 * @param nomDestinataire
	 * @param fiab
	 * @param dist
	 * @param dur
	 * @throws VoisinException
	 * @throws CentreException
	 */
	public void modifVoisin(String nomCentre, String nomDestinataire, double fiab, double dist, double dur) throws VoisinException, CentreException {
		MaillonGrapheSec tmp2 = null;
		MaillonGraphe tmp = this.getPremier();
		MaillonGraphe tmp3 = this.getPremier();
		boolean check = false;
		//recherche du maillon portant nomCentre
		while (!tmp.getNom().equals(nomCentre)) {
			tmp = tmp.getSuivant();
			if (tmp == null) {
				throw new CentreException("Le centre " + nomCentre + " n'existe pas!");//cas ou le sommet n'existe pas
			}
		}
		//a partir du maillon trouver on parcourt ca liste des voisins
		tmp2 = tmp.lVois;
		while (!check && tmp2 != null) {
			
			if (tmp2.getDestination().getNom().equals(nomDestinataire)) {//si cest trouver alors on fait les modifs
				tmp2.setDistance(dist);
				tmp2.setDuree(dur);
				tmp2.setFiabilite(fiab);
				check = true;
			} else {
				tmp2 = tmp2.getSuivantMaillonSec();//passe qu maillon suivant
			}
		}
		
		if (tmp2 == null) {
			throw new VoisinException("L'arete n'existe pas !");//cas ou l'arete n'existe pas
		} // regarde si l'arete existe
		
		//meme principe pour l'autre sens de l'arete
		while (!tmp3.getNom().equals(nomDestinataire)) {
			tmp3 = tmp3.getSuivant();
			if (tmp3 == null) {
				throw new CentreException("Le sommet " + nomDestinataire + " n'existe pas!");
			}
		}
		check = false;
		tmp2 = tmp3.getVoisin();
		while (!check && tmp2 != null) {
			if (tmp2.getDestination().getNom().equals(nomCentre)) {
				tmp2.setDistance(dist);
				tmp2.setDuree(dur);
				tmp2.setFiabilite(fiab);
				check = true;
			}
			tmp2 = tmp2.getSuivantMaillonSec();
		}
	}
	
	/**
	 * Cette methode renvoie true si il existe une arrete sinon elle renvoie false
	 *
	 *
	 * @param nomCentre
	 * @param nomDestinataire
	 * @return verifeExistence : boolean
	 */
	public boolean existeVoisin(String nomCentre, String nomDestinataire) {
		boolean verifeExistence = false; // Variable pour indiquer l'existence d'un voisin
		MaillonGraphe tmp = this.getPremier(); // Obtient le premier maillon du graphe
		MaillonGrapheSec tmp2 = null; // Variable pour parcourir les voisins du maillon
		while (!tmp.getNom().equals(nomCentre)) { // Recherche le maillon correspondant au nom du centre
			tmp = tmp.getSuivant();
		}
		tmp2 = tmp.lVois; // Obtient la liste des voisins du maillon
		while (!verifeExistence && tmp2 != null) { // Parcourt les voisins jusqu'à trouver le destinataire ou épuiser la liste
			if (tmp2.getDestination().getNom().equals(nomDestinataire)) { // Vérifie si le voisin correspond au destinataire
				verifeExistence = true; // Met à jour la variable d'existence
			}
			tmp2 = tmp2.getSuivantMaillonSec(); // Passe au voisin suivant
		}
		return verifeExistence; // Retourne le résultat d'existence du voisin
	}
	
	
	/**
	 * Cette methode renvoie true si le centre(sommet) existe sinon elle renvoie false
	 * <p>
	 * false
	 *
	 * @param nomCentre
	 * @return boolean : false ou true
	 * @see MaillonGraphe#getNom()
	 */
	public boolean existeCentre(String nomCentre) {
		MaillonGraphe tmp = this.getPremier(); // Obtient le premier maillon du graphe
		while (tmp != null && !tmp.getNom().equals(nomCentre)) { // Parcourt les maillons jusqu'à trouver le maillon correspondant ou épuiser la liste
			tmp = tmp.getSuivant();
		}
		assert tmp == null; // Vérifie qu'aucun maillon correspondant n'a été trouvé (assertion)
		return (tmp != null); // Retourne true si un maillon correspondant a été trouvé, sinon retourne false
	}
	
	
	/**
	 * Cette methode renvoie tout les sommet en les affichant
	 *
	 * @return String : chaineCentres
	 */
	public String tousLesCentresToString() {
		StringBuilder chaineCentres = new StringBuilder();
		MaillonGraphe tmp = this.getPremier();
		//affiche les sommets
		while (tmp != null) {
			chaineCentres.append(tmp.getNom()).append(" [").append(tmp.getType()).append("]\n");
			tmp = tmp.getSuivant();
		}
		return chaineCentres.toString();//renvoie une chaine de caractere
	}
	
	/**
	 * Cette methode affecte tout les centres(sommets) dans une liste
	 *
	 * @return ensembleCentre : List<MaillonGraphe>
	 */
	public List<MaillonGraphe> tousLesCentresToList() throws ListeCentresNull {
		List<MaillonGraphe> ensembleCentre = new LinkedList<>();
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			ensembleCentre.add(tmp);//ajout la liste
			tmp = tmp.getSuivant();
		}
		if (ensembleCentre == null) {
			throw new ListeCentresNull("la liste est nulle !");
		}
		return ensembleCentre;//renvoie la liste
	}
	
	/**
	 * @return List : MaillonGrapheSec
	 */
	public List<MaillonGrapheSec> toutesLesAretesToList() {
		List<MaillonGrapheSec> ensembleArrete = new LinkedList<>(); // Crée une liste vide pour stocker les arêtes
		MaillonGraphe tmp = this.getPremier(); // Obtient le premier maillon du graphe
		MaillonGrapheSec tmp2 = null; // Variable pour parcourir les arêtes des maillons
		while (tmp != null) { // Parcourt tous les maillons du graphe
			tmp2 = tmp.getVoisin(); // Obtient la première arête du maillon
			while (tmp2 != null) { // Parcourt les arêtes du maillon
				if (!ensembleArrete.contains(tmp2)) { // Vérifie si l'arête n'est pas déjà dans la liste
					ensembleArrete.add(tmp2); // Ajoute l'arête à la liste
				}
				tmp2 = tmp2.getSuivantMaillonSec(); // Passe à l'arête suivante
			}
			tmp = tmp.getSuivant(); // Passe au maillon suivant
		}
		return ensembleArrete; // Retourne la liste de toutes les arêtes
	}
	
	
	/**
	 * Cette methode renvoie tout les blocs sous formes de chaine de caraceteres
	 *
	 * @return String : ensembleBloc
	 */
	public String tousLesBlocsToString() {
		StringBuilder ensembleBloc = new StringBuilder(); // Crée un StringBuilder pour stocker les noms des blocs
		MaillonGraphe tmp = this.getPremier(); // Obtient le premier maillon du graphe
		while (tmp != null) { // Parcourt tous les maillons du graphe
			if (tmp.getType().equals("O")) { // Vérifie si le maillon est de type "O"
				ensembleBloc.append(tmp.getNom()).append("\n"); // Ajoute le nom du maillon suivi d'un saut de ligne dans le StringBuilder
			}
			tmp = tmp.getSuivant(); // Passe au maillon suivant
		}
		return ensembleBloc.toString(); // Retourne la chaîne de caractères contenant tous les noms de blocs
	}
	
	
	/**
	 * Cette methode recupere le centre avec son nom en parametre
	 *
	 * @param nomCentre
	 * @return MaillonGraphe : centreRechercher
	 */
	public MaillonGraphe getCentre(String nomCentre) {
		MaillonGraphe tmp = this.getPremier(); // Obtient le premier maillon du graphe
		MaillonGraphe centreRechercher = null; // Variable pour stocker le centre recherché
		while (centreRechercher == null && tmp != null) { // Parcourt les maillons jusqu'à trouver le centre recherché ou épuiser la liste
			if (tmp.getNom().equals(nomCentre)) { // Vérifie si le nom du maillon correspond au nom du centre recherché
				centreRechercher = tmp; // Stocke le maillon correspondant dans la variable centreRechercher
			}
			tmp = tmp.getSuivant(); // Passe au maillon suivant
		}
		return centreRechercher; // retourne  le centre recherché (ou null s'il n'a pas été trouvé)
	}
	
	
	/**
	 * Cette methode permet de lire le fichier grace au chemin passer en parametre
	 *
	 * @param nomFichierChoisi : String
	 */
	public void chargementFichier(String nomFichierChoisi) throws ListeCentresNull {
		try {
			File file = new File(nomFichierChoisi); // Crée un objet File à partir du nom de fichier choisi
			Scanner scanner = new Scanner(file); // Crée un objet Scanner pour lire le fichier
			int compteurLigne = 0; // Compteur pour suivre le numéro de ligne
			Map<Integer, List> hashMapArreteTraite = new HashMap<>(); // Map pour stocker les arêtes traitées
			List<String> listArreteDonnee; // Liste pour stocker les données d'une arête
			while (scanner.hasNextLine()) { // Tant qu'il y a des lignes à lire dans le fichier
				String line = scanner.nextLine(); // Lit une ligne du fichier
				if (compteurLigne >= 5) { // Permet de ne pas prendre les premières lignes d'explications
					String[] parts = line.split(";"); // Divise la ligne en parties en utilisant le délimiteur ';'
					
					String nom = parts[0]; // recupere le nom du sommet
					String type = parts[1]; // recupere le type du sommet
					switch (type) { // effectue une conversion du type du sommet
						case "M":
							type = "Maternité";
							break;
						case "N":
							type = "Centre de nutrition";
							break;
						case "O":
							type = "Opératoire";
							break;
					}
					ajoutCentre(nom, type); // Ajoute le sommet au graphe
					
					for (int i = 2; i < parts.length; i++) { // Parcourt toutes les arêtes du sommet actuel
						if (!parts[i].equals("0")) { // Vérifie si l'arête existe
							listArreteDonnee = new LinkedList<>(); // Crée une nouvelle liste pour les données de l'arête
							if (hashMapArreteTraite.containsKey(i)) { // Vérifie si une arête complète a été détectée précédemment
								// Ajoute l'arête entre le sommet précédent et le sommet actuel avec les données récupérées
								ajoutVoisin(hashMapArreteTraite.get(i).get(0).toString(), "S" + (compteurLigne - 4), Double.parseDouble((String) hashMapArreteTraite.get(i).get(1)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(2)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(3)));
								hashMapArreteTraite.remove(i); // Supprime l'arête de la map
							} else {
								String[] edgeValues = parts[i].split(","); // divise les valeurs de l'arête en utilisant le délimiteur ','
								listArreteDonnee.add(nom); // ajoute le nom du sommet actuel a la liste des données de l'arête
								listArreteDonnee.addAll(Arrays.asList(edgeValues)); // ajoute les autres valeurs de l'arête à la liste des données
								hashMapArreteTraite.put(i, listArreteDonnee); // Ajoute la liste des données de l'arête à la map
							}
						}
					}
				}
				compteurLigne++; // Incrémente le compteur de ligne
			}
			scanner.close(); // Ferme le scanner
			
			f.floydWarshallFiabilite(); // Lance l'algorithme pour les fiabilités
			
			cheminDijkstra = new HashMap<>(); // Crée une nouvelle map pour stocker les chemins de Dijkstra
			this.tousLesCentresToList().forEach(maillonGraphe -> { // Parcourt tous les centres du graphe
				cheminDijkstra.put(maillonGraphe.getNom(), new Dijkstra(this, maillonGraphe.getNom())); // Ajoute un objet Dijkstra pour chaque centre au map cheminDijkstra
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Cette methode parcourt la liste pour afficher le Graphe avec les sommets et les arretes
	 *
	 * @return String
	 */
	public String toString() {
		StringBuilder chaine = new StringBuilder();
		MaillonGraphe tmp = this.getPremier();//recupere le premier maillon de la liste principal
		while (tmp != null) {
			chaine.append("Nom: ").append(tmp.getNom()).append(" | Type: ").append(tmp.getType()).append("\n");//affiche les donnes du sommets
			MaillonGrapheSec tmp2 = tmp.getVoisin();//pointeur vers le maillon de la seconde liste chainée
			while (tmp2 != null) {//On parcourt la liste des voisins
				chaine.append("Fiabilité: ").append(tmp2.getFiabilite()).append(" | Distance: ").append(tmp2.getDistance()).append(" | Durée: ").append(tmp2.getDuree()).append(" | Destination: ").append(tmp2.getDestination().getNom()).append("\n");
				tmp2 = tmp2.getSuivantMaillonSec();//passe au maillon suivant
			}
			tmp = tmp.getSuivant();//passe au maillon suivant de la liste principal
		}
		return chaine.toString();//renvoie la  chaine String
	}
	
	
}


