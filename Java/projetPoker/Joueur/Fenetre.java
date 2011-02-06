//A partir de java.awt;
import java.awt.Color; 
import java.awt.BorderLayout;
 
//A partir de java.awt.event;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

//A partir de java.awt.swing;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;



//A partir de java.awt.swing.event;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;


import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
  *	Classe : Fenetre
  *	Super classe: JFrame (javax.swing.JFrame)
  *	Classes filles: aucune
  *	Interfaces: ActionListener, KeyListener, VariablesGlobales, ProtocoleReseau
  *	Description: classe gerant la fenetre d'une instance joueur. Elle est ensuite remplie par un Panneau
  * 	Auteur:	Yann Danielou
  */

public class Fenetre extends JFrame implements ActionListener, KeyListener, MenuListener, VariablesGlobales, ProtocoleReseau, VariablesTraduction{
 
    private Panneau table = new Panneau();
    private JPanel container = new JPanel();

	private	Bouton seCoucher;
	private	BoutonSuivre suivreOuParole;
	private	BoutonMiser miserOuRelancer;
	private Bouton tapis;

	private JButton diminuerMise;
	private JButton augmenterMise;

	private Client client;

	private	int miseActuelle;

	private int actionAttendue;

	private String nomJoueur;
	
	private JMenuBar barre;
	private JMenu menuPreferences;

  /**
   * Constructeur
	* @param	nom	nom de la fenetre
	* @return 	client	Client, ce qui permet d'envoyer des infos au serveur depuis cette classe
   */
    public Fenetre(String nom, Client client){
		super(nom);

		this.client = client;

		this.setSize(Integer.parseInt(InstanceJoueur.prop.getPropriete("fenetre_x")), Integer.parseInt(InstanceJoueur.prop.getPropriete("fenetre_y")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLocationRelativeTo(null);
		
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());

		barre = new JMenuBar();
		setJMenuBar(barre);

		menuPreferences = new JMenu(InstanceJoueur.lang.getTraduction(trad_configuration));
		barre.add(menuPreferences);

	//	menuPreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));

 		menuPreferences.addMenuListener(this);

		container.add(table, BorderLayout.CENTER);

		seCoucher = new Bouton(InstanceJoueur.lang.getTraduction(trad_seCoucher), SE_COUCHER);
		suivreOuParole = new BoutonSuivre(InstanceJoueur.lang.getTraduction(trad_suivre) + " " + InstanceJoueur.lang.getTraduction(trad_ou) + " " +  InstanceJoueur.lang.getTraduction(trad_parole), 0);
		miserOuRelancer = new BoutonMiser(InstanceJoueur.lang.getTraduction(trad_miser) +  " " + InstanceJoueur.lang.getTraduction(trad_ou) + " " + InstanceJoueur.lang.getTraduction(trad_relancer), 0,0);
		tapis  = new Bouton(InstanceJoueur.lang.getTraduction(trad_tapis), 0);

		diminuerMise = new JButton("-");
		augmenterMise = new JButton("+");


		seCoucher.addActionListener(this);
		suivreOuParole.addActionListener(this);
		miserOuRelancer.addActionListener(this);
		tapis.addActionListener(this);

		diminuerMise.addActionListener(this);
		augmenterMise.addActionListener(this);

		this.desactiverBoutons();

		JPanel panneauBoutons = new JPanel();
		panneauBoutons.add(seCoucher);
		panneauBoutons.add(suivreOuParole);
		panneauBoutons.add(diminuerMise);
		panneauBoutons.add(miserOuRelancer);
		panneauBoutons.add(augmenterMise);
		panneauBoutons.add(tapis);

		container.add(panneauBoutons, BorderLayout.SOUTH);
		

		miseActuelle = 0;
		actionAttendue = ATTENTE_ACTION;

		this.setContentPane(container);
		this.setVisible(true);

	 	this.addKeyListener(this);
	 	seCoucher.addKeyListener(this);
	 	suivreOuParole.addKeyListener(this);
	 	diminuerMise.addKeyListener(this);
	 	miserOuRelancer.addKeyListener(this);
	 	augmenterMise.addKeyListener(this);
	 	tapis.addKeyListener(this);


		nomJoueur = InstanceJoueur.prop.getPropriete("nom_joueur");
		nomJoueur = (String) JOptionPane.showInputDialog(null, InstanceJoueur.lang.getTraduction(trad_entrezNom), InstanceJoueur.lang.getTraduction(trad_identification), JOptionPane.QUESTION_MESSAGE, null, null, nomJoueur);
		
		if(!nomJoueur.equals(InstanceJoueur.prop.getPropriete("nom_joueur")))
			InstanceJoueur.prop.setPropriete("nom_joueur", nomJoueur);

		this.setTitle("Poker\t" + InstanceJoueur.lang.getTraduction(trad_joueur) + " : "+nomJoueur);
    }
 

 //*****************************************************************************************
  //                                    Accesseurs
  //*****************************************************************************************
	
  /**
   * renvoie le panneau table
	* @return 	Carte		La carte qui dont la position est i
   */
	public Panneau getTable(){
		return this.table;
	}	

  /**
   * renvoie le nom du joueur
	* @return 	nomJoueur		String représentant le nom du joueur
   */
	public String getNomJoueur(){
		return this.nomJoueur;
	}



  //*****************************************************************************************
  //                                    Méthodes
  //*****************************************************************************************
	/**
	* C'est la méthode qui sera appelée lors d'un clic sur notre bouton
	* @param		e	ActionEvent: événement produit
	*/
	public void actionPerformed(ActionEvent e){
	  System.out.println("bouton cliqué, actionAttendue = " + actionAttendue);

	  if(e.getSource() == seCoucher){
			actionAttendue = seCoucher.getValeur();
			this.desactiverBoutons();
			client.envoyerDonnees(ENVOYER_ACTION_CHOISIE + SEPARATEUR_MESSAGE + actionAttendue);
		}

	  else if(e.getSource() == suivreOuParole){
			actionAttendue = suivreOuParole.getValeur();
			this.desactiverBoutons();
			client.envoyerDonnees(ENVOYER_ACTION_CHOISIE + SEPARATEUR_MESSAGE + actionAttendue);
		}

	  else if(e.getSource() == miserOuRelancer){
			actionAttendue = miserOuRelancer.getValeur();
			this.desactiverBoutons();
			client.envoyerDonnees(ENVOYER_ACTION_CHOISIE + SEPARATEUR_MESSAGE + actionAttendue);
		}

	  else if(e.getSource() == diminuerMise){
			miserOuRelancer.diminuerValeur(diminuerMise, augmenterMise);
		}

	  else if(e.getSource() == augmenterMise){
			miserOuRelancer.augmenterValeur(diminuerMise, augmenterMise);
		}

	  else if(e.getSource() == tapis){
			actionAttendue = tapis.getValeur();
			this.desactiverBoutons();
			client.envoyerDonnees(ENVOYER_ACTION_CHOISIE + SEPARATEUR_MESSAGE + actionAttendue);
		}

	}

	/**
	* Méthodes inutilisées
	* @param e ActionEvent: événement produit
	*/
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){}


	/**
	* Méthode qui gère l'appuie sur une touche du clavier. Sert lors de l'appuie sur la touche + ou - pour modifier la some de mise ou relance
	* @param e ActionEvent: événement produit
	*/
	public void keyReleased(KeyEvent e){
		System.out.println("touche tapée " + e.getKeyChar());
	if(((e.getKeyChar()) == '-') && (diminuerMise.isEnabled()))
		miserOuRelancer.diminuerValeur(diminuerMise, augmenterMise);
	else if(((e.getKeyChar()) == '+') && (augmenterMise.isEnabled()))
		miserOuRelancer.augmenterValeur(diminuerMise, augmenterMise);
	}






	/**
	* méthode activant les bouttons et leur assignant un texte
	* @param		peutRelancer : boolean qui définit si le joueur peut relancer, ou s'il a atteint le nbre limite de relances
	* @param		miseMinimale: mise minimale que doit effectuer le joueur pour être en jeu (sinon, se couche)
	* @param		miseMaximale: mise maximale que peut effectuer le joueur (pour ne pas dépasser le tapis de l'autre joueur)
	*/
	public void activerBouttons(boolean peutRelancer, int miseMinimale, int miseMaximale) {
	  seCoucher.setEnabled(true);
	  seCoucher.setText(InstanceJoueur.lang.getTraduction(trad_seCoucher));

	  suivreOuParole.setEnabled(true);
	  suivreOuParole.setValeur(miseMinimale);
	  if(miseMinimale == miseActuelle)
	      suivreOuParole.setString(InstanceJoueur.lang.getTraduction(trad_parole));
	  else
	      suivreOuParole.setString(InstanceJoueur.lang.getTraduction(trad_suivre));
	      

	  miserOuRelancer.setEnabled(peutRelancer);
	  miserOuRelancer.setValeur((miseMaximale + miseMinimale) /2);
	  miserOuRelancer.setValeurMin(miseMinimale);
	  miserOuRelancer.setValeurMax(miseMaximale);

	  if(peutRelancer)
	    miserOuRelancer.setString(InstanceJoueur.lang.getTraduction(trad_miser) + " / " + InstanceJoueur.lang.getTraduction(trad_relancer));


	 
	  tapis.setEnabled(peutRelancer);
	  tapis.setValeur(miseMaximale);
	
	  diminuerMise.setEnabled(peutRelancer);
	  augmenterMise.setEnabled(peutRelancer);
	 }

	/**
	* Désactive tous les boutons de la fenetre
	*/
	private void desactiverBoutons(){
	  seCoucher.setEnabled(false);
	  suivreOuParole.setEnabled(false);
	  miserOuRelancer.setEnabled(false);
	  diminuerMise.setEnabled(false);
	  augmenterMise.setEnabled(false);
	  tapis.setEnabled(false);
	}

	/**
	* méthode qui attend qu'un bouton ait été appuyé et renvoie la valeur du bouton afin de déterminer l'action choisie
	* @return 	actionAttendue		action choisie par le joueur
	*/
	public int attendreAction(){
	  System.out.println("action attendue");
	  actionAttendue = ATTENTE_ACTION;
	  while(actionAttendue == ATTENTE_ACTION){
			try {	Thread.sleep(3);} catch (InterruptedException e) {	}
		}
	  System.out.println("action produite : " + actionAttendue);
	  this.desactiverBoutons();
	  return actionAttendue;

	}

	public void menuSelected(MenuEvent e){
		if(e.getSource() == menuPreferences){
			Preference zd = new Preference(this, "Configuration", true);
		}
	}

	public void menuDeselected(MenuEvent e){}
	public void menuCanceled(MenuEvent e){}


	public boolean demanderRecave(){
		JOptionPane jop = new JOptionPane();			
		int option = jop.showConfirmDialog(null, "Voulez-vous arrêter l'animation ?", "Arrêt de l'animation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if(option != JOptionPane.NO_OPTION && option != JOptionPane.CANCEL_OPTION && option != JOptionPane.CLOSED_OPTION)
		  return true;

		else
		  return false;
	}

}