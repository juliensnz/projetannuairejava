package app;

import commande.Interface;

public class Service implements Comparable<Service> {

	private String		nom;
	private static int	id		= 0;
	private boolean[]	dispo	= new boolean[1440];

	public Service(String nom) {
		Service.id++;
		this.nom = nom;
		for (int i = 0; i < 1440; i++)
			this.dispo[i] = false;
	}// Constructeur

	public void ajouterPlage(int minDebut, int minFin) {
		int min = minDebut <= minFin ? minDebut : minFin;
		int max = minDebut <= minFin ? minFin : minDebut;

		for (int i = min; i < max; i++)
			this.dispo[i] = true;
	}// On ajoute une plage horaire, valeurs controlŽes lors de la saisie,

	// valeurs en minutes de 0 ˆ 1440.

	public void ajouterPlage(String heureDebut, String heureFin) {
		this.ajouterPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}// Ajout d'une plage avec des valeurs formatŽes en XXhYYmin

	public void supprimerPlage(int minDebut, int minFin) {
		int min = minDebut <= minFin ? minDebut : minFin;
		int max = minDebut <= minFin ? minFin : minDebut;

		for (int i = min; i < max; i++)
			this.dispo[i] = false;
	}// Supprimer une plage horaire, valeurs en minutes de 0 ˆ 1440

	public void supprimerPlage(String heureDebut, String heureFin) {
		this.supprimerPlage(Service.traduire(heureDebut), Service.traduire(heureFin));
	}// Suppression d'une plage avec des valeurs formatŽes en XXhYYmin

	public static String traduire(int time) {
		int heures = time / 60;
		int minutes = time % 60;

		return (heures < 10 ? "0" + heures : "" + heures) + "h" + (minutes < 10 ? "0" + minutes : "" + minutes);
	}// Transforme un temps en minutes (0 ˆ 1440) en XXhYYmin

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
	}// Transforme un temps en heures minutes XXhYYmin en entier (0 ˆ 1440)

	public int compareTo(Service o) {
		return this.getNom().compareTo(o.getNom());
	}

	public boolean getDispo(int heure) {
		return this.dispo[heure];
	}

	public String getNom() {
		return this.nom;
	}

	public String getPlage() {
		boolean etat = false;
		int ouvertureService = 0;
		String resultat = "";

		for (int i = 0; i < this.dispo.length; i++) {
			ouvertureService = this.dispo[i] == true && etat == false ? i : ouvertureService;
			// Prend pour valeur vrai au déŽbut d'une plage horaire.
			resultat += this.dispo[i] == false && etat == true ? " [" + Service.traduire(ouvertureService) + " : " + Service.traduire(i) + "]" : "";
			// Se dŽéclenche a la fin d'une plage.
			etat = this.dispo[i];
		}
		if (!resultat.isEmpty())
			resultat += ".";

		return resultat;
	}// Retourne les horaires d'ouverture d'un service sous forme de chaine.
}
