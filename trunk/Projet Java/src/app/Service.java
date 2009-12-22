package app;

import commande.Interface;

/**
 * La classe service permet de d�crire un service ainsi que la plage horaire
 * associ�e.
 * 
 * @author Maxime Bury, Julien Sanchez
 * @see app.Annuaire
 * @see app.Relais
 * 
 */
public class Service implements Comparable<Service> {

	/**
	 * Le nom du service
	 */
	private String		nom;

	/**
	 * Un identifiant unique pour chaque service
	 */
	private static int	id		= 0;

	/**
	 * Un tableau de bool�ens de 1440 cases repr�sentant les minutes de la
	 * journ�e.
	 */
	private boolean[]	dispo	= new boolean[1440];

	/**
	 * Constructeur de la classe Service. Le contr�le de saisie (non nullit� du
	 * nom) est effectu� dans la classe d'interface. Par d�faut, la plage
	 * horaire du service est initialis�e � faux, ce qui signifie qu'il n'est
	 * pas disponnible. A chaque cr�ation de service, l'identifiant est
	 * incr�ment�.
	 * 
	 * @param nom
	 *            Le nom du service
	 */
	public Service(String nom) {
		Service.id++;
		this.nom = nom;
		for (int i = 0; i < 1440; i++)
			this.dispo[i] = false;
	}

	/**
	 * Ajouter une plage de disponnibilit� � un service. Cette m�thode
	 * initialise de tableau � vrai entre les indices minDebut et minFin pass�s
	 * en param�tre. On v�rifie bien que minDebut < minFin avant d'effectuer
	 * l'op�ration, et si ce n'est pas le cas on les �change.
	 * 
	 * @param minDebut
	 *            Heure de d�but de la plage horaire (en minutes)
	 * @param minFin
	 *            Heure de fin de la lage horaire (en minutes)
	 */
	public void ajouterPlage(int minDebut, int minFin) {
		int min = minDebut <= minFin ? minDebut : minFin;
		int max = minDebut <= minFin ? minFin : minDebut;

		for (int i = min; i < max; i++)
			this.dispo[i] = true;
	}

	/**
	 * Ajouter une plage de disponibilit� � un service. Cette m�thode prends en
	 * param�tre des heures sous forme de chaine de caract�res. Elle appelle la
	 * m�thode de traduction {@link Service#traduire(String)} sur ces variables
	 * afin de les traduire en un nombre de minutes entier puis elle appelle la
	 * m�thode {@link Service#ajouterPlage(int, int)}
	 * 
	 * @param heureDebut
	 *            Heure de d�but de la plage (HHhMMm)
	 * @param heureFin
	 *            Heure de fin de la plage (HHhMMm)
	 */
	public void ajouterPlage(String heureDebut, String heureFin) {
		this.ajouterPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}

	/**
	 * Supprimer une plage de disponnibilit� � un service. Cette m�thode
	 * fonctionne de mani�re identique � la m�thode
	 * {@link Service#ajouterPlage(int, int)} mais avec l'effet inverse.
	 * 
	 * @param minDebut
	 *            Heure de d�but de la plage horaire (en minutes)
	 * @param minFin
	 *            Heure de fin de la lage horaire (en minutes)
	 */
	public void supprimerPlage(int minDebut, int minFin) {
		int min = minDebut <= minFin ? minDebut : minFin;
		int max = minDebut <= minFin ? minFin : minDebut;

		for (int i = min; i < max; i++)
			this.dispo[i] = false;
	}

	/**
	 * Supprimer une plage de disponnibilit� � un service. Cette m�thode
	 * fonctionne de mani�re identique � la m�thode
	 * {@link Service#ajouterPlage(String, String)} mais avec l'effet inverse.
	 * 
	 * @param heureDebut
	 *            Heure de d�but de la plage (HHhMMm)
	 * @param heureFin
	 *            Heure de fin de la plage (HHhMMm)
	 */
	public void supprimerPlage(String heureDebut, String heureFin) {
		this.supprimerPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}

	/**
	 * Prends en param�tre un entier (un nombre de minutes) et renvoie l'heure
	 * format�e correspondante sous forme de chaine. On utilise la division
	 * enti�re et le modulo pour extraire le nombres d'heures et le nombre de
	 * minutes.
	 * 
	 * @param time
	 *            Nombre de minutes � convertir
	 * @return L'heure au format HHhMMm dans une chaine
	 */
	public static String traduire(int time) {
		int heures = time / 60;
		int minutes = time % 60;

		return (heures < 10 ? "0" + heures : "" + heures) + "h" + (minutes < 10 ? "0" + minutes : "" + minutes);
	}

	/**
	 * Prends en param�tre l'heure format�e et essaye de la convertir en un
	 * nombre de minutes correspondant. Si la conversion �choue, on r�cup�re
	 * l'heure actuelle du systeme via la m�thode
	 * {@link Interface#getCurrentTime() getCurrentTime()} de la classe
	 * d'interface.
	 * 
	 * @param time
	 *            L'heure sous la forme HHhMMm
	 * @return L'entier compris entre 0 et 1440 correspondant.
	 */
	public static int traduire(String time) {
		int heures, minutes;
		try {
			heures = Integer.parseInt(time.substring(0, time.indexOf("h")));
			minutes = Integer.parseInt(time.substring(time.indexOf("h") + 1, time.indexOf("h") + 3));
		}
		catch (NumberFormatException e) {
			return Interface.getCurrentTime();
		}
		return heures * 60 + minutes;
	}

	/**
	 * L'ordre des services corresponds � l'ordre alphab�tique de leurs noms.
	 */
	public int compareTo(Service o) {
		return this.getNom().compareTo(o.getNom());
	}

	/**
	 * Renvoie vrai si le service est disponible � l'heure pass�e en param�tre,
	 * faux sinon.
	 * 
	 * @param heure
	 *            Temps en minutes (0 � 1440)
	 * @return Vrai si le service est disponnible, faux sinon
	 */
	public boolean getDispo(int heure) {
		return this.dispo[heure];
	}

	/**
	 * Getter pour obtenir le nom du service
	 * 
	 * @return Le nom du service
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Cette m�thode renvoie une chaine qui indique les plages de disponibilit�
	 * d'un service. Elle parcours l'ensemble du tableau de bool�ens et traduit
	 * en heure format�es les indices de d�but et de fin des plages initialis�e
	 * � vrai. Elle formate ensuite ces valeurs dans une chaine, et la retourne
	 * 
	 * @return Une chaine indiquant la disponibilit� du service.
	 */
	public String getPlage() {
		boolean etat = false;
		int ouvertureService = 0;
		String resultat = "";

		for (int i = 0; i < this.dispo.length; i++) {
			ouvertureService = this.dispo[i] == true && etat == false ? i : ouvertureService;
			// Prend pour valeur vrai au d�but d'une plage horaire.
			resultat += this.dispo[i] == false && etat == true ? " [" + Service.traduire(ouvertureService) + " : " + Service.traduire(i)
					+ "]" : "";
			// Se d��clenche a la fin d'une plage.
			etat = this.dispo[i];
		}
		if (!resultat.isEmpty()) resultat += ".";

		return resultat;
	}// Retourne les horaires d'ouverture d'un service sous forme de chaine.
}
