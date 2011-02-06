
public class Main
{
	public static void main(String[] args)
	{
		World monde = new World(500, 500);
		Listener ecouteur = new Listener(monde);
		Snake serpent = new Snake();
		Model model = new Model(serpent, monde, ecouteur);
	}
}