class exponentielle {

	public static void main (String [] args) {
	
	int exponentiel=3;
	double result_expo=0;

	result_expo=exponentielle(exponentiel);

	System.out.println("Exponentiel exacte de " + exponentiel +" = " + Math.exp(exponentiel));
	System.out.println("Exponentiel dev limites de " + exponentiel +" = " + result_expo);

	}


	public static double exponentielle (int exponentielle_a_calculer){

	double result_expo=0;

	for(int i=0;i<100;i++){
		double temp;
		temp=puissance(exponentielle_a_calculer,i)/factorielle(i);
		result_expo=result_expo+temp;
	}
	
	return result_expo;

	}


	public static double puissance (int nombre, int exposant){
		double result_temp=1;	

		for(int i=0;i<exposant;i++){
			result_temp=result_temp*nombre;
		}
	
	return result_temp;

	}


	public static double factorielle (int nombre_pour_factorielle){
		double factorielle=1;	

		for(int i=1;i<=nombre_pour_factorielle;i++){
			factorielle=factorielle*i;
		}
	
	return factorielle;

	}

}