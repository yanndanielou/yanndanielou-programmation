package authentification.loginandpassword.complexpassword;

import java.util.List;

import authentification.loginandpassword.ClearTextPassword;

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
		MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH,
		/** QPWDRQDDIF */
		PASSWORD_ALREADY_USED_PREVIOUSLY,
		/** QPWDCHGBLK */
		PASSWORD_ALREADY_CHANGED_RECENTLY,
		/**
		 * QPWDLMTCHR provides additional security by preventing users from using
		 * specific characters, such as vowels, in a password. Restricting vowels
		 * prevents users from forming actual words for their passwords.
		 */
		PASSWORD_CONTAINS_RESTRICTED_CARACTERS,
		/**
		 * QPWDLMTAJC Consecutive Digits for Passwords
		 */
		CONSECUTIVE_DIGITS,
		/**
		 * QPWDPOSDIF
		 * Character Position Difference for Passwords
		 */
		CHARACTER_POSITION_DIFFERENCE, COMMON_PASSWORD, PASSWORD_CONTAINS_LOGIN_ERROR,
		PASSWORD_CONTAINS_REPETITIVE_PATTERN, CONTAINS_HALF_OF_PREVIOUS_PASSWORD,
		PASSWORD_CONTAINS_REPETITIVE_CHARACTERS, UNKNOWN_REASON;
	}

	
}
