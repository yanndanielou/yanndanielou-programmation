import java.awt.event.*;

public class Listener implements KeyListener // CONTROLEUR
{
	int dx, dy;
	World monde;
	
	public Listener(World monde)
	{
		this.monde = monde;
		this.monde.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP :
				dx = 0;
				dy = -1;
				break;
			case KeyEvent.VK_DOWN :
				dx = 0;
				dy = 1;
				break;
			case KeyEvent.VK_LEFT :
				dx = -1;
				dy = 0;
				break;
			case KeyEvent.VK_RIGHT :
				dx = 1;
				dy = 0;
				break;
		}

	}
	
	public void keyTyped(KeyEvent arg0)
	{

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}