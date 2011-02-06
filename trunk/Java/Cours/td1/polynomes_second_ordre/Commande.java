public class Commande{
	public static void main (String [] args){
		int nbElem = args.length;
		System.out.println(nbElem);
		for(int i = 0; i < nbElem; i++)
			System.out.println(args[i]);
	}
}