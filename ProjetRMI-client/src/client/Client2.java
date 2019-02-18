package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gestion.AccessToken;
import gestion.Banque;

public class Client2 {

	public static void main(String[] args) {
		
		Registry registre;
		Registry registre2;
		
		try {
		
	
			registre = LocateRegistry.getRegistry(11344);
			// on recupere le registre
	
			Banque instance_adapte = (Banque) registre.lookup("AccesBanqueDistance");

			
			//creation 1 er compte et 1er token
			AccessToken at = instance_adapte.creerCompte("nom_1", "prenom_1", 2000);
			at.setIdentifiants("identifiant_1","mdp_1");
			int id_compte=instance_adapte.registerToken(at);
			
			if(id_compte==-1)
				System.out.println("Le serveur a un probleme avec le token recu");
			//creation 2 er compte et 2er token
	
			AccessToken at2 = instance_adapte.creerCompte("nom_2", "prenom_2", 1000);
			at2.setIdentifiants("identifiant_2","mdp_2");
			int id_compte2=instance_adapte.registerToken(at2);
			
			if(id_compte2==-1)
				System.out.println("Le serveur a un probleme avec le token recu");
			
			
			
			// premier virement entre 2 compte de meme banque !
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));
			System.out.println("compte "+id_compte2+" solde actuel : " + instance_adapte.getSolde(id_compte2,at2));
	
			instance_adapte.virement(id_compte, id_compte2, 20, at, at2);
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));
			System.out.println("compte "+id_compte2+" solde actuel : " + instance_adapte.getSolde(id_compte2,at2));
			
			
			// 2 eme virement entre 2 compte de meme banque mais avec des mauvais token !
			instance_adapte.virement(id_compte, id_compte2, 20, at2, at2);

			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));
			System.out.println("compte "+id_compte2+" solde actuel : " + instance_adapte.getSolde(id_compte2,at2));
			
			
			
								// NOTRE COMTPE DE LA 2 EME BANQUE
			
								// A VOIR VOMMENT GERER LE VIREMENT CAR C'EST PAS LA MEME BANQUE
								// CA IMPLIQUE QUE C'EST PAS LA MEME LISTE DE COMPTES
			
			registre2 = LocateRegistry.getRegistry(11344);
			// on recupere le registre
	
			Banque instance_adapte2 = (Banque) registre2.lookup("AccesBanque_2_Distance");		
			
			
			AccessToken at3 = instance_adapte2.creerCompte("nom_3", "prenom_3", 1500);
			at3.setIdentifiants("identifiant_3","mdp_3");
			int id_compte3=instance_adapte2.registerToken(at3);
			
			if(id_compte3==-1)
				System.out.println("Le serveur a un probleme avec le token recu");
			
			System.out.println("compte "+id_compte+" solde actuel : " + instance_adapte.getSolde(id_compte,at));
			System.out.println("compte "+id_compte3+" solde actuel : " + instance_adapte2.getSolde(id_compte3,at3));
			
			instance_adapte.virement(id_compte, id_compte3, 50, at, at3);

			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
