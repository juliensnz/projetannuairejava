package commande;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import app.Annuaire;
import app.Relais;
import app.Service;
import exceptions.RelaisException;

/**
 * Cette classe impl�mente une interface basique en ligne de commande pour un
 * annuaire. Elle appelle a cet effet les m�thodes de la classe Annuaire ainsi
 * que les m�thodes de la classe Relais
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
				if (a.isEmpty()) System.out.println("L'annuaire est vide. Vous devez cr�er un relais avant de pouvoir l'��diter");
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
				if (a.isEmpty()) System.out.println("L'annuaire est vide. Vous devez cr��er un relais avant de pouvoir l'�diter");
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
	 * Affiche les d�tails d'un annuaire pass� en param�tre. Deux bool�ens
	 * pass�s en param�tre permettent de r�gler le niveau de d�tail de
	 * l'affichage : afficher ou non les coordonn�es, les services des relais.
	 */
	public static void afficherAnnuaire(Annuaire a, boolean coord, boolean serv) {
		System.out.println("Voici la liste des relais pr�sents dans l'annuaire : \n");
		for (Relais r : a.getMapRelais().values()) {
			InterfaceRelais.afficher(r, coord, serv);
		}
	}

	/**
	 * Prends en param�tre un annuaire et un service, et affiche les relais qui
	 * proposent le service.
	 */
	public static void afficherAnnuaire(Annuaire a, Service s) {
		System.out.println("Voici la liste des relais pr��sents dans l'annuaire offrant le service " + s.getNom() + " : \n");
		for (Relais r : a.getMapRelais().values())
			for (Service si : r.getServices())
				if (si.equals(s)) InterfaceRelais.afficher(r, true, true);
		System.out.println("Fin de la liste");
	}

	/**
	 * M�thode permettant l'ajout d'un relais dans l'annuaire via la console. La
	 * m�thode est rappel�e r�cursivement si l'ajout �choue.
	 */
	public static void ajouterRelais(Annuaire a) {
		System.out.println("Cr�ation d'un relais :");
		System.out.print("Nom : ");
		String nom = Interface.getString();

		System.out.print("Abscisse : ");
		int abs = Interface.getInt();

		System.out.print("Ordonn�e : ");
		int ord = Interface.getInt();

		if (a.ajouterRelais(abs, ord, nom)) return;
		else InterfaceAnnuaire.ajouterRelais(a);
	}

	/**
	 * M�thode permettant l'�dition un relais � l'aide de la console. On liste
	 * les relais de l'annuaire, l'utilisateur doit ensuite choisir le relais �
	 * �diter par son nom. Un controle de saisie est effectu�.
	 */
	public static void editerRelais(Annuaire a) throws RelaisException {
		System.out.println("Choisissez le relais que vous voulez ��diter : ");
		InterfaceAnnuaire.afficherAnnuaire(a, false, false);
		System.out.println("Autre. Annuler");
		System.out.print("Choix : ");
		String choix = Interface.getString();
		if (!choix.isEmpty()) {
			InterfaceRelais.editerRelais(a.getRelais(choix));
		}
	}

	/**
	 * M�thode qui prends en param�tre un annuaire et propose un menu permettant
	 * de trouver un relais, de diff�rentes mani�res.
	 */
	public static void trouverRelais(Annuaire a) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Trouver les relais � proximit�");
		System.out.println("2. Trouver le relais le plus proche");
		System.out.println("3. Distance d'un relais � la position actuelle");
		System.out.println("Autre. Annuler");
		System.out.print("Choix :");
		int choix = getInt();
		int x, y;
		System.out.print("Abscisse actuelle :");
		x = getInt();
		System.out.print("Ordonn�e actuelle :");
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
					System.out.println("Le relais " + r.getNom() + "se situe � " + r.distance(x, y) + "de la position actuelle");
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Cette m�thode permet la comparaison de deux relais dans l'annuaire pass�
	 * en param�tre.
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
					.println("Ces deux relais sont �gaux (proposent les m�mes services)\n");
			else System.out.println("Ces deux relais sont diff�rents\n");
		}
	}

	/**
	 * Cette m�thode permet de supprimer un relais � l'aide de la console
	 */
	public static void supprimerRelais(Annuaire a) {
		InterfaceAnnuaire.afficherAnnuaire(a, false, false);
		System.out.println("Choisissez le relais que vous voulez supprimer : ");
		System.out.println("Autre. Annuler");
		System.out.print("Choix : ");
		String choix = Interface.getString();
		if (!choix.isEmpty()) {
			a.supprimerRelais(choix);
			System.out.println("Le relais a bien �t� supprim�");
		}
	}

	/**
	 * Cette m�thode permet de trouver et afficher un relais dans un rayon donn�
	 * par l'utilisateur, par rapport � un point pass� en param�tre.
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
		if (i == 0) System.out.println("Aucun relais ne se trouve � moins de " + rayon + "km");

		System.out.println("Retour au menu principal");
	}

	/**
	 * Cette m�thode permet de trouver et afficher le relais le plus proche de
	 * la position entr�e en param�tre.
	 */
	public static void trouverProche(Map<String, Relais> map, int x, int y) {
		Relais plusProche = map.get(0);
		double min = plusProche.distance(x, y);
		for (Relais r : map.values())
			if (r.distance(x, y) < min) plusProche = r;
		System.out.println("Le relais le plus proche est : " + plusProche.getNom());
	}

	/**
	 * Cette m�thode propose un menu qui permet de trouver un service via la
	 * console.
	 */
	public static void trouverService(Annuaire a) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Trouver un service � proximit�");
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
	 * Cette m�thode propose un menu afin trouver le relais le plus proche
	 * proposant un service pass� en param�tre, dans l'annuaire pass� en
	 * param�tre.
	 */
	public static void serviceProche(Annuaire a, Service s) {
		int x, y, choix, heure;
		System.out.print("Abscisse :");
		x = getInt();
		System.out.print("Ordonn�e :");
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
	 * Recherche de relais � l'aide de la console. La position l'heure et le
	 * service recherch� sont pass�s en param�tre
	 */
	public static void rechercherRelais(Annuaire a, int x, int y, String service, int heure) {
		List<String> correspond = new ArrayList<String>();
		Map<String, Relais> map = a.getMapRelais();
		for (String key : map.keySet()) {
			Relais r = map.get(key);
			if (r.contientService(service) && r.getServices(service).getDispo(heure)) correspond.add(key);
		}
		if (correspond.isEmpty()) System.out.println("Aucun relais ne correspond � votre recherche �� cette heure-ci");
		// Si la liste des relais proposant le service est vide...
		else {
			Relais r = null, plusProche = map.get(correspond.get(0));
			for (String key : correspond)
				r = map.get(key);
			if (r.distance(x, y) < plusProche.distance(x, y)) plusProche = r;// Si
			// Si on trouve un relais plus proche, on remplace
			System.out.println("Le relais le plus proche qui propose le service \"" + service + "\" est situ�� � " + plusProche.getNom()
					+ " (� " + plusProche.distance(x, y) + "km d'ici).");
		}
	}
}
