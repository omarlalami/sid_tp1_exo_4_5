package gestion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Banque extends Remote{
	
	public AccessToken creerCompte(String nom, String prenom, double soldeInitial) throws RemoteException;
	public Boolean depot(int idCompte, double montant, AccessToken token) throws RemoteException;
	public Boolean retrait(int idCompte, double montant, AccessToken token) throws RemoteException;
	public Boolean virement(int idCompte, int idCompte2, double montant, AccessToken token1, AccessToken token2) throws RemoteException;
	public double getSolde(int idCompte, AccessToken token) throws RemoteException;
	public int registerToken(AccessToken token) throws RemoteException;

}
