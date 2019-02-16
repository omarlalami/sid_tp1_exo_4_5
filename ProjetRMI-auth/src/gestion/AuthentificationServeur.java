package gestion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthentificationServeur extends Remote{
	
	public void link(int idCompte, AccessToken token) throws RemoteException;
	public Boolean verifierClef(int idCompte, AccessToken token) throws RemoteException;
	
}
