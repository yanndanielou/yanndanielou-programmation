class nombreParfait{
	static void EstParfait(int n){
		int somme_diviseur=1;
			for(int i=2; i<=n/2; i++)
				if(n%i==0)
					somme_diviseur+=i;

			if(somme_diviseur==n){
				System.out.println(n + " est parfait");
				System.out.print(n + " = 1");

				for(int i=2; i<= n/2; i++)
					if(n%i==0)
						System.out.print(" + " + i);
					System.out.println();
			}
	}

	public static void main (String [] args){
		EstParfait(28);
	}
}