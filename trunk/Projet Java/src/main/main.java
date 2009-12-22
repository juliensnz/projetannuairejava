package main;

import commande.Console;
import exceptions.RelaisException;

public class main {

	/**
	 * @param args
	 * @throws ConsoleException
	 */
	public static void main(String[] args) {
		try {
			Console.lancer();
		}
		catch (RelaisException e) {
			e.printStackTrace();
		}
	}
}
