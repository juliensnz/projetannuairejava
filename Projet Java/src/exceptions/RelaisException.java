package exceptions;

@SuppressWarnings("serial")
public class RelaisException extends Exception {

	public RelaisException() {
		System.out.println("Une erreur c'est produite lors de la cr�ation du relais");
	}

	public RelaisException(String message) {
		System.out.println("Une erreur c'est produite lors de la cr�ation du relais");
		System.out.println(message);
	}
}
