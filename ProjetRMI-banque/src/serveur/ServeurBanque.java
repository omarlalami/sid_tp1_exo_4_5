package serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gestion.Banque;
import objetsDistants.BanqueImpl;



public class ServeurBanque {
	
	public static void main(String[] args) {
		
		try {
			
			BanqueImpl instance = new BanqueImpl(); // on creer objet
			Banque instance_adapte = (Banque) UnicastRemoteObject.exportObject(instance,0);	// ici on creer genre lobjet distant

			Registry registre = LocateRegistry.getRegistry();	// on recupere le resgitre
			registre.bind("AccesBanqueDistance",instance_adapte);	// on associe notre instante au nom CalculMathObject dans le registre
			
			System.err.println("serveur pret"); // sa nous met en rouge
		}
		catch(Exception e){
			e.getStackTrace();
		}	

	}

}