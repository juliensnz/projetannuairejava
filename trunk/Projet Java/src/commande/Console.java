package commande;

import java.util.ArrayList;
import java.util.List;
import app.Annuaire;
import exceptions.RelaisException;

public class Console {

	List<Annuaire>	listeAnnuaires	= new ArrayList<Annuaire>();

	public Console() throws RelaisException {
		Annuaire annuaire = new Annuaire();
		annuaire.ajouterRelais(12, 6, "Nantes");
		annuaire.ajouterRelais(23, 45, "Nancy");
		annuaire.ajouterRelais(2, 20, "Lyon");
		annuaire.ajouterRelais(5, 5, "Paris");
		annuaire.getRelais("Nantes").ajouterService("Pain");
		annuaire.getRelais("Nantes").getServices("Pain").ajouterPlage("08h00", "18h00");
		annuaire.getRelais("Nantes").ajouterService("Pizza");
		annuaire.getRelais("Nantes").getServices("Pizza").ajouterPlage("12h00", "19h00");
		annuaire.getRelais("Nantes").ajouterService("WC");
		annuaire.getRelais("Nantes").getServices("WC").ajouterPlage("06h00", "22h00");

		annuaire.getRelais("Lyon").ajouterService("Pain");
		annuaire.getRelais("Lyon").getServices("Pain").ajouterPlage("04h00", "12h00");
		annuaire.getRelais("Lyon").ajouterService("Essence");
		annuaire.getRelais("Lyon").getServices("Essence").ajouterPlage("11h00", "17h00");
		annuaire.getRelais("Lyon").ajouterService("WC");
		annuaire.getRelais("Lyon").getServices("WC").ajouterPlage("04h00", "23h00");

		annuaire.getRelais("Paris").ajouterService("Snack");
		annuaire.getRelais("Paris").getServices("Snack").ajouterPlage("09h00", "20h00");
		annuaire.getRelais("Paris").ajouterService("Essence");
		annuaire.getRelais("Paris").getServices("Essence").ajouterPlage("08h00", "19h23");
		annuaire.getRelais("Paris").ajouterService("WC");
		annuaire.getRelais("Paris").getServices("WC").ajouterPlage("06h00", "22h00");
		this.ajouterAnnuaire(annuaire);
		this.ajouterAnnuaire(annuaire);
		System.out.println("Bienvenue dans notre programme d'annuaire");
		System.out.println("Que voulez vous faire ?");
		this.menuPrincipal();
	}

	public static void lancer() throws RelaisException {
		new Console();
	}

	public void menuPrincipal() {
		System.out.println("1. Ajouter un annuaire");
		System.out.println("2. Utiliser / Editer un annuaire");
		System.out.println("3. Comparer deux annuaires");
		System.out.println("Autre. Quitter");
		System.out.print("Choix : ");
		int entree = Interface.getInt();

		switch (entree) {
			case 1:
				Annuaire a = new Annuaire();
				this.ajouterAnnuaire(a);
				this.menuPrincipal();
				break;
			case 2:
				for (int i = 1; i <= this.listeAnnuaires.size(); i++)
					System.out.println(i + ". Annuaire n°" + i);
				System.out.println("Autre. Annuler");
				System.out.print("Choix : ");
				entree = Interface.getInt();
				if (entree > 0 && entree <= this.listeAnnuaires.size()) {
					entree--;
					InterfaceAnnuaire.menuAnnuaire(this.listeAnnuaires.get(entree));
				}
				this.menuPrincipal();
				break;
			case 3:
				for (int i = 1; i <= this.listeAnnuaires.size(); i++)
					System.out.println(i + ". Annuaire n°" + i);
				System.out.println("Autre. Annuler");
				int choix1,
				choix2;
				System.out.print("Comparer l'annuaire n° ");
				choix1 = Interface.getInt();
				System.out.print("avec l'annuaire n° ");
				choix2 = Interface.getInt();
				if (choix1 > 0 && choix1 <= this.listeAnnuaires.size() && choix2 > 0 && choix2 <= this.listeAnnuaires.size()) {
					choix1--;
					choix2--;
					if (this.listeAnnuaires.get(choix1).equals(this.listeAnnuaires.get(choix2)))
						System.out.println("Ces deux annuaires sont éŽgaux \n");
					else
						System.out.println("Ces deux annuaires sont diffŽérents \n");
				}
				else
					System.out.println("Une erreur s'est produite. Retour au menu principal.");
				this.menuPrincipal();
				break;
			default:
				System.out.println("Fin des tests. Au revoir.");
				System.exit(0);
				break;
		}
	}

	public void ajouterAnnuaire(Annuaire annuaire) {
		this.listeAnnuaires.add(annuaire);
		System.out.println("Un annuaire a étéŽ crééŽŽ.");
	}
}
