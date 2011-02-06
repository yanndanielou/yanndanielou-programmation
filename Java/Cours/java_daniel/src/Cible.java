
public class Cible extends Point
{
	private int absc;
	private int ord;
	private int rayon;
	
	Cible(int x, int y)
	{
		super(x,y);
		this.absc = (int) (Math.random()*x);
		this.ord = (int) (Math.random()*y);
		this.rayon = 10;
	}
	
	public int get_absc()
	{
		return this.absc;
	}
	
	public int get_ord()
	{
		return this.ord;
	}
	
	public int get_rayon()
	{
		return this.rayon;
	}
	
}
