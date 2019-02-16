package gestion;

public class Compte {

	private String nom;
	private String prenom;
	private int idCompte;
	private double solde;
	
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public int getIdCompte() {
		return idCompte;
	}
	public double getSolde() {
		return solde;
	}
	
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}
	
	public Boolean depot(int idCompte, double montant) {
		
		if (getIdCompte()==idCompte)
			setSolde(getSolde()+montant);
		
		return true;		
	}

	public Boolean retrait(int idCompte, double montant) {
		
		if (getIdCompte()==idCompte)
			setSolde(getSolde()-montant);
		if(getSolde()<-100)
			return false;
		else
			return true;
		
	}
	
}
