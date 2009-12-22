package commande;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import app.Annuaire;
import app.Relais;
import app.Service;
import exceptions.RelaisException;

/**
 * Cette classe implÈmente une interface basique en ligne de commande pour un
 * annuaire. Elle appelle a cet effet les mÈthodes de la classe Annuaire ainsi
 * que les mÈthodes de la classe Relais
 */
public abstract class InterfaceAnnuaire extends Interface {

	public static void menuAnnuaire(Annuaire a) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Ajouter un relais");
		System.out.println("2. Editer un relais");
		System.out.println("3. Supprimer un relais");
		System.out.println("4. Rechercher un relais");
		System.out.println("5. Comparer deux relais");
		System.out.println("6. Afficher la liste des relais");
		System.out.println("7. Rechercher un service");
		System.out.println("Autre. Revenir au menu principal");
		System.out.print("Choix : ");
		int entree = Interface.getInt();
		switch (entree) {
			case 1:
				InterfaceAnnuaire.ajouterRelais(a);
				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 2:
				if (a.isEmpty()) System.out.println("L'annuaire est vide. Vous devez crÈéer un relais avant de pouvoir l'éÈditer");
				else {
					try {
						InterfaceAnnuaire.editerRelais(a);
					}
					catch (RelaisException e) {
					}
				}
				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 3:
				if (a.isEmpty()) System.out.println("L'annuaire est vide. Vous devez créÈer un relais avant de pouvoir l'Èéditer");
				else InterfaceAnnuaire.supprimerRelais(a);

				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 4:
				InterfaceAnnuaire.trouverRelais(a);
				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 5:
				InterfaceAnnuaire.comparerRelais(a);
				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 6:
				System.out.println("1. Avec les services");
				System.out.println("2. Sans les services");
				System.out.println("Autre. Annuler");
				System.out.print("Choix : ");
				int entree2 = Interface.getInt();
				if (entree2 == 1) InterfaceAnnuaire.afficherAnnuaire(a, true, true);
				else if (entree2 == 2) InterfaceAnnuaire.afficherAnnuaire(a, true, false);

				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			case 7:
				InterfaceAnnuaire.trouverService(a);
				InterfaceAnnuaire.menuAnnuaire(a);
				break;
			default:
				break;
		}
	}

	/**
	 * Affiche les dÈtails d'un annuaire passÈ en paramËtre. Deux boolÈens
	 * passÈs en paramËtre permettent de rÈgler le niveau de dÈtail de
	 * l'affichage : afficher ou non les coordonnÈes, les services des relais.
	 */
	public static void afficherAnnuaire(Annuaire a, boolean coord, boolean serv) {
		System.out.println("Voici la liste des relais prÈésents dans l'annuaire : \n");
		for (Relais r : a.getMapRelais().values()) {
			InterfaceRelais.afficher(r, coord, serv);
		}
	}

	/**
	 * Prends en paramËtre un annuaire et un service, et affiche les relais qui
	 * proposent le service.
	 */
	public static void afficherAnnuaire(Annuaire a, Service s) {
		System.out.println("Voici la liste des relais préÈsents dans l'annuaire offrant le service " + s.getNom() + " : \n");
		for (Relais r : a.getMapRelais().values())
			for (Service si : r.getServices())
				if (si.equals(s)) InterfaceRelais.afficher(r, true, true);
		System.out.println("Fin de la liste");
	}

	/**
	 * MÈthode permettant l'ajout d'un relais dans l'annuaire via la console. La
	 * mÈthode est rappelÈe rÈcursivement si l'ajout Èchoue.
	 */
	public static void ajouterRelais(Annuaire a) {
		System.out.println("CrÈéation d'un relais :");
		System.out.print("Nom : ");
		String nom = Interface.getString();

		System.out.print("Abscisse : ");
		int abs = Interface.getInt();

		System.out.print("OrdonnÈe : ");
		int ord = Interface.getInt();

		if (a.ajouterRelais(abs, ord, nom)) return;
		else InterfaceAnnuaire.ajouterRelais(a);
	}

	/**
	 * MÈthode permettant l'Èdition un relais ‡ l'aide de la console. On liste
	 * les relais de l'annuaire, l'utilisateur doit ensuite choisir le relais ‡
	 * Èditer par son nom. Un controle de saisie est effectuÈ.
	 */
	public static void editerRelais(Annuaire a) throws RelaisException {
		System.out.println("Choisissez le relais que vous voulez éÈditer : ");
		InterfaceAnnuaire.afficherAnnuaire(a, false, false);
		System.out.println("Autre. Annuler");
		System.out.print("Choix : ");
		String choix = Interface.getString();
		if (!choix.isEmpty()) {
			InterfaceRelais.editerRelais(a.getRelais(choix));
		}
	}

	/**
	 * MÈthode qui prends en paramËtre un annuaire et propose un menu permettant
	 * de trouver un relais, de diffÈrentes maniËres.
	 */
	public static void trouverRelais(Annuaire a) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Trouver les relais à proximitÈé");
		System.out.println("2. Trouver le relais le plus proche");
		System.out.println("3. Distance d'un relais à la position actuelle");
		System.out.println("Autre. Annuler");
		System.out.print("Choix :");
		int choix = getInt();
		int x, y;
		System.out.print("Abscisse actuelle :");
		x = getInt();
		System.out.print("OrdonnÈe actuelle :");
		y = getInt();
		switch (choix) {
			case 1:
				InterfaceAnnuaire.trouverRayon(a, x, y);
				break;
			case 2:
				InterfaceAnnuaire.trouverProche(a.getMapRelais(), x, y);
				break;
			case 3:
				InterfaceAnnuaire.afficherAnnuaire(a, false, false);
				System.out.print("Choix :");
				String nom = Interface.getString();
				if (!nom.isEmpty()) {
					Relais r = a.getRelais(nom);
					System.out.println("Le relais " + r.getNom() + "se situe ‡ " + r.distance(x, y) + "de la position actuelle");
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Cette mÈthode permet la comparaison de deux relais dans l'annuaire passÈ
	 * en paramËtre.
	 */
	public static void comparerRelais(Annuaire a) {
		InterfaceAnnuaire.afficherAnnuaire(a, false, false);
		System.out.println("Autre. Annuler");
		String choix1, choix2;
		System.out.print("Comparer le relais : ");
		choix1 = Interface.getString();
		System.out.print("avec le relais : ");
		choix2 = Interface.getString();
		if (!choix1.isEmpty() && !choix2.isEmpty()) {
			if (a.getRelais(choix1).equals(a.getRelais(choix2))) System.out
					.println("Ces deux relais sont Èégaux (proposent les mÍêmes services)\n");
			else System.out.println("Ces deux relais sont diffÈérents\n");
		}
	}

	/**
	 * Cette mÈthode permet de supprimer un relais ‡ l'aide de la console
	 */
	public static void supprimerRelais(Annuaire a) {
		InterfaceAnnuaire.afficherAnnuaire(a, false, false);
		System.out.println("Choisissez le relais que vous voulez supprimer : ");
		System.out.println("Autre. Annuler");
		System.out.print("Choix : ");
		String choix = Interface.getString();
		if (!choix.isEmpty()) {
			a.supprimerRelais(choix);
			System.out.println("Le relais a bien été supprimé");
		}
	}

	/**
	 * Cette mÈthode permet de trouver et afficher un relais dans un rayon donnÈ
	 * par l'utilisateur, par rapport ‡ un point passÈ en paramËtre.
	 */
	public static void trouverRayon(Annuaire a, int x, int y) {
		System.out.print("Rayon de recherche : ");
		int rayon = getInt();
		int i = 0;
		for (Relais r : a.getMapRelais().values()) {
			if (r.distance(x, y) < rayon) {
				InterfaceRelais.afficher(r, true, true);
				i++;
			}
		}
		if (i == 0) System.out.println("Aucun relais ne se trouve à moins de " + rayon + "km");

		System.out.println("Retour au menu principal");
	}

	/**
	 * Cette mÈthode permet de trouver et afficher le relais le plus proche de
	 * la position entrÈe en paramËtre.
	 */
	public static void trouverProche(Map<String, Relais> map, int x, int y) {
		Relais plusProche = map.get(0);
		double min = plusProche.distance(x, y);
		for (Relais r : map.values())
			if (r.distance(x, y) < min) plusProche = r;
		System.out.println("Le relais le plus proche est : " + plusProche.getNom());
	}

	/**
	 * Cette mÈthode propose un menu qui permet de trouver un service via la
	 * console.
	 */
	public static void trouverService(Annuaire a) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Trouver un service ‡ proximitÈé");
		System.out.println("2. Trouver les relais proposant un service");
		System.out.println("Autre. Quitter");
		System.out.print("Choix : ");
		int entree = Interface.getInt();

		System.out.print("Nom du service : ");
		Service s = new Service(Interface.getString());
		switch (entree) {
			case 1:
				InterfaceAnnuaire.serviceProche(a, s);
				break;
			case 2:
				System.out.println("Trouver tous les relais proposant un service");
				InterfaceAnnuaire.afficherAnnuaire(a, s);
				break;
			default:
				break;
		}
	}

	/**
	 * Cette mÈthode propose un menu afin trouver le relais le plus proche
	 * proposant un service passÈ en paramËtre, dans l'annuaire passÈ en
	 * paramËtre.
	 */
	public static void serviceProche(Annuaire a, Service s) {
		int x, y, choix, heure;
		System.out.print("Abscisse :");
		x = getInt();
		System.out.print("OrdonnÈe :");
		y = getInt();
		System.out.println("Heure :");
		System.out.println("1. Heure actuelle");
		System.out.println("2. Autre heure");
		choix = Interface.getInt();
		switch (choix) {
			case 1:
				heure = Interface.getCurrentTime();
				break;
			case 2:
				System.out.println("Veuillez entrer l'heure sous la forme HHhMMm :");
				System.out.print("Heure : ");
				heure = Service.traduire(getString());
				break;
			default:
				heure = Interface.getCurrentTime();
		}
		InterfaceAnnuaire.rechercherRelais(a, x, y, s.getNom(), heure);
	}

	/**
	 * Recherche de relais ‡ l'aide de la console. La position l'heure et le
	 * service recherchÈ sont passÈs en paramËtre
	 */
	public static void rechercherRelais(Annuaire a, int x, int y, String service, int heure) {
		List<String> correspond = new ArrayList<String>();
		Map<String, Relais> map = a.getMapRelais();
		for (String key : map.keySet()) {
			Relais r = map.get(key);
			if (r.contientService(service) && r.getServices(service).getDispo(heure)) correspond.add(key);
		}
		if (correspond.isEmpty()) System.out.println("Aucun relais ne correspond à votre recherche ‡à cette heure-ci");
		// Si la liste des relais proposant le service est vide...
		else {
			Relais r = null, plusProche = map.get(correspond.get(0));
			for (String key : correspond)
				r = map.get(key);
			if (r.distance(x, y) < plusProche.distance(x, y)) plusProche = r;// Si
			// Si on trouve un relais plus proche, on remplace
			System.out.println("Le relais le plus proche qui propose le service \"" + service + "\" est situéÈ à " + plusProche.getNom()
					+ " (à " + plusProche.distance(x, y) + "km d'ici).");
		}
	}
}
