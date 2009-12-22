package commande;

import app.Relais;
import app.Service;
import exceptions.RelaisException;

/**
 * Classe d'interface en ligne de commande pour un service. Elle en permet
 * l'�dition principalement.
 */
public abstract class InterfaceService extends Interface {

	/**
	 * Menu pour effectuer les modifications et ajout de services sur un relais
	 */
	public static void menuServices(Relais r) {
		System.out.println("Que voulez vous faire ?");
		System.out.println("1. Ajouter un service");
		System.out.println("2. Editer un service");
		System.out.println("3. Supprimer un service");
		System.out.println("Autre. Quitter l'�diteur de service");
		System.out.print("Choix : ");
		int choix = Interface.getInt();

		switch (choix) {
			case 1:
				InterfaceService.ajouterService(r);
				InterfaceService.menuServices(r);
				break;
			case 2:
				InterfaceService.editerService(r);
				InterfaceService.menuServices(r);
				break;
			case 3:
				InterfaceService.supprimerService(r);
				InterfaceService.menuServices(r);
				break;
			default:
				break;
		}
	}

	/**
	 * Afficher les d�tails d'un service (nom et plages)
	 */
	public static void afficherService(Service s) {
		System.out.println(s.getNom() + " : " + s.getPlage());
	}

	/**
	 * Ajouter un service � l'aide de la ligne de commande
	 */
	public static void ajouterService(Relais r) {
		System.out.println("Ajouter un service : ");
		System.out.print("Nom : ");
		String nom = Interface.getString();
		try {
			r.ajouterService(nom);
		}
		catch (RelaisException e) {
		}
	}

	/**
	 * Editer un service parmis ceux propos�s dans un relais � l'aide de la
	 * ligne de commande
	 */
	public static void editerService(Relais r) {
		if (r.getNbService() == 0) {
			System.out
					.println("Le relais ne propose pas de service pour l'instant. Vous devez cr�er un service avant de pouvoir l'�diter");
			InterfaceService.menuServices(r);
		}
		else {
			System.out.println("Editer un service : ");
			InterfaceRelais.afficherServices(r);
			System.out.println("Autre. Annuler");
			System.out.print("Choix : ");
			int choix = Interface.getInt();
			if (choix > 0 && choix <= r.getNbService()) {
				choix--;
				InterfaceService.editerService(r.getServices(choix));
			}
		}
	}

	/**
	 * Editer un service � l'aide de la ligne de commande
	 */
	public static void editerService(Service s) {
		System.out.println("Editer un service :");
		System.out.println("1. Ajouter une plage horaire");
		System.out.println("2. Supprimer une plage horaire");
		System.out.println("3. Afficher les plages horaires");
		System.out.println("Autre. Quitter l'�diteur de services");
		System.out.print("Choix : ");
		int entree = Interface.getInt();

		switch (entree) {
			case 1:
				System.out.println("Ajouter une plage horaire (XXhYYmin) : ");
				System.out.print("De : ");
				String debut = Interface.getString();
				System.out.print("� : ");
				String fin = Interface.getString();
				s.ajouterPlage(debut, fin);
				break;
			case 2:
				System.out.println("Supprimer une plage horaire (XXhYYmin) : ");
				System.out.print("De : ");
				String debutSup = Interface.getString();
				System.out.print("�� : ");
				String finSup = Interface.getString();
				s.supprimerPlage(debutSup, finSup);
				break;
			case 3:
				System.out.print("Plages d'ouverture : " + s.getPlage());
				break;
			default:
				System.out.println("Retour au menu pr�c�dent");
				break;
		}
	}

	/**
	 * Supprimer un service parmis ceux propos�s par un relais
	 */
	public static void supprimerService(Relais r) {
		if (r.getNbService() == 0) {
			System.out
					.println("Le relais ne propose pas de service pour l'instant. Vous devez cr��er des services avant de pouvoir en supprimer");
			InterfaceService.editerService(r);
		}
		else {
			System.out.println("Supprimer un service : ");
			InterfaceRelais.afficherServices(r);
			System.out.print("Choix : ");
			int choix = Interface.getInt();
			if (choix >= 0 && choix < r.getNbService()) {
				choix--;
				r.retirerService(r.getServices(choix));
			}
		}
	}
}
