package commande;

import java.util.Scanner;

public abstract class Interface {

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

	public static String getString() {
		Scanner sc = new Scanner(System.in);
		String entree = sc.nextLine();
		return entree;
	}

	public static int getCurrentTime() {
		// Initialisation du timestamp
		int heures = (int) (System.currentTimeMillis() / (1000 * 60 * 60) % 24 + 1);
		int minutes = (int) (System.currentTimeMillis() / (1000 * 60) % 60);
		return heures * 60 + minutes;
	}
}
