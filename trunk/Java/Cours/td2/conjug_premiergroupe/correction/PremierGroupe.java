public class PremierGroupe{
	static final String sujets[]={"je","tu","il", "nous","vous", "ils"};
	static final String terminaisons[] = {"e", "es", "e", "ons", "ez", "ent"};
	static final String terminaison_verbe = "er";

	public static void conjugue(String racine){
		for(int i=0; i< sujets.length;i++){
			System.out.println(sujets[i] + " " + racine + terminaisons[i]);
		}
	}

	public static void main (String [] args){
		String verbe = args[0];
			if(verbe.endsWith(terminaison_verbe)){
 				String racine_verbe = verbe.substring(0,(verbe.length() - terminaison_verbe.length()));
				conjugue(racine_verbe);
			}
			else
				System.out.println(args[0] + " n'est pas un verbe du premier groupe");
	}
}