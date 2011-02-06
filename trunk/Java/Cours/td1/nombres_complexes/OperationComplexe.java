/*Vous allez definir une bibiliotheque de methodes utilitaires pour la manipulation de nombres complexes. Pour cela, vous allez definir une classe Complexe ayant les attributs PartieReelle, partieImmaginaire, Module. Cette classe contiendra un constructeur et une methode d'affichage. Vous definirez également une classe OperationComplexe contenant des méthodes utilitaires pour la manipulation de complexes. Les methodes devant être implémentées sont: calcul du module, addition, multiplication*/



class OperationComplexe {

	public static void main (String [] args) {

	Complexe test1= new Complexe(3,4);
	Complexe test2= new Complexe(2,1);
	//System.out.println(calcul_module(test1));
//	System.out.println(calcul_module(test));
	System.out.println("Pr =" + multiplication_complexe(test1,test2).partieReelle + " et Pi="+ multiplication_complexe(test1,test2).partieImaginaire);

	}


	public static double calcul_module(Complexe nombre){
		double result;

		result= Math.pow(nombre.partieReelle,2) +  Math.pow(nombre.partieImaginaire,2);	
		result= Math.sqrt(result);
		
	return result;
	}

	public static Complexe addition_complexe(Complexe nombre1, Complexe nombre2){
		Complexe resultat_additon = new Complexe (0,0);
		resultat_additon.partieReelle = nombre1.partieReelle + nombre2.partieReelle;
		resultat_additon.partieImaginaire = nombre1.partieImaginaire + nombre2.partieImaginaire;
	//	resultat_additon.module = calcul_module(resultat_additon);
		
	return resultat_additon;
	}

	public static Complexe multiplication_complexe(Complexe nombre1, Complexe nombre2){
		Complexe resultat_multiplication = new Complexe (0,0);

		resultat_multiplication.partieReelle = (nombre1.partieReelle * nombre2.partieReelle) - (nombre1.partieImaginaire * nombre2.partieImaginaire);

		resultat_multiplication.partieImaginaire = (nombre1.partieReelle * nombre2.partieImaginaire) + (nombre1.partieImaginaire * nombre2.partieReelle);
		
	return resultat_multiplication;
	}

}