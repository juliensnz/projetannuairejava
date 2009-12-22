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
	 * Ordonn�e du relais
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
	 * Constructeur de la classe Relais. Il prends en param�tre les coordonn�es
	 * d'un relais et son nom. Deux contr�les sont effectu�s, et d�clenchent une
	 * exception en cas d'�chec. On v�rifie que les coordonn�es sont positives
	 * et que le nom est non-vide. Si c'est le cas, on initialise les champ du
	 * relais avec les param�tres, et on incr�mente l'identifiant.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonn�e du relais
	 * @param nom
	 *            Nom du relais
	 * @throws RelaisException
	 */
	public Relais(int x, int y, String nom) throws RelaisException {
		if (x < 0 || y < 0) throw new RelaisException("La position d'un relais ne peut �tre n�gative !");
		else if (nom.isEmpty()) throw new RelaisException("Le nom d'un relais ne peut pas �tre vide !");
		else {
			this.x = x;
			this.y = y;
			this.nom = nom;
			Relais.id++;
			this.services = new ArrayList<Service>();
		}
	}

	/**
	 * Cette m�thode cr�e un nouveau service � partir du nom pass�e en param�tre
	 * et tente de l'ajouter � la liste. Des exceptions sont lanc�es si le nom
	 * du service est vide ou bien si le service existe d�j� dans la liste. Si
	 * tout se passe bien, le service est ajout� � la liste.
	 * 
	 * @param nomService
	 *            Nom du service � ajouter
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
					throw new RelaisException("Le service existe d��j��");
				}
			if (!contient) this.services.add(nouvService);
		}
		else throw new RelaisException("Le nom du service est vide");
	}

	/**
	 * Ajouter une liste de services � un relais. Pour chaque service de la
	 * liste, on appelle la m�thode {@link Relais#ajouterService(String)}
	 * 
	 * @param l
	 *            Liste de services � ajouter
	 * @throws RelaisException
	 */
	public void ajouterService(List<String> l) throws RelaisException {
		for (String s : l)
			this.ajouterService(s);
	}

	/**
	 * Retire le service pass� en param�tre du relais. On parcours la liste des
	 * services, et on supprime les services qui ont le m�me nom que le service
	 * pass� en param�tre.
	 * 
	 * @param s
	 *            Service � supprimer
	 */
	public void retirerService(Service s) {
		for (Service si : this.services)
			if (si.getNom().equals(s.getNom())) {
				this.services.remove(si);
				return;
			}
	}

	/**
	 * Prends en param�tre un nom de service et renvoie vrai si le relais le
	 * propose, faux sinon
	 * 
	 * @param nom
	 *            Nom du service � rechercher
	 * @return Vrai si le service est pr�sent, faux sinon
	 */
	public boolean contientService(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom)) return true;
		return false;
	}

	/**
	 * Retoune la distance euclidienne entre le relais et les coordon�es pass�es
	 * en param�tre.
	 * 
	 * @param x
	 *            Abscisse du point
	 * @param y
	 *            Ordonn�e du point
	 * @return Distance entre le relais et le point
	 */
	public double distance(int x, int y) {
		return Math.round(Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2)) * 100) / 100;
	}

	/**
	 * Cette m�thode g�n�re al�atoirement un relais. Le nom du relais est
	 * "Relais n�" suivi de l'identifiant unique du relais. Les coordon�es sont
	 * al�atoires entre 0 et 100. Si la cr�ation du relais d�clenche une
	 * exception, on renvoie null.
	 * 
	 * @return Le relais g�n�r� ou null en cas d'�chec
	 */
	public static Relais genererRelais() {
		Relais nouveauRelais = null;
		int x = (int) Math.round(Math.random() * 100);
		int y = (int) Math.round(Math.random() * 100);
		String nom = "Relais n� " + Relais.getId();
		try {
			nouveauRelais = new Relais(x, y, nom);
			return nouveauRelais;
		}
		catch (RelaisException e) {
			return null;
		}

	}

	/**
	 * Prends en param�tre un entier et g�n�re le nombre de relais
	 * correspondant. Si la g�n�ration al�atoire d'un ou plusieurs relais
	 * �choue, il se peut que la liste contienne en fait moins de relais que
	 * pr�vu
	 * 
	 * @param nbRelais
	 *            Nombre de relais � g�n�rer
	 * @return Liste de relais al�atoires
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
	 * Deux relai sont �gaux s'ils ont la m�me position et les m�me services.
	 * 
	 * @param o
	 *            Relais � comparer
	 * @return Vrai si les 2 relais sont �gaux, faux sinon
	 */
	public boolean equals(Relais o) {
		return this.x == o.x && this.y == o.y && this.equivalentService(o);
	}

	/**
	 * Teste l'�galit� entre les listes de services de deux relais. Les deux
	 * listes sont tri�es et compar�es. La m�thode renvoie vrai si elle sont
	 * �gales, faux sinon.
	 * 
	 * @param r
	 *            Relais � comparer
	 * @return Vrai si les deux relais proposent les m�me services, faux sinon
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
	 * @return L'ordonn�e du relais
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
	 * @return Le service � l'index i dans la liste
	 */
	public Service getServices(int i) {
		return this.services.get(i);
	}

	/**
	 * @param nom
	 *            Nom du service voulu
	 * @return Le service correspondant au nom pass� en param�tre s'il existe,
	 *         null sinon
	 */
	public Service getServices(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom)) return s;
		return null;
	}

	/**
	 * @return La liste des services propos�s par le relais
	 */
	public List<Service> getServices() {
		return this.services;
	}

	/**
	 * Setter pour le nom d'un relais. Une exception est lanc�e et le nom reste
	 * inchang� si le nom pass� en param�tre est vide.
	 * 
	 * @param nom
	 *            Nouveau nom du relais
	 * @throws RelaisException
	 */
	public void setNom(String nom) throws RelaisException {
		if (nom.isEmpty()) throw new RelaisException("Le nom d'un relais ne peux pas ��tre vide");
		else this.nom = nom;
	}

	/**
	 * Changer la position d'un relais. Une exception est lanc�e si les
	 * coordonn�es ne sont pas valides.
	 * 
	 * @param x
	 *            Nouvelle abscisse du relais
	 * @param y
	 *            Nouvelle ordonn�e du relais
	 * @throws RelaisException
	 */
	public void setPosition(int x, int y) throws RelaisException {
		if (x <= 0 || y <= 0) throw new RelaisException("La position d'un relais ne peut �tre n��gative !");
		else {
			this.x = x;
			this.y = y;
		}
	}
}
