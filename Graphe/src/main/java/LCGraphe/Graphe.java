package LCGraphe;

import Exception.CentreException;
import Exception.VoisinException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * @author Rafik Bouchenna et Emmanuel Ardoin
 */
public class Graphe {
	
	private Map<String, Dijkstra> cheminDijkstra;
	private FloydWarshall f = new FloydWarshall(this);
	
	
	public class MaillonGrapheSec {
		
		private double fiab;
		private double dist;
		private double dur;
		private String dest;
		private MaillonGrapheSec suiv;
		
		private MaillonGrapheSec(double fiabilite, double distance, double duree, String d) {
			fiab = fiabilite;
			dist = distance;
			dur = duree;
			dest = d;
			suiv = null;
		}
		
		/**
		 * @return String: destination
		 */
		public String getDestination() {
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
		public void setDestination(String dest) {
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
	
	public class MaillonGraphe {
		
		private String nom;
		private String type;
		private MaillonGrapheSec lVois;
		private MaillonGraphe suiv;
		
		
		MaillonGraphe(String n, String t) {
			nom = n;
			type = t;
			lVois = null;
			suiv = null;
			
		}
		
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
		 * @return LinkedList<MaillonGrapheSec>
		 */
		public ArrayList<MaillonGrapheSec> voisinsToList() {
			ArrayList<MaillonGrapheSec> listeMaillon = new ArrayList<>();
			MaillonGrapheSec tmp = this.lVois;
			while (tmp != null) {
				listeMaillon.add(tmp);
				tmp = tmp.getSuivantMaillonSec();
			}
			assert listeMaillon != null;
			return listeMaillon;
		}
		
		/**
		 * @param nomVoisin
		 * @return voisin
		 */
		public MaillonGrapheSec getVoisin(String nomVoisin) {
			MaillonGrapheSec voisin = null;
			MaillonGrapheSec tmp = this.lVois;
			while (voisin == null && tmp != null) {
				if (tmp.getDestination().equals(nomVoisin)) {
					voisin = tmp;
				}
				tmp = tmp.getSuivantMaillonSec();
			}
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
				if (tmp.getDestination().equals(nomVoisin)) {
					verife = true;
				}
				tmp = tmp.getSuivantMaillonSec();
			}
			return verife;
		}
		
		/**
		 * Renvoie une chaine contenant les voisins
		 *
		 * @return String : s
		 */
		public String voisinsToString() {
			MaillonGrapheSec tmp = this.lVois;
			StringBuilder s = new StringBuilder();
			while (tmp != null) {
				s.append("Destination : ").append(tmp.getDestination()).append(" [Fiabilité : ").append(tmp.getFiabilite() * 10).append("%, Distance : ").append(tmp.getDistance()).append("Km, Durée : ").append(tmp.getDuree()).append(" minutes]\n");
				tmp = tmp.getSuivantMaillonSec();
				
			}
			return s.toString();
		}
		
	}
	
	private MaillonGraphe premier;
	
	public Graphe() {
		premier = null;
	}
	
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
	 */
	public void ajoutCentre(String nomCentre, String typeCentre) {
		MaillonGraphe nouv = new MaillonGraphe(nomCentre, typeCentre);
		
		nouv.suiv = this.getPremier();
		this.premier = nouv;
		
	}
	
	/**
	 * @param centre1
	 * @param centre2
	 * @see {@link #getCentre(String)} , une methode qui permet de chercher
	 * le maillon grace a son nom passer en parametre
	 */
	public String voisinsVoisinsToString(String centre1, String centre2) {
		StringBuilder chaineVoisin = new StringBuilder();
		List<String> voisins1 = new LinkedList<>();
		List<String> voisins2 = new LinkedList<>();
		MaillonGraphe maillonCentre1 = getCentre(centre1);
		MaillonGraphe maillonCentre2 = getCentre(centre2);
		
		MaillonGrapheSec tmp1 = maillonCentre1.lVois;
		while (tmp1 != null) {
			voisins1.add(tmp1.getDestination());
			tmp1 = tmp1.getSuivantMaillonSec();
		}
		MaillonGrapheSec tmp2 = maillonCentre2.lVois;
		while (tmp2 != null) {
			voisins2.add(tmp2.getDestination());
			tmp2 = tmp2.getSuivantMaillonSec();
		}
		List<String> voisinsCommuns = new ArrayList<>(voisins1);
		voisinsCommuns.retainAll(voisins2);//ne retiens que les elements communs au deux liste
		
		boolean distance2 = false;
		
		for (String voisin : voisinsCommuns) {
			MaillonGraphe maillonVoisin = getCentre(voisin);
			MaillonGrapheSec tmp3 = maillonVoisin.lVois;
			while (tmp3 != null) {
				if (voisins1.contains(tmp3.getDestination()) && voisins2.contains(tmp3.getDestination())) {
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
	
	
	
	/**
	 * Cette methode ajoute les Voisins(Arretes) avec
	 * les parametres donner en entrée de la methode
	 *
	 * @param nomCentre
	 * @param nomDestinataire
	 * @param fiab
	 * @param dist
	 * @param dur
	 */
	public void ajoutVoisin(String nomCentre, String nomDestinataire, Double fiab, Double dist, Double dur) {
		MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, nomDestinataire);
		MaillonGraphe tmp = this.getPremier();
		while (!tmp.getNom().equals(nomCentre)) {
			tmp = tmp.getSuivant();
		}
		nouv.suiv = tmp.lVois;
		tmp.lVois = nouv;
		
		MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, nomCentre);
		tmp = this.getPremier();
		while (!tmp.getNom().equals(nomDestinataire)) {
			tmp = tmp.getSuivant();
		}
		nouv2.suiv = tmp.lVois;
		tmp.lVois = nouv2;
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
		while (!tmp.getNom().equals(nomCentre)) {
			tmp = tmp.getSuivant();
			if (tmp == null) {
				throw new CentreException("Le centre " + nomCentre + " n'existe pas!");
			}
		}
		tmp2 = tmp.lVois;
		while (!check && tmp2 != null) {
			if (tmp2.getDestination().equals(nomDestinataire)) {
				tmp2.setDistance(dist);
				tmp2.setDuree(dur);
				tmp2.setFiabilite(fiab);
				check = true;
			} else {
				tmp2 = tmp2.getSuivantMaillonSec();
			}
		}
		
		if (tmp2 == null) {
			throw new VoisinException("L'arete n'existe pas !");
		} // regarde si l'arete existe
		
		while (!tmp3.getNom().equals(nomDestinataire)) {
			tmp3 = tmp3.getSuivant();
			if (tmp3 == null) {
				throw new CentreException("Le sommet " + nomDestinataire + " n'existe pas!");
			}
		}
		check = false;
		tmp2 = tmp3.getVoisin();
		while (!check && tmp2 != null) {
			if (tmp2.getDestination().equals(nomCentre)) {
				tmp2.setDistance(dist);
				tmp2.setDuree(dur);
				tmp2.setFiabilite(fiab);
				check = true;
			}
			tmp2 = tmp2.getSuivantMaillonSec();
		}
	}
	
	/**
	 * Cette methode renvoie true si il existe une arrete sinon elle renvoie
	 * false
	 *
	 * @param nomCentre
	 * @param nomDestinataire
	 * @return verifeExistence : boolean
	 */
	public boolean existeVoisin(String nomCentre, String nomDestinataire) {
		boolean verifeExistence = false;
		MaillonGraphe tmp = this.getPremier();
		MaillonGrapheSec tmp2 = null;
		while (!tmp.getNom().equals(nomCentre)) {
			tmp = tmp.getSuivant();
		}
		tmp2 = tmp.lVois;
		while (!verifeExistence && tmp2 != null) {
			if (tmp2.getDestination().equals(nomDestinataire)) {
				verifeExistence = true;
			}
			tmp2 = tmp2.getSuivantMaillonSec();
		}
		return verifeExistence;
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
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null && !tmp.getNom().equals(nomCentre)) {
			tmp = tmp.getSuivant();
		}
		return (tmp != null);
	}
	
	/**
	 * Cette methode renvoie tout les sommet en les affichant
	 *
	 * @return String : chaineCentres
	 */
	public String tousLesCentresToString() {
		StringBuilder chaineCentres = new StringBuilder();
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			chaineCentres.append(tmp.getNom()).append(" [").append(tmp.getType()).append("]\n");
			tmp = tmp.getSuivant();
		}
		return chaineCentres.toString();
	}
	
	/**
	 * @return ensembleCentre : LinkedList<MaillonGraphe>
	 */
	public List<MaillonGraphe> tousLesCentresToList() {
		List<MaillonGraphe> ensembleCentre = new LinkedList<>();
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			ensembleCentre.add(tmp);
			tmp = tmp.getSuivant();
		}
		return ensembleCentre;
	}
	
	/**
	 * @return List : MaillonGrapheSec
	 */
	public List<MaillonGrapheSec> toutesLesAretesToList() {
		List<MaillonGrapheSec> ensembleArrete = new LinkedList<>();
		MaillonGraphe tmp = this.getPremier();
		MaillonGrapheSec tmp2 = null;
		while (tmp != null) {
			tmp2 = tmp.getVoisin();
			while (tmp2 != null) {
				if (ensembleArrete.contains(tmp2)) {
					tmp2 = tmp2.getSuivantMaillonSec();
				}
			}
			tmp = tmp.getSuivant();
		}
		return ensembleArrete;
	}
	
	/**
	 * Cette methode renvoie tout les blocs sous formes de chaine de caraceteres
	 *
	 * @return String : ensembleBloc
	 */
	public String tousLesBlocsToString() {
		StringBuilder ensembleBloc = new StringBuilder();
		MaillonGraphe tmp = this.getPremier();
		while (tmp != null) {
			if (tmp.type.equals("O")) {
				ensembleBloc.append(tmp.nom).append("\n");
			}
			tmp = tmp.getSuivant();
		}
		return ensembleBloc.toString();
	}
	
	/**
	 * Cette methode recupere le centre avec son nom en parametre
	 *
	 * @param nomCentre
	 * @return MaillonGraphe : centreRechercher
	 */
	public MaillonGraphe getCentre(String nomCentre) {
		MaillonGraphe tmp = this.premier;
		MaillonGraphe centreRechercher = null;
		while (centreRechercher == null && tmp != null) {
			if (tmp.nom.equals(nomCentre)) {
				centreRechercher = tmp;
			}
			tmp = tmp.getSuivant();
		}
		return centreRechercher;
	}
	
	/**
	 * Cette methode permet de lire le fichier grace au chemin specifier en parametre
	 *
	 * @param nomFichierChoisi : String
	 */
	public void chargementFichier(String nomFichierChoisi) {
		try {
			File file = new File(nomFichierChoisi);
			Scanner scanner = new Scanner(file);
			int compteurLigne = 0;
			Map<Integer, List> hashMapArreteTraite = new HashMap<>();
			List<String> listArreteDonnee;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (compteurLigne >= 5) { // permet de pas prendre les premiere lignes d'explications
					String[] parts = line.split(";");
					
					String nom = parts[0]; // prends le nom du sommet
					String type = parts[1];// prends le type du sommet
					switch (type) {
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
					ajoutCentre(nom, type); // et on ajoute le sommet au Graphe
					
					for (int i = 2; i < parts.length; i++) { // parcours toutes les arretes du sommet actuel
						if (!parts[i].equals("0")) {
							listArreteDonnee = new LinkedList<>();
							if (hashMapArreteTraite.containsKey(i)) { // cette ligne permet de voir si ya une arrete complete a été détecté
								ajoutVoisin(hashMapArreteTraite.get(i).get(0).toString(), "S" + (compteurLigne - 4), Double.parseDouble((String) hashMapArreteTraite.get(i).get(1)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(2)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(3)));
								hashMapArreteTraite.remove(i);
							} else {
								String[] edgeValues = parts[i].split(",");
								listArreteDonnee.add(nom);
								listArreteDonnee.addAll(Arrays.asList(edgeValues));
								hashMapArreteTraite.put(i, listArreteDonnee);
							}
							
						}
					}
				}
				compteurLigne++;
			}
			scanner.close();
			f.floydWarshallFiabilite();
			cheminDijkstra = new HashMap<>();
			this.tousLesCentresToList().forEach(maillonGraphe -> {
				cheminDijkstra.put(maillonGraphe.getNom(), new Dijkstra(this, maillonGraphe.getNom()));
				
				
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
		MaillonGraphe tmp = this.premier;
		while (tmp != null) {
			chaine.append("Nom: ").append(tmp.nom).append(" | Type: ").append(tmp.type).append("\n");
			MaillonGrapheSec tmp2 = tmp.lVois;
			while (tmp2 != null) {
				chaine.append("Fiabilité: ").append(tmp2.fiab).append(" | Distance: ").append(tmp2.dist).append(" | Durée: ").append(tmp2.dur).append(" | Destination: ").append(tmp2.dest).append("\n");
				tmp2 = tmp2.suiv;
			}
			tmp = tmp.getSuivant();
		}
		return chaine.toString();
	}
	
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
	
}


