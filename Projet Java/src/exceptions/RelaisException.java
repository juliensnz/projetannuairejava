package exceptions;

/**
 * Cette classe est d�di�e � la gestion des exceptions lanc�es par la classe
 * Relais. Elle affiche les messages d'erreurs correspondants � l'exception
 * lanc�e.
 * 
 * @author Maxime Bury, Julien Sanchez
 * 
 */
@SuppressWarnings("serial")
public class RelaisException extends Exception {

	/**
	 * Affiche un message d'erreur dans la sortie standard.
	 */
	public RelaisException() {
		System.out.println("Une erreur c'est produite lors de la cr��ation du relais");
	}

	/**
	 * Affiche dans la sortie standard le message pass� en param�tre.
	 * 
	 * @param message
	 *            Message � afficher
	 */
	public RelaisException(String message) {
		System.out.println("Une erreur c'est produite lors de la cr�ation du relais");
		System.out.println(message);
	}
}
