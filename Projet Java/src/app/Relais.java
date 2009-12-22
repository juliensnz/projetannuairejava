package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import exceptions.RelaisException;

public class Relais implements Comparable<Relais> {

	private String			nom;
	private int				positionX;
	private int				positionY;
	private static int		id			= 0;
	private List<Service>	services	= new ArrayList<Service>();

	public Relais() {
		this.positionX = 0;
		this.positionY = 0;
		this.nom = "";
		Relais.id++;
	}// Constructeur de base.

	public Relais(int x, int y, String nom) throws RelaisException {
		if (x < 0 || y < 0)
			throw new RelaisException("La position d'un relais ne peut Íêtre nÈégative !");
		else if (nom.isEmpty())
			throw new RelaisException("Le nom d'un relais ne peut pas Íêtre vide !");
		else {
			this.positionX = x;
			this.positionY = y;
			this.nom = nom;
			Relais.id++;
		}
	}// Constructeur paraméÈtrÈé, deux contrÙôles de saisie.

	public void ajouterService(String nomService) throws RelaisException {
		boolean contient = false;
		if (!nomService.isEmpty()) {
			Service nouvService = new Service(nomService);
			for (Service s : this.services)
				if (s.equals(nouvService)) {
					contient = true;
					throw new RelaisException("Le service existe déÈj‡à");
				}
			if (!contient)
				this.services.add(nouvService);
		}
		else
			throw new RelaisException("Le nom du service est vide");
	}// Ajoute le service nomService au relais, s'il n'existe pas déÈj‡à et si

	public void ajouterService(List<String> l) throws RelaisException {
		for (String s : l)
			this.ajouterService(s);
	}// Ajouter une liste de services.

	public void retirerService(Service s) {
		for (Service si : this.services)
			if (si.getNom().equals(s.getNom())) {
				this.services.remove(si);
				return;
			}
	}// Retire un service d'un relais (éÈventuellement plusieurs).

	public boolean contientService(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom))
				return true;
		return false;
	}// Renvoie vrai si le relais contient un service nommé "nom", faux sinon.

	public double distance(int x, int y) {
		return Math.round(Math.sqrt(Math.pow((this.positionX - x), 2) + Math.pow((this.positionY - y), 2)) * 100) / 100;
	}// Renvoie la distance euclidienne entre deux relais.

	public static Relais genererRelais() {
		Relais nouveauRelais = null;
		int x = (int) Math.round(Math.random() * 100);
		int y = (int) Math.round(Math.random() * 100);
		String nom = "Relais n° " + Relais.getId();
		try {
			nouveauRelais = new Relais(x, y, nom);
		}
		catch (RelaisException e) {
			nouveauRelais = new Relais();
		}
		return nouveauRelais;
	}// Méthode pour gÈénÈérer aléatoirement un relais.

	public static List<Relais> genererRelais(int nbRelais) {
		List<Relais> l = new ArrayList<Relais>();
		for (int i = 0; i < nbRelais; i++)
			l.add(Relais.genererRelais());
		return l;
	}// Générer un nombre donné de relais.

	public int compareTo(Relais o) {
		return this.getNom().compareTo(o.getNom());
	}

	public boolean equals(Relais o) {
		return this.positionX == o.positionX && this.positionY == o.positionY && this.equivalentService(o);
	}// Si deux relais ont la même position et les même services, alors ce sont

	public boolean equivalentService(Relais r) {
		Collections.sort(this.services);
		Collections.sort(r.services);
		return this.services.equals(r.services);
	}// Compare les deux liste de services avec la méthode equals.

	public static int getId() {
		return Relais.id;
	}

	public String getNom() {
		return this.nom;
	}

	public int getX() {
		return this.positionX;
	}

	public int getY() {
		return this.positionY;
	}

	public int getNbService() {
		return this.services.size();
	}

	public Service getServices(int i) {
		return this.services.get(i);
	}// Service par index

	public Service getServices(String nom) {
		for (Service s : this.services)
			if (s.getNom().equals(nom))
				return s;
		return null;
	}// Services par nom

	/**
	 * @return La liste des services proposÈs par le relais
	 */
	public List<Service> getServices() {
		return this.services;
	}

	public void setNom(String nom) throws RelaisException {
		if (nom.isEmpty())
			throw new RelaisException("Le nom d'un relais ne peux pas êÍtre vide");
		else
			this.nom = nom;
	}// Changer le nom d'un relais.

	public void setPosition(int x, int y) throws RelaisException {
		if (x <= 0 || y <= 0)
			throw new RelaisException("La position d'un relais ne peut Íêtre néÈgative !");
		else {
			this.positionX = x;
			this.positionY = y;
		}
	}// Changer la position d'un relais.
}
