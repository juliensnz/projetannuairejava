package commande;

import app.Relais;
import app.Service;
import exceptions.RelaisException;

/**
 * Classe qui propose une interface en ligne de commande pour la gestion d'un
 * relais. Elle appelle des méthodes des autres classes d'interface, ainsi que
 * des méthodes de la classe Relais
 */
public abstract class InterfaceRelais extends Interface {

	/**
	 * Affiche un relais et si cela est demandé, ses coordonnées et ses
	 * services.
	 */
	public static void afficher(Relais r, boolean coord, boolean serv) {
		System.out.println("- " + r.getNom() + " :");
		if (coord) {
			System.out.println("\tAbscisse : " + r.getX());
			System.out.println("\tOrdonnee : " + r.getY());
		}
		if (serv) InterfaceRelais.afficherServices(r);

		System.out.println("*****************");
	}

	/**
	 * Affiche les services d'un relais et leur horaires de disponibilitŽé
	 * éventuelle.
	 */
	public static void afficherServices(Relais r) {
		int i = 1;
		System.out.println("\tServices disponnibles :");
		for (Service s : r.getServices()) {
			System.out.print("\t" + i + ". ");
			InterfaceService.afficherService(s);
			i++;
		}
	}

	/**
	 * Edition d'un relais en mode console.
	 */
	public static void editerRelais(Relais r) throws RelaisException {
		System.out.println("Edition du relais : " + r.getNom());
		System.out.println("Que voulez vous Žéditer ?");
		System.out.println("1. Nom");
		System.out.println("2. Abscisse");
		System.out.println("3. Ordonnée");
		System.out.println("4. Services");
		System.out.println("Autre. Quitter l'éditeur de relais");
		System.out.print("Choix : ");
		int choix = Interface.getInt();
		switch (choix) {
			case 1:
				System.out.print("Nom : ");
				r.setNom(Interface.getString());
				break;
			case 2:
				System.out.print("X : ");
				r.setPosition(Interface.getInt(), r.getY());
				break;
			case 3:
				System.out.print("Y : ");
				r.setPosition(r.getX(), Interface.getInt());
				break;
			case 4:
				InterfaceService.menuServices(r);
				break;
			// Case 5: On passe dans le default (quitter)
			default:
				System.out.println("Retour au menu préŽcéŽdent");
		}
	}
}
