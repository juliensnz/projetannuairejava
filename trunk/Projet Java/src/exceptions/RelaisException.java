package exceptions;

/**
 * Cette classe est dédiée à la gestion des exceptions lancées par la classe
 * Relais. Elle affiche les messages d'erreurs correspondants à l'exception
 * lancée.
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
		System.out.println("Une erreur c'est produite lors de la crŽéation du relais");
	}

	/**
	 * Affiche dans la sortie standard le message passé en paramètre.
	 * 
	 * @param message
	 *            Message à afficher
	 */
	public RelaisException(String message) {
		System.out.println("Une erreur c'est produite lors de la créŽation du relais");
		System.out.println(message);
	}
}
