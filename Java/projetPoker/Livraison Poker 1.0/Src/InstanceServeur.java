
class InstanceServeur implements VariablesGlobales{

	public static Serveur serveur;
	public static Proprietes prop;
	public static Scores scores;

  public static void main (String [] args) throws Exception {

  scores = new Scores();
  prop = new Proprietes("serveur",1);
  new Log();

  System.out.println("taille x= " + prop.getPropriete("taille_x"));
  System.out.println("taille y= " + prop.getPropriete("taille_y"));
  System.out.println("taille ya= " + prop.getPropriete("taille_ya"));
prop.setPropriete("taille_ya", "290");

prop.setPropriete("taille_x", "123");
  
	Log.ajouterTrace("LANCEMENT SERVEUR", INFO);
	serveur = new Serveur();

      Partie p1 = new Partie();


      while(true){
	p1.demmarrer(p1.getJoueur1(), p1.getJoueur2());
	p1.demmarrer(p1.getJoueur2(), p1.getJoueur1());
    }


  }










}