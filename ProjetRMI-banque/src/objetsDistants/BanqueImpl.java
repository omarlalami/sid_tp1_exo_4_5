package objetsDistants;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Random;

import gestion.AccessToken;
import gestion.AuthentificationServeur;
import gestion.Banque;
import gestion.Compte;


public class BanqueImpl implements Banque{

	private int idToken_class=1;
	private AuthentificationServeur authServeur;
	
	//private String nom="ma banque";
	private HashMap <Integer,Compte> comptes = new HashMap<Integer,Compte>();
	
	// dans register il faudra ensuite suprimer des 2 hasmap suivante les references	
	private HashMap <Integer,Integer> token_compte = new HashMap<Integer,Integer>();
	private HashMap <Integer,Integer> token_banque = new HashMap<Integer,Integer>();

	
	@Override
	public AccessToken creerCompte(String nom, String prenom, double soldeInitial) {

		System.out.println("creation compte ....( sans liaison serveur auth )");
		
		int compte_id = comptes.size()+1;

		comptes.put(compte_id,new Compte());
		comptes.get(compte_id).setSolde(soldeInitial);
		comptes.get(compte_id).setNom(nom);
		comptes.get(compte_id).setPrenom(prenom);
		comptes.get(compte_id).setIdCompte(compte_id);
		
		System.out.println("creation compte OK ( sans liaison serveur auth )");
		System.out.println("ajout dans nos hashtable....");

		Random rn = new Random();
		Integer banque_id = rn.nextInt();
		
		token_compte.put(idToken_class, compte_id);
		token_banque.put(idToken_class, banque_id);
		
		System.out.println("ajout dans nos hashtable OK");
		System.out.println("Serveur banque attend le retour token du client ...");

		
		AccessToken a = new AccessToken(idToken_class, compte_id, banque_id);
		
		idToken_class++;
		
		Registry registre;
		
		try {
			System.out.println("serveur baque etablie liaison a serveur autentification ...");
			
			registre = LocateRegistry.getRegistry(8859);
			authServeur = (AuthentificationServeur) registre.lookup("AuthentificationServeur");
			
			System.out.println("serveur baque etablie liaison a serveur autentification ... OK");

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	@Override
	public Boolean depot(int idCompte, double montant, AccessToken token) {
		try {
			if (authServeur.verifierClef(idCompte,token))
				return (comptes.get(idCompte)).depot(idCompte, montant);
			else
				System.out.println("Probleme token pour depot()");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Boolean retrait(int idCompte, double montant, AccessToken token) {
		try {
			if (authServeur.verifierClef(idCompte,token))
				return (comptes.get(idCompte)).retrait(idCompte, montant);
			else
				System.out.println("Probleme token pour retrait()");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Boolean virement(int idCompte, int idCompte2, double montant, AccessToken token1, AccessToken token2) {
		
		try {
			if (authServeur.verifierClef(idCompte,token1) && authServeur.verifierClef(idCompte2,token2))
				return ((comptes.get(idCompte2)).depot(idCompte2, montant) && (comptes.get(idCompte)).retrait(idCompte, montant));
			else
				System.out.println("Probleme token pour virement()");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return false;

	}
	
	@Override
	public double getSolde(int idCompte, AccessToken token) {
		
		try {
			if (authServeur.verifierClef(idCompte,token))
				return (comptes.get(idCompte)).getSolde();
			else 
				System.out.println("Probleme token pour getSolde()");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return -1;	
	}
	
	@Override
	public int registerToken(AccessToken token) {
		
		System.out.println("Serveur banque recoit token du client ... On lance les verifications");

		int id_token = token.getIdToken();
		int id_compte=-1;
		
		 // on verifie que ce token existe
		if(token_compte.get(id_token)!=null && token_banque.get(id_token)!=null)
		{
			// on verifie que c'est le bon id_banque
			if ( token.validateCleBanque(token_banque.get(id_token)))
			{
				try {// on enregistre sur authServeur et on vide nos hashmap !
					id_compte=token_compte.get(token.getIdToken());
					authServeur.link(id_compte,token);
					token_compte.remove(id_token);
					token_banque.remove(id_token);
					System.out.println("Verifications OKon vide nos hashtable OK");
					System.out.println("on vide nos hashtable ... OK");
				}catch(Exception e){
					e.printStackTrace();
				}

			}
			else
				System.out.println("Le serveur banque a un probleme avec le token passé ! token a un mauvais id");
		}
		else
			System.out.println("Le serveur banque a un probleme avec le token passé ! le token n'existe pas");

		System.out.println("Serveur banque : creation compte OK ( avec liaison serveur auth )");

		return id_compte;
	}

}
