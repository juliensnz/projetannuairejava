package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import exceptions.RelaisException;

public class Relais implements Comparable<Relais> {

	/**
	 * Nom du relais
	 */
	private String			nom;

	/**
	 * Abscisse du relais
	 */
	private int				x;

	/**
	 * Ordonnée du relais
	 */
	private int				y;

	/**
	 * Identifiant unique d'un relais
	 */
	private static int		id			= 0;

	/**
	 * Liste des services d'un relais
	 */
	private List<Service>	services	= null;

	/**
	 * Constructeur de la classe Relais. Il prends en paramètre les coordonnées
	 * d'un relais et son nom. Deux contrôles sont effectués, et déclenchent une
	 * exception en cas d'échec. On vérifie que les coordonnées sont positives
	 * et que le nom est non-vide. Si c'est le cas, on initialise les champ du
	 * relais avec les paramètres, et on incrémente l'identifiant.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonnée du relais
	 * @param nom
	 *            Nom du relais
	 * @throws RelaisException
	 */
	public Relais(int x, int y, String nom) throws RelaisException {
		if (x < 0 || y < 0) throw new RelaisException("La position d'un relais ne peut être négative !");
		else if (nom.isEmpty()) throw new RelaisException("Le nom d'un relais ne peut pas être vide !");
		else {
			this.x = x;
			this.y = y;
			this.nom = nom;
			Relais.id++;
			this.services = new ArrayList<Service>();
		}
	}

	/**
	 * Cette méthode crée un nouveau service à partir du nom passée en paramètre
	 * et tente de l'ajouter à la liste. Des exceptions sont lancées si le nom
	 * du service est vide ou bien si le service existe déjà dans la liste. Si
	 * tout se passe bien, le service est ajouté à la liste.
	 * 
	 * @param nomService
	 *            Nom du service à ajouter
	 * @throws RelaisException
	 * @see Service#Service(String)
	 */
	public void ajouterService(String nomService) throws RelaisException {
		boolean contient = false;
		if (!nomService.isEmpty()) {
			Service nouvService = new Service(nomService);
			for (Service s : this.services)
				if (s.equals(nouvService)) {
					contient = true;
					throw new RelaisException("Le service existe déjàˆ");
				}
			if (!contient) this.services.add(nouvService);
		}
		else throw new RelaisException("Le nom du service est vide");
	}

	/**
	 * Ajouter une liste de services à un relais. Pour chaque service de la
	 * liste, on appelle la méthode {@link Relais#ajouterService(String)}
	 * 
	 * @param l
	 *            Liste de services à ajouter
	 * @throws RelaisException
	 */
	public void ajouterService(List<String> l) throws RelaisException {
		for (String s : l)
			this.ajouterService(s);
	}

	/**
	 * Retire le service passé en paramètre du relais. On parcours la liste des
	 * services, et on supprime les services qui ont le même nom que le service
	 * passé en paramètre.
	 * 
	 * @param s
	 *            Service à supprimer
	 */
	public void retirerService(Service s) {
		for (Service si : this.services)
			if (si.getNom().equals(s.getNom())) {
				this.services.remove(si);
				return;
			}
	}

	/**
	 * Prends en paramètre un nom de service et renvoie vrai si le relais le
	 * propose, faux sinon
	 * 
	 * @param nom
	 *            Nom du service à rechercher
	 * @return Vrai si le service est présent, faux sinon
	 */
	public boolean contientService(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom)) return true;
		return false;
	}

	/**
	 * Retoune la distance euclidienne entre le relais et les coordonées passées
	 * en paramètre.
	 * 
	 * @param x
	 *            Abscisse du point
	 * @param y
	 *            Ordonnée du point
	 * @return Distance entre le relais et le point
	 */
	public double distance(int x, int y) {
		return Math.round(Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2)) * 100) / 100;
	}

	/**
	 * Cette méthode génère aléatoirement un relais. Le nom du relais est
	 * "Relais n°" suivi de l'identifiant unique du relais. Les coordonées sont
	 * aléatoires entre 0 et 100. Si la création du relais déclenche une
	 * exception, on renvoie null.
	 * 
	 * @return Le relais généré ou null en cas d'échec
	 */
	public static Relais genererRelais() {
		Relais nouveauRelais = null;
		int x = (int) Math.round(Math.random() * 100);
		int y = (int) Math.round(Math.random() * 100);
		String nom = "Relais n° " + Relais.getId();
		try {
			nouveauRelais = new Relais(x, y, nom);
			return nouveauRelais;
		}
		catch (RelaisException e) {
			return null;
		}

	}

	/**
	 * Prends en paramètre un entier et génère le nombre de relais
	 * correspondant. Si la génération aléatoire d'un ou plusieurs relais
	 * échoue, il se peut que la liste contienne en fait moins de relais que
	 * prévu
	 * 
	 * @param nbRelais
	 *            Nombre de relais à générer
	 * @return Liste de relais aléatoires
	 */
	public static List<Relais> genererRelais(int nbRelais) {
		List<Relais> l = new ArrayList<Relais>();
		for (int i = 0; i < nbRelais; i++) {
			Relais r = Relais.genererRelais();
			if (r != null) l.add(r);
		}
		return l;
	}

	/**
	 * L'ordre de comparaison des relais est l'ordre naturels des noms.
	 */
	public int compareTo(Relais o) {
		return this.getNom().compareTo(o.getNom());
	}

	/**
	 * Deux relai sont égaux s'ils ont la même position et les même services.
	 * 
	 * @param o
	 *            Relais à comparer
	 * @return Vrai si les 2 relais sont égaux, faux sinon
	 */
	public boolean equals(Relais o) {
		return this.x == o.x && this.y == o.y && this.equivalentService(o);
	}

	/**
	 * Teste l'égalité entre les listes de services de deux relais. Les deux
	 * listes sont triées et comparées. La méthode renvoie vrai si elle sont
	 * égales, faux sinon.
	 * 
	 * @param r
	 *            Relais à comparer
	 * @return Vrai si les deux relais proposent les même services, faux sinon
	 */
	public boolean equivalentService(Relais r) {
		Collections.sort(this.services);
		Collections.sort(r.services);
		return this.services.equals(r.services);
	}

	/**
	 * @return L'identifiant unique du relais
	 */
	public static int getId() {
		return Relais.id;
	}

	/**
	 * @return Le nom du relais
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @return L'abscisse du relais
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return L'ordonnée du relais
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return La taille de la liste de services (le nombre de services)
	 */
	public int getNbService() {
		return this.services.size();
	}

	/**
	 * @param i
	 *            Index du service voulu dans la liste
	 * @return Le service à l'index i dans la liste
	 */
	public Service getServices(int i) {
		return this.services.get(i);
	}

	/**
	 * @param nom
	 *            Nom du service voulu
	 * @return Le service correspondant au nom passé en paramètre s'il existe,
	 *         null sinon
	 */
	public Service getServices(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom)) return s;
		return null;
	}

	/**
	 * @return La liste des services proposés par le relais
	 */
	public List<Service> getServices() {
		return this.services;
	}

	/**
	 * Setter pour le nom d'un relais. Une exception est lancée et le nom reste
	 * inchangé si le nom passé en paramètre est vide.
	 * 
	 * @param nom
	 *            Nouveau nom du relais
	 * @throws RelaisException
	 */
	public void setNom(String nom) throws RelaisException {
		if (nom.isEmpty()) throw new RelaisException("Le nom d'un relais ne peux pas être vide");
		else this.nom = nom;
	}

	/**
	 * Changer la position d'un relais. Une exception est lancée si les
	 * coordonnées ne sont pas valides.
	 * 
	 * @param x
	 *            Nouvelle abscisse du relais
	 * @param y
	 *            Nouvelle ordonnée du relais
	 * @throws RelaisException
	 */
	public void setPosition(int x, int y) throws RelaisException {
		if (x <= 0 || y <= 0) throw new RelaisException("La position d'un relais ne peut être négative !");
		else {
			this.x = x;
			this.y = y;
		}
	}
}
