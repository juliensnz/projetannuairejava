package app;

import java.util.HashMap;
import java.util.Map;
import exceptions.RelaisException;

/**
 * La classe annuaire est d�di�e � la gestion d'un annuaire de Relais, elle
 * propose l'ensemble des outils d�di�s � la cr�ation, modification et
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
	 * la table associative des relais. Cette table est d�clar�e en tant que
	 * collection de type Map. Elle est instanci�e par le constructeur avec
	 * l'impl�mentation HashMap. La cl� peut donc �ventuellement �tre nulle, le
	 * cont�le est effectu� � la cr�ation d'un relais, et donc avant
	 * l'insertion.
	 */
	public Annuaire() {
		this.annuaireRelais = new HashMap<String, Relais>();
	}

	/**
	 * Prends en param�tre l'abscisse, l'ordonn�e et le nom d'un relais et
	 * l'ajoute � l'annuaire. Elle appelle ensuite le constructeur de la classe
	 * Relais avec ces param�tres. Si une exception est lanc�e par le
	 * constructeur, elle est capt�e, et la m�thode retourne faux pour signaler
	 * l'�chec de l'ajout. Si tout se passe bien, on retourne vrai.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonn�e du relais
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
	 * Retire le ou les relais se trouvant aux coordon�es pass�es en param�tre.
	 * Cette m�thode parcours tous les �l�ments de l'annuaire pour comparer leur
	 * coordon�es avec celles pass�es en param�tres. En cas de correspondance,
	 * le relais est supprim� de l'annuaire. Le controle de saisie des
	 * coordonn�es est effectu� dans la classe d'interface.
	 * 
	 * @param x
	 *            Abscisse du relais
	 * @param y
	 *            Ordonn�e du relais
	 */
	public void retirerRelais(int x, int y) {
		for (String key : this.annuaireRelais.keySet()) {
			Relais r = this.annuaireRelais.get(key);
			if (r.getX() == x && r.getY() == y)
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Prends en param�tre un nom de relais, et supprime l'�ventuel relais
	 * correspondant de l'annuaire. Cette m�thode parcours les cl�s de la table,
	 * et supprime l'entr�e en cas de correspondance.
	 * 
	 * @param nom
	 *            Nom du relais � supprimer.
	 */
	public void retirerRelais(String nom) {
		for (String key : this.annuaireRelais.keySet()) {
			if (key.equals(nom))
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Retire le ou les relais de l'annuaire qui proposent le service pass� en
	 * param�tre. Pour chaque relais de l'annuaire, on r�cup�re la liste de
	 * service via la m�thode {@link app.Relais#getServices() getService} de la
	 * classe Relais. Le relais est supprim� si le service est contenu dans la
	 * liste. Ce test est r�alis� via la m�thode contains de l'interface List.
	 * Si le test renvoie true, le relais est supprim�, sinon il est conserv�
	 * 
	 * @param s
	 *            Service � rechercher pour �liminer les relais le contenant.
	 */
	public void retirerRelais(Service s) {
		for (String key : this.annuaireRelais.keySet()) {
			Relais r = this.annuaireRelais.get(key);
			if (r.getServices().contains(s))
				this.annuaireRelais.remove(key);
		}
	}

	/**
	 * Supprime un service pass� en param�tre de tous les relais, s'il y est
	 * pr�sent. On parours tout l'annuaire, et on applique la m�thode
	 * retirerService de la classe Relais � chacun de ses �l�ments.
	 * 
	 * @param s
	 *            Service � rechercher et supprimer.
	 */
	public void retirerService(Service s) {
		for (Relais r : this.annuaireRelais.values())
			r.retirerService(s);
	}

	/**
	 * Supprime le relais ayant la cl� pass�e en param�tre de la table.
	 * 
	 * @param key
	 *            La cl� du relais � supprimer dans la table, qui est aussi son
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
	 * Cette m�thode permet d'obtenir le nombre de relais de l'annuaire. Elle
	 * renvoie l'entier retourn� par la m�thode size de l'interface Map.
	 * 
	 * @return La taille de la table associative
	 */
	public int getNbRelais() {
		return this.annuaireRelais.size();
	}

	/**
	 * Prends en param�tre un nom, et renvoie le relais correspondant dans
	 * l'annuaire. S'il n'existe pas, la m�thode renvoie NULL
	 * 
	 * @return Retourne un Relais en cas de succ�s, NULL sinon.
	 */
	public Relais getRelais(String nom) {
		return this.annuaireRelais.get(nom);
	}

	/**
	 * Surcharge de la m�thode equals, afin de tester l'�galit� entre deux
	 * annuaires. Deux annuaires sont �gaux si leur deux tables respectives le
	 * sont, et deux tables sont �gales si leurs ensembles de cl� sont �gaux.
	 * 
	 * @return Retourne true si les annuaires sont �gaux, faux sinon.
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