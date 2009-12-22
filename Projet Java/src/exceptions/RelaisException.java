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

	public RelaisException() {
		System.out.println("Une erreur c'est produite lors de la crŽéation du relais");
	}

	public RelaisException(String message) {
		System.out.println("Une erreur c'est produite lors de la créŽation du relais");
		System.out.println(message);
	}
}
