class InstanceJoueur implements ProtocoleReseau{

	public static Fenetre maFenetre;
	public static Client client;
	public static Langues lang;

	public static Proprietes prop;
	
	public static void main(String [] args)throws Exception{
	
		  prop = new Proprietes("joueur",2);
		  lang = new Langues();

			
		String messageAEnvoyer = NE_RIEN_ENVOYER;
		client = new Client(args);
		maFenetre = new Fenetre ("joueur",client);

		for (int i = 0; ;){
		  if((messageAEnvoyer = Controleur.traiterReceptionDonnees(maFenetre, client.recevoirDonnees())) != NE_RIEN_ENVOYER){
		    client.envoyerDonnees(messageAEnvoyer);
		  }
		}
	}

}
