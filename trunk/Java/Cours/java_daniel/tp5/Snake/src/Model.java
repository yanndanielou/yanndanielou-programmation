import java.awt.Color;
import java.awt.Graphics;



public class Model
{
	int i, x, y;

	boolean stop = false;
	
	public Model(Snake serpent, World monde, Listener ecouteur)
	{
		Graphics pencil = monde.panel.getGraphics();
		pencil.setColor(Color.black);
		
		while(stop = true)
		{
			serpent.scale.add(new Point(x + ecouteur.dx, y + ecouteur.dy))
			
			for(i = 0; i < serpent.scale.size(); i++)
			{
				//Point head = (Point)serpent.scale.get(0);
				//Point neck = (Point)serpent.scale.get(1);
				
				pencil.drawLine(x, y, x+dx, y+dy);
			}
		pencil.dispose();
		}
	}
}
