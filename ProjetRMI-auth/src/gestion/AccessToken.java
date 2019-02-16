package gestion;

import java.io.Serializable;

public class AccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Partie reservee au client
	private String identifiant = "";
	private String motdepasse = "";
	private Boolean modified = false;
	
	// Partie reservee au service d'authentification et a la banque
	private int idToken = 0;
	private int idCompte = -1;
	private int cleBanque = -1;
	
	/**
	 * Va initialiser le token avec des identifiants specifiques a la banque
	 * @param idToken	l'id du Token (genere cote banque)
	 * @param idCompte	l'id du Compte
	 * @param cleBanque	la cle bancaire (genere cote banque)
	 */
	public AccessToken(int idToken, int idCompte, int cleBanque) {
		this.idToken = idToken;
		this.idCompte = idCompte;
		this.cleBanque = cleBanque;
		this.modified = false;
	}
	
	/**
	 * Va mettre a jour l'identifiant client et son mot de passe
	 * Une fois mis a jour, il est impossible de le remodifier
	 * @param identifiant	un identifiant (cote client)
	 * @param mdp			un mot de passe (cote client)
	 */
	public void setIdentifiants(String identifiant, String mdp) {
		if (!this.modified) {
			this.identifiant = identifiant;
			this.motdepasse = mdp;
			this.modified = true;
		}
	}
	
	/**
	 * Retourne l'id du token (cote banque)
	 * @return l'id du token
	 */
	public int getIdToken() {return this.idToken;}
	
	/**
	 * Valide si la cle fournie est bien celle du token (methode appellee par la banque)
	 * @param key une cle
	 * @return vrai si key est bien la cle banque initialement mise par la banque
	 */
	public Boolean validateCleBanque(int key) {return key == this.cleBanque;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		
		
		if (idCompte != other.idCompte)
			return false;
		
		
		if (identifiant == null) {
			if (other.identifiant != null)
				return false;
		} else if (!identifiant.equals(other.identifiant))
			return false;
		
		
		if (motdepasse == null) {
			if (other.motdepasse != null)
				return false;
		} else if (!motdepasse.equals(other.motdepasse))
			return false;
		return true;
	}
}