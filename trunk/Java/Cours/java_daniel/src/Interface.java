import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;


public class Interface extends JFrame implements KeyListener
{
	public JPanel pan = new JPanel();
	int x = 250;
	int y = 250;
	int incr_y = 5;
	int incr_x = 5;
	int nb_cible = 5;
	int dx, dy;
	Timer timer;
	
	
	public Interface() //throws InterruptedException
	{
		//this.addKeyListener(this);
		//timer = new Timer(true);
		//timer.start();
				
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		pan.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.setPreferredSize(new Dimension (500, 500));
		pan.setBackground(Color.WHITE);
		getContentPane().add(pan);
		setResizable(false);
		setTitle("Snake");
		setLocation(300, 50);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		setVisible(true);
		//Thread.sleep(100);
		for(int i=0; i<nb_cible;i++)
		{
			affiche_cible();
		}
		//affiche_serpent();
		//Thread.sleep(100);
		
	}

	/*
	public void affiche_serpent()
	{
		Graphics crayon = pan.getGraphics();
		crayon.setColor(Color.red);
		crayon.drawLine(pan.getHeight()/2, pan.getWidth()/2, x+1, y+1);
		crayon.dispose();
		x += 1;
		y += 1;
	}
	*/
	
	public void affiche_cible()
	{
		Graphics crayon = pan.getGraphics();
		Cible cible = new Cible(pan.getHeight(),pan.getWidth());
		crayon.fillOval(cible.get_absc(), cible.get_ord(), cible.get_rayon(), cible.get_rayon());
	}

	@Override
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
		Graphics crayon = pan.getGraphics();
		crayon.setColor(Color.red);
		crayon.drawLine(x, y, x+dx, y+dy);
		crayon.dispose();
		x += dx;
		y += dy;
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
