import javax.swing.*; import java.awt.*;

public class FenetrePetite extends JFrame
{
	JPanel panneau;
	
	public FenetrePetite()
	{
		setTitle("Pauvre fenetre ...");
		panneau = new JPanel();
		panneau.setPreferredSize(new Dimension(500, 500));
		
		JScrollPane defilement = new JScrollPane(panneau);
		add(defilement);
	}
}
