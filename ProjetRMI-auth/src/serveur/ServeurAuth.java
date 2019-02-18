package serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gestion.AuthentificationServeur;
import objetsDistants.AuthentificationServeurImpl;

public class ServeurAuth {
	
	public static void main(String[] args) {
		
		//	A LIRE ! createRegistry PASSEZ LE PORT AFFICHER DAS LA CONSOLE !
		// NE PAS OUBLIER DE DONNER LE PORT A BanqueImpl.java AUSSI
		
		try {
			
			AuthentificationServeurImpl instance = new AuthentificationServeurImpl();
			AuthentificationServeur instance_adapte = (AuthentificationServeur) UnicastRemoteObject.exportObject(instance,0); 
			
			//Registry registre = LocateRegistry.getRegistry();	// on recupere le resgitre
			Registry registre = LocateRegistry.createRegistry(11296);
			
			registre.bind("AuthentificationServeur",instance_adapte);	// on associe notre instante au nom CalculMathObject dans le registre

			System.err.println("serveur auth pret"); // sa nous met en rouge
			
		}catch(Exception e) {
			e.getStackTrace();
		}


	}
}
