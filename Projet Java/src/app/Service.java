package app;

import commande.Interface;

/**
 * La classe service permet de décrire un service ainsi que la plage horaire
 * associée.
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
	 * Un tableau de booléens de 1440 cases représentant les minutes de la
	 * journée.
	 */
	private boolean[]	dispo	= new boolean[1440];

	/**
	 * Constructeur de la classe Service. Le contrôle de saisie (non nullité du
	 * nom) est effectué dans la classe d'interface. Par défaut, la plage
	 * horaire du service est initialisée à faux, ce qui signifie qu'il n'est
	 * pas disponnible. A chaque création de service, l'identifiant est
	 * incrémenté.
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
	 * Ajouter une plage de disponnibilité à un service. Cette méthode
	 * initialise de tableau à vrai entre les indices minDebut et minFin passés
	 * en paramètre. On vérifie bien que minDebut < minFin avant d'effectuer
	 * l'opération, et si ce n'est pas le cas on les échange.
	 * 
	 * @param minDebut
	 *            Heure de début de la plage horaire (en minutes)
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
	 * Ajouter une plage de disponibilité à un service. Cette méthode prends en
	 * paramètre des heures sous forme de chaine de caractères. Elle appelle la
	 * méthode de traduction {@link Service#traduire(String)} sur ces variables
	 * afin de les traduire en un nombre de minutes entier puis elle appelle la
	 * méthode {@link Service#ajouterPlage(int, int)}
	 * 
	 * @param heureDebut
	 *            Heure de début de la plage (HHhMMm)
	 * @param heureFin
	 *            Heure de fin de la plage (HHhMMm)
	 */
	public void ajouterPlage(String heureDebut, String heureFin) {
		this.ajouterPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}

	/**
	 * Supprimer une plage de disponnibilité à un service. Cette méthode
	 * fonctionne de manière identique à la méthode
	 * {@link Service#ajouterPlage(int, int)} mais avec l'effet inverse.
	 * 
	 * @param minDebut
	 *            Heure de début de la plage horaire (en minutes)
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
	 * Supprimer une plage de disponnibilité à un service. Cette méthode
	 * fonctionne de manière identique à la méthode
	 * {@link Service#ajouterPlage(String, String)} mais avec l'effet inverse.
	 * 
	 * @param heureDebut
	 *            Heure de début de la plage (HHhMMm)
	 * @param heureFin
	 *            Heure de fin de la plage (HHhMMm)
	 */
	public void supprimerPlage(String heureDebut, String heureFin) {
		this.supprimerPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}

	/**
	 * Prends en paramètre un entier (un nombre de minutes) et renvoie l'heure
	 * formatée correspondante sous forme de chaine. On utilise la division
	 * entière et le modulo pour extraire le nombres d'heures et le nombre de
	 * minutes.
	 * 
	 * @param time
	 *            Nombre de minutes à convertir
	 * @return L'heure au format HHhMMm dans une chaine
	 */
	public static String traduire(int time) {
		int heures = time / 60;
		int minutes = time % 60;

		return (heures < 10 ? "0" + heures : "" + heures) + "h" + (minutes < 10 ? "0" + minutes : "" + minutes);
	}

	/**
	 * Prends en paramètre l'heure formatée et essaye de la convertir en un
	 * nombre de minutes correspondant. Si la conversion échoue, on récupère
	 * l'heure actuelle du systeme via la méthode
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
	 * L'ordre des services corresponds à l'ordre alphabétique de leurs noms.
	 */
	public int compareTo(Service o) {
		return this.getNom().compareTo(o.getNom());
	}

	/**
	 * Renvoie vrai si le service est disponible à l'heure passée en paramètre,
	 * faux sinon.
	 * 
	 * @param heure
	 *            Temps en minutes (0 à 1440)
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
	 * Cette méthode renvoie une chaine qui indique les plages de disponibilité
	 * d'un service. Elle parcours l'ensemble du tableau de booléens et traduit
	 * en heure formatées les indices de début et de fin des plages initialisée
	 * à vrai. Elle formate ensuite ces valeurs dans une chaine, et la retourne
	 * 
	 * @return Une chaine indiquant la disponibilité du service.
	 */
	public String getPlage() {
		boolean etat = false;
		int ouvertureService = 0;
		String resultat = "";

		for (int i = 0; i < this.dispo.length; i++) {
			ouvertureService = this.dispo[i] == true && etat == false ? i : ouvertureService;
			// Prend pour valeur vrai au déŽbut d'une plage horaire.
			resultat += this.dispo[i] == false && etat == true ? " [" + Service.traduire(ouvertureService) + " : " + Service.traduire(i)
					+ "]" : "";
			// Se dŽéclenche a la fin d'une plage.
			etat = this.dispo[i];
		}
		if (!resultat.isEmpty()) resultat += ".";

		return resultat;
	}// Retourne les horaires d'ouverture d'un service sous forme de chaine.
}
