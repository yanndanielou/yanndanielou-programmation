package Hmi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame implements ActionListener
{
	//Singleton
	static MainWindow _Instance;
	
	JInternalFrame fenetreUnique;

	JLabel         info = new JLabel(" ");

	JDesktopPane   jdp  = new JDesktopPane();
	
	
	private JMenuBar menuBar 	= new JMenuBar();
	
	private JMenu menuFenetres 		= new JMenu("Fenêtres");
	private JMenu menuComptes 		= new JMenu("Comptes");
	private JMenu menuEcritures 	= new JMenu("Ecritures");
	private JMenu menuBordereaux	= new JMenu("Bordereaux");
	private JMenu menuBudget 		= new JMenu("Budget");
	private JMenu menuResultats 	= new JMenu("Résultats");
	private JMenu menuAbout 		= new JMenu("?");
		
	//Menu fenêtres
	private JMenuItem itemToutFermer = new JMenuItem("Tout fermer");	
	//Menu Comptes
	private JMenuItem itemCreationComptes 			= new JMenuItem("Création des comptes");
	private JMenuItem itemOuvrirCompte    			= new JMenuItem("Ouvrir un compte");
	private JMenuItem itemFermerCompte    			= new JMenuItem("Fermer le compte");
	private JMenuItem itemMouvementsAffectations    = new JMenuItem("Mouvements/Affectations");
	private JMenuItem itemOperationsAutomatiques    = new JMenuItem("Opérations automatiques");
	private JMenuItem itemExporterEcritures		    = new JMenuItem("Exporter les écritures");
	private JMenuItem itemEffacerEcritures		    = new JMenuItem("Effacer les écritures");
	//Menu écritures
	private JMenuItem itemSaisieEcritures		    = new JMenuItem("Effacer les écritures");
	private JMenuItem itemTableauEcritures		    = new JMenuItem("Tableau des écritures");
	//Menu about
	private JMenuItem itemAbout		    			= new JMenuItem("A propos");

	
	
	
	private MainWindow()
	{
	    setTitle("Ma première fenêtre java");
	    setSize(800, 600);
	    //positionnement au centre
	    setLocationRelativeTo(null);
	    //Fermeture sur clic "Fermer" !
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Création des menus
		setJMenuBar(menuBar);
		
	    menuBar.add(menuFenetres);
		menuBar.add(menuComptes);
		menuBar.add(menuEcritures);
		menuBar.add(menuBordereaux);
		menuBar.add(menuBudget);
		menuBar.add(menuResultats);
		menuBar.add(menuAbout);

		//Création des sous menus
		menuFenetres.add(itemToutFermer);
			
		menuComptes.add(itemCreationComptes);
		itemCreationComptes.addActionListener(this);
		menuComptes.add(itemOuvrirCompte);
		menuComptes.add(itemFermerCompte);
		menuComptes.addSeparator();
		menuComptes.add(itemMouvementsAffectations);
		menuComptes.add(itemOperationsAutomatiques);
		menuComptes.addSeparator();
		menuComptes.add(itemExporterEcritures);
		menuComptes.addSeparator();
		menuComptes.add(itemEffacerEcritures);

		menuEcritures.add(itemSaisieEcritures);
		menuEcritures.add(itemTableauEcritures);
		
		menuAbout.add(itemAbout);
		
	    // on place l'interface graphique
	    Container cont = getContentPane();
	 	 
	    jdp.putClientProperty("JDesktopPane.dragMode", "outline");
	    cont.add(jdp, BorderLayout.CENTER);
	 
	    cont.add(info, BorderLayout.SOUTH);
		
		setVisible(true);
	    
	}
	
	public static MainWindow GetInstance()
	{
		if(_Instance == null)
			_Instance = new MainWindow();
		
		return _Instance;
	}
	
	  public void actionPerformed(ActionEvent e)
	  {
	        fenetreUnique = new AccountManagementWindow(this); // on peut l'iconifier
	        jdp.removeAll();
	        jdp.repaint();
	        jdp.add(fenetreUnique);
	 
	        info.setText("On crée la fenêtre");

	  }
}	



/*
class CreateAccountListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent arg0) {
    	System.out.println("creation compte");
    	AccountManagementWindow manager = new AccountManagementWindow();
       	MainWindow.GetInstance().setContentPane(manager);
       	MainWindow.GetInstance().setVisible(true);
           	
    }    
}
*/