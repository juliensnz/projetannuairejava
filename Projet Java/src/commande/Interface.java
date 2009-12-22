package commande;

import java.util.Scanner;

/**
 * Classe permettant de récuperer une chaine ou un entier en gerrant les exceptions.
 */
public abstract class Interface {

	/**
	 * Récperer un int dans la console.
	 */
	public static int getInt() {
		Scanner sc = new Scanner(System.in);
		int entree = 0;
		try {
			entree = sc.nextInt();
		}
		catch (java.util.InputMismatchException e) {
			System.out.print("Veuillez entrer un nombre valide : ");
			return Interface.getInt();
		}
		return entree;
	}

	/**
	 * Obtenir une chaine depuis la console
	 */
	public static String getString() {
		Scanner sc = new Scanner(System.in);
		String entree = sc.nextLine();
		return entree;
	}

	/**
	 * Obtenir l'heure actuelle
	 */
	public static int getCurrentTime() {
		// Initialisation du timestamp
		int heures = (int) (System.currentTimeMillis() / (1000 * 60 * 60) % 24 + 1);
		int minutes = (int) (System.currentTimeMillis() / (1000 * 60) % 60);
		return heures * 60 + minutes;
	}
}
