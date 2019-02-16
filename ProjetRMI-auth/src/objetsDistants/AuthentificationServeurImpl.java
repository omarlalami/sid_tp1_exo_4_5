package objetsDistants;

import java.util.HashMap;
import gestion.AccessToken;
import gestion.AuthentificationServeur;

public class AuthentificationServeurImpl implements AuthentificationServeur{

	private HashMap <Integer,AccessToken> access_tokens = new HashMap<Integer,AccessToken>();
	
	@Override
	public void link(int idCompte, AccessToken token) {
		access_tokens.put(idCompte,token);
	}

	@Override
	public Boolean verifierClef(int idCompte, AccessToken token) {
		
		return token.equals(access_tokens.get(idCompte));
	}

}