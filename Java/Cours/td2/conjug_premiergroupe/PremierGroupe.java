public class PremierGroupe{
	public static void main (String [] args){
		String chaine= args[0];

		for(int j = 0;j<6 ; j++){

		if(j==0)
			System.out.print("\nje ");
		if(j==1)
			System.out.print("tu ");
		if(j==2)
			System.out.print("il ");
		if(j==3)
			System.out.print("nous ");
		if(j==4)
			System.out.print("vous ");
		if(j==5)
			System.out.print("ils ");


				for(int i = 0; i<chaine.length()-1;i++){
					System.out.print(chaine.charAt(i));
				
				}
			if(j==1)
				System.out.print("s");
			else if(j==3)
				System.out.print("ons");
			else if(j==4)
				System.out.print("z");
			else if(j==5)
				System.out.print("nt");
	
			System.out.println("");
			
		}
	}
}