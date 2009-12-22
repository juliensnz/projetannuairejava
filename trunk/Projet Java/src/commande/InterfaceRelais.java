package commande;

import app.Relais;
import app.Service;
import exceptions.RelaisException;

public abstract class InterfaceRelais extends Interface {

	public static void afficher(Relais r, boolean coord, boolean serv) {
		System.out.println("- " + r.getNom() + " :");
		if (coord) {
			System.out.println("\tAbscisse : " + r.getX());
			System.out.println("\tOrdonnee : " + r.getY());
		}
		if (serv)
			InterfaceRelais.afficherServices(r);

		System.out.println("*****************");
	}

	public static void afficherServices(Relais r) {
		int i = 1;
		System.out.println("\tServices disponnibles :");
		for (Service s : r.getServices()) {
			System.out.print("\t" + i + ". ");
			InterfaceService.afficherService(s);
			i++;
		}
	}// Affiche les services d'un relais et leur horaires de disponibilit�� �ventuelle.

	public static void editerRelais(Relais r) throws RelaisException {
		System.out.println("Edition du relais : " + r.getNom());
		System.out.println("Que voulez vous ��diter ?");
		System.out.println("1. Nom");
		System.out.println("2. Abscisse");
		System.out.println("3. Ordonn�e");
		System.out.println("4. Services");
		System.out.println("Autre. Quitter l'�diteur de relais");
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
			//Case 5: On passe dans le default (quitter)
			default:
				System.out.println("Retour au menu pr�c�dent");
		}
	}
}
