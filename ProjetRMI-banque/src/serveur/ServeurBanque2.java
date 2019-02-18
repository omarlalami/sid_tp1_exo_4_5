package serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gestion.Banque;
import objetsDistants.BanqueImpl;



public class ServeurBanque2 {
	
	public static void main(String[] args) {
		
		//	A LIRE ! createRegistry PASSEZ LE PORT AFFICHER DAS LA CONSOLE !
		// NE PAS OUBLIER DE DONNER LE PORT A Client.java AUSSI
		
		try {
			
			BanqueImpl instance = new BanqueImpl(); // on creer objet
			Banque instance_adapte = (Banque) UnicastRemoteObject.exportObject(instance,0);	// ici on creer genre lobjet distant

			//Registry registre = LocateRegistry.getRegistry();	// on recupere le resgitre
			Registry registre = LocateRegistry.createRegistry(8875);

			registre.bind("AccesBanque_2_Distance",instance_adapte);	// on associe notre instante au nom CalculMathObject dans le registre
			
			System.err.println("serveur banque 2 pret"); // sa nous met en rouge
		}
		catch(Exception e){
			e.getStackTrace();
		}	

	}

}