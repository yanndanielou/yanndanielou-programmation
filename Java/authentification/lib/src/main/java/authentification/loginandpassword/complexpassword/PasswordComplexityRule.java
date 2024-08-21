package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.Password;

public interface PasswordComplexityRule {

	/**
	 * L'authentifiant des mainteneurs doit être un mot de passe et respecter la
	 * politique de mots de passe suivante :
	 * 
	 * - Longueur minimale de 8 caractères ;
	 * 
	 * - Caractères complexes : 3 parmi les 4 ensembles (Majuscules, minuscules,
	 * chiffres, caractères spéciaux) ;
	 * 
	 * - 3 essais possibles avant blocage du compte ou temporisation d'accès ;
	 * 
	 * - Historique des 5 derniers mots de passe ;
	 * 
	 * - Durée de vie de 90 jours ;
	 * 
	 * - Le nouveau mot de passe ne doit pas contenir plus de 50% des caractères de
	 * l’ancien mot de passe ;
	 * 
	 * - Tout mot de passe ayant été renouvelé ne peut l’être une nouvelle fois
	 * qu’après un délai minimal de 24 heures.
	 * 
	 * - Tout mot de passe donnant accès à un système qui n’a pas été renouvelé dans
	 * le délai maximal fixé doit être expiré. Il ne doit plus être possible
	 * d’accéder à ce système avec ce mot de passe tant que ce dernier n’a pas été
	 * réinitialisé.
	 * 
	 * - Imposer le renouvellement du mot de passe à l'utilisateur en cas de
	 * modification ou création (pour 1ère utilisation) par l'administrateur
	 * 
	 */
	public enum PasswordComplexityError {
		MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH, COMMON_PASSWORD, PASSWORD_CONTAINS_LOGIN_ERROR, PASSWORD_CONTAINS_MOTIF,
		UNKNOWN_REASON;
	}

	public PasswordComplexityError isNewPasswordAllowed(String newPasswordInClear, String login,
			List<Password> previousPasswordsFromOldestToRecent);

}
