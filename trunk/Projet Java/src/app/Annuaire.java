package app;

import java.util.HashMap;
import java.util.Map;
import exceptions.RelaisException;

/**
 * La classe annuaire est dédiée à la gestion d'un annuaire de Relais, elle
 * propose l'ensemble des outils dédiés à la création, modification et
 * suppression de ceux-ci.
 * 
 * @author Bury Maxime, Julien Sanchez
 * @see app.Relais
 * @see app.Service
 */
public class Annuaire {

	private Map<String, Relais>	annuaireRelais	= null;

	/**
	 * Constructeur de la classe Annuaire. Il se charge simplement d'initialiser
	 * la table associative des relais. Cette table est déclarée en tant que
	 * collection de type Map. Elle est instanciée par le constructeur avec
	 * l'implémentation HashMap. La clé peut donc éventuellement être nulle, le
	 * contôle est effectué à la création d'un relais, et donc avant
	 * l'insertion.
	 */
	public Annuaire() {
		this.annuaireRelais = new HashMap<String, Relais>();
	}

	/**
	 * Prends en paramètre l'abscisse, l'ordonnée et le nom d'un relais et
	 * l'ajoute à l'annuaire. Elle appelle ensuite le constructeur de la classe
	 * Relais avec ces paramètres. Si une exception est lancée par le
	 * constructeur, elle est captée, et la méthode retourne faux pour signaler
	 * l'échec de l'ajout. Si tout se passe bien, on retourne vrai.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonnée du relais
	 * @param nom
	 *            Nom du relais, qui sert d'identifiant dans la table
	 * @throws exceptions.RelaisException
	 * @see app.Relais#Relais()
	 */
	public boolean ajouterRelais(int x, int y, String nom) {
		Relais r = null;
		try {
			r = new Relais(x, y, nom);
			this.annuaireRelais.put(r.getNom(), r);
			return true;
		}
		catch (RelaisException e) {
			return false;
		}
	}

	/**
	 * Retire le ou les relais se trouvant aux coordonées passées en paramètre.
	 * Cette méthode parcours tous les éléments de l'annuaire pour comparer leur
	 * coordonées avec celles passées en paramètres. En cas de correspondance,
	 * le relais est supprimé de l'annuaire. Le controle de saisie des
	 * coordonnées est effectué dans la classe d'interface.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonnée du relais
	 */
	public void retirerRelais(int x, int y) {
		for (String key : this.annuaireRelais.keySet()) {
			Relais r = this.annuaireRelais.get(key);
			if (r.getX() == x && r.getY() == y)
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Prends en paramètre un nom de relais, et supprime l'éventuel relais
	 * correspondant de l'annuaire. Cette méthode parcours les clés de la table,
	 * et supprime l'entrée en cas de correspondance.
	 * 
	 * @param nom
	 *            Nom du relais à supprimer.
	 */
	public void retirerRelais(String nom) {
		for (String key : this.annuaireRelais.keySet()) {
			if (key.equals(nom))
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Retire le ou les relais de l'annuaire qui proposent le service passé en
	 * paramètre. Pour chaque relais de l'annuaire, on récupère la liste de
	 * service via la méthode {@link app.Relais#getServices() getService} de la
	 * classe Relais. Le relais est supprimé si le service est contenu dans la
	 * liste. Ce test est réalisé via la méthode contains de l'interface List.
	 * Si le test renvoie true, le relais est supprimé, sinon il est conservé
	 * 
	 * @param s
	 *            Service à rechercher pour éliminer les relais le contenant.
	 */
	public void retirerRelais(Service s) {
		for (String key : this.annuaireRelais.keySet()) {
			Relais r = this.annuaireRelais.get(key);
			if (r.getServices().contains(s))
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Supprime un service passé en paramètre de tous les relais, s'il y est
	 * présent. On parours tout l'annuaire, et on applique la méthode
	 * retirerService de la classe Relais à chacun de ses éléments.
	 * 
	 * @param s
	 *            Service à rechercher et supprimer.
	 */
	public void retirerService(Service s) {
		for (Relais r : this.annuaireRelais.values())
			r.retirerService(s);
	}

	/**
	 * Supprime le relais ayant la clé passée en paramètre de la table.
	 * 
	 * @param key
	 *            La clé du relais à supprimer dans la table, qui est aussi son
	 *            nom.
	 */
	public void supprimerRelais(String key) {
		this.annuaireRelais.remove(key);
	}

	/**
	 * Getter pour obtenir la table des relais.
	 * 
	 * @return La table associative des relais de l'annuaire.
	 */
	public Map<String, Relais> getMapRelais() {
		return this.annuaireRelais;
	}

	/**
	 * Cette méthode permet d'obtenir le nombre de relais de l'annuaire. Elle
	 * renvoie l'entier retourné par la méthode size de l'interface Map.
	 * 
	 * @return La taille de la table associative
	 */
	public int getNbRelais() {
		return this.annuaireRelais.size();
	}

	/**
	 * Prends en paramètre un nom, et renvoie le relais correspondant dans
	 * l'annuaire. S'il n'existe pas, la méthode renvoie NULL
	 * 
	 * @return Retourne un Relais en cas de succès, NULL sinon.
	 */
	public Relais getRelais(String nom) {
		return this.annuaireRelais.get(nom);
	}

	/**
	 * Surcharge de la méthode equals, afin de tester l'égalité entre deux
	 * annuaires. Deux annuaires sont égaux si leur deux tables respectives le
	 * sont, et deux tables sont égales si leurs ensembles de clé sont égaux.
	 * 
	 * @return Retourne true si les annuaires sont égaux, faux sinon.
	 */
	public boolean equals(Annuaire a) {
		return this.annuaireRelais.equals(a.annuaireRelais);
	}

	/**
	 * @return Retourne vrai si l'annuaire est vide, faux sinon.
	 */
	public boolean isEmpty() {
		return this.annuaireRelais.isEmpty();
	}
}