package commande;

import java.util.Scanner;

/**
 * Classe permettant de r�cuperer une chaine ou un entier en g�rant les
 * exceptions.
 */
public abstract class Interface {

	/**
	 * M�thode permettant la saisie s�curis�e d'un entier. La m�thode est
	 * rappel�e r�cursivement tant que la saisie n'est pas correcte.
	 * 
	 * @return L'entier saisi par l'utilisateur.
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
	 * M�thode permettant la saisie d'une chaine de caract�res.
	 * 
	 * @return La chaine saisie par l'utilisateur
	 */
	public static String getString() {
		Scanner sc = new Scanner(System.in);
		String entree = sc.nextLine();
		return entree;
	}

	/**
	 * M�thode permettant d'obtenir l'heure actuelle sous forme d'un nombre de
	 * minutes entier.
	 * 
	 * @return L'heure du syst�me en minutes
	 */
	public static int getCurrentTime() {
		// Initialisation du timestamp
		int heures = (int) (System.currentTimeMillis() / (1000 * 60 * 60) % 24 + 1);
		int minutes = (int) (System.currentTimeMillis() / (1000 * 60) % 60);
		return heures * 60 + minutes;
	}
}
