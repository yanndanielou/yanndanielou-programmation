import java.awt.*; import java.awt.event.*;

public class Listener implements KeyListener
{
	int incr_y = 5;
	int incr_x = 5;
	int x = 250;
	int y = 250;
	int dx, dy;
	Drawing d;
	
	public Listener(Drawing dessin)
	{
		d = dessin;
		d.addKeyListener(this);		
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP :
				dx = 0;
				dy = -incr_y;
				break;
			case KeyEvent.VK_DOWN :
				dx = 0;
				dy = incr_y;
				break;
			case KeyEvent.VK_LEFT :
				dx = -incr_x;
				dy = 0;
				break;
			case KeyEvent.VK_RIGHT :
				dx = incr_x;
				dy = 0;
				break;
		}
		Graphics crayon = d.getGraphics();
		crayon.setColor(Color.red);
		crayon.drawLine(x, y, x+dx, y+dy);
		crayon.dispose();
		x += dx;
		y += dy;
	}
	
	public void keyTyped(KeyEvent arg0)
	{

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
