import 	java.util.*;

public class Tp{
	public static void main (String [] args){

	/*CompteEntreprise ent1 = new CompteEntreprise ("Siemens", 002211100 , 1234.4);
	//sc1.affichage();
	System.out.println(ent1.toString());
	ent1.crediter(120);
	System.out.println(ent1.toString());
	ent1.debiter(230);
	System.out.println(ent1.toString());


	CompteAnonyme ano1 = new CompteAnonyme ("name_anno", "prename anno", 1565456, 100);
	//e1.affichage();
	System.out.println(ano1.toString());
	ano1.crediter(120);
	System.out.println(ano1.toString());
	ano1.debiter(230);
	System.out.println(ano1.toString());

	ComptePersonnePhysique ph1 = new ComptePersonnePhysique ("preno ph1", "nom ph1", 77866687, 1230, false);
	//ph1.affichage();
	System.out.println(ph1.toString());
	}*/

	Banque banque1 = new Banque ("Danielou corp");
	banque1.creer_compte_personne("preno ph1", "nom ph1", 1230.0);
	System.out.println(banque1.Compte.get(1).toString());

	/*banque1.creer_compte_anonyme("name_anno", "prename anno", 100.0);

	banque1.creer_compte_entreprise("Siemens" , 1234.4);*/

	}

	//sc1.affichage();


}
