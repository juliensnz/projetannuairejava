package commande;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import app.Annuaire;
import app.Relais;
import app.Service;
import exceptions.RelaisException;

/**
 * Interface en ligne de commande pour un annuaire.
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
	 * Affiche les d�tails d'un annuaire.
	 */
	public static void afficherAnnuaire(Annuaire a, boolean coord, boolean serv) {
		System.out.println("Voici la liste des relais pr�sents dans l'annuaire : \n");
		int i = 1;
		for (Relais r : a.getMapRelais().values()) {
			InterfaceRelais.afficher(r, coord, serv);
			i++;
		}
	}
	
	/**
	 * Affiche les relais d'un annuaire "a" contenant le m�me service "s"
	 */
	public static void afficherAnnuaire(Annuaire a, Service s) {
		System.out.println("Voici la liste des relais pr��sents dans l'annuaire offrant le service " + s.getNom() + " : \n");
		for (Relais r : a.getMapRelais().values())
			for (Service si : r.getServices())
				if (si.equals(s)) InterfaceRelais.afficher(r, true, true);
		System.out.println("Fin de la liste");
		// A remettre dans l'annuaire
	}

	/**
	 * Interface pour l'ajout d'un relais � un annuaire
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
	 * Editer un relais � l'aide de la console
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
	 * Trouver un relais de l'annuaire "a"
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
	 * Comparer deux relais � l'aide de la console dans l'annuaire "a"
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
	 * Supprimer un relais � l'aide de la console
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
	 * Trouver un relais dans un rayon (obtenu � l'aide de la console) entr� en param�tre
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
	 * Trouver le relais le plus proche de la position entr�e en param�tre.
	 */
	public static void trouverProche(Map<String, Relais> map, int x, int y) {
		Relais plusProche = map.get(0);
		double min = plusProche.distance(x, y);
		for (Relais r : map.values())
			if (r.distance(x, y) < min) plusProche = r;
		System.out.println("Le relais le plus proche est : " + plusProche.getNom());
	}

	/**
	 * Trouver un service � l'aide de la console dans l'annuaire "a"
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
	 * Trouver le relais le plus proche proposant le service "s" dans l'annuaire "a"
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
	 * Recherche de relais � l'aide de la console. La position l'heure et le service recherch� sont entr�s en param�tre
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
																				// on
																				// trouve
																				// un
																				// relai
																				// plus
																				// proche,
																				// on
			// remplace
			System.out.println("Le relais le plus proche qui propose le service \"" + service + "\" est situ�� � " + plusProche.getNom()
					+ " (� " + plusProche.distance(x, y) + "km d'ici).");
		}
	}
}
