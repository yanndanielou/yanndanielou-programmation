import java.awt.event.*; 

	public class Listener implements ActionListener
	{
		public Listener()
		{
			Window.Combo_Box.addActionListener(this);
			Window.Clear.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if (e.getSource() == Window.Clear)
				{

				}
				if (e.getSource() == Window.Combo_Box)
				{
					// TODO
				}
			}
			catch (NumberFormatException error)
			{

			}
		}
	}
