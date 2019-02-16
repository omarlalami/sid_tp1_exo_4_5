package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gestion.AccessToken;
import gestion.Banque;

public class Client {
	
	public Client() {
		
	}
	public static void main(String[] args) {
		
		Registry registre;
		
		try {
			
			registre = LocateRegistry.getRegistry();
			// on recupere le registre

			Banque instance_adapte = (Banque) registre.lookup("AccesBanqueDistance");
			
			
			//creation 1 er compte et 1er token
			AccessToken at = instance_adapte.creerCompte("nom_1", "prenom_1", 2000);
			at.setIdentifiants("identifiant_1","mdp_1");
			int id_compte=instance_adapte.registerToken(at);
			
			if(id_compte==-1)
				System.out.println("Le serveur a un probleme avec le token recu");
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));
			
			instance_adapte.depot(id_compte, 200,at);
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));

			
			
			//creation 2eme compte et 2eme token

			AccessToken at2 = instance_adapte.creerCompte("nom_2", "prenom_2", 1000);
			at2.setIdentifiants("identifiant_2","mdp_2");
			int id_compte2=instance_adapte.registerToken(at2);
			
			if(id_compte2==-1)
				System.out.println("Le serveur a un probleme avec le token recu");
			
			System.out.println("compte "+id_compte2+" solde actuel : " + instance_adapte.getSolde(id_compte2,at2));
			
			instance_adapte.depot(id_compte2, 200,at2);
			
			System.out.println("compte "+id_compte2+" solde actuel : " + instance_adapte.getSolde(id_compte2,at2));

			
			// test anti vol ! on prend 1er compte & 2eme token
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at2));

			
		}
		catch(Exception e) {
			e.getStackTrace();
		}
	}
}