class Polynome_second_degre{
	private double a,b,c;
	private double delta;
	private double r1, r2;

	public Polynome_second_degre()
	{
		this.a = 2;
		this.b = 3;
		this.c = 1;
	}

	public Polynome_second_degre(double a, double b, double c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public void resolution ()
	{
		delta = b*b - 4*a*c;
		if(delta > 0){
			r1 = (-b + Math.sqrt(delta)) / (2*a);
			r2 = (-b - Math.sqrt(delta)) / (2*a);
		}
		else if (delta == 0)
		{
			r1 = -b / (2*a);
			r2 = r1;
		}
	}

	public void affiche_polynome ()
	{
		if(delta > 0)
			System.out.println("Les racines sont " + r1 + "et " + r2);
		else if (delta == 0)
			System.out.println("La racine est " + r1);
		else System.out.println("Aucune racine réelle");
	}

	public static void main(String [] args)
	{
		Polynome_second_degre p1;
		p1 = new Polynome_second_degre(1,4,3);
		p1.resolution();
		p1.affiche_polynome();
	}
}
