package Hmi;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class AccountManagementWindow extends JInternalFrame implements InternalFrameListener
{
	private MainWindow _parent;
	
	private JLabel labelNumeroCompte 	= new JLabel("Numéro du Compte");
	private JLabel labelPassword 		= new JLabel("Password");
	private JLabel labelCodeGuichet	 	= new JLabel("Code Guichet");
	private JLabel labelNomBanque 		= new JLabel("Nom de la Banque");
	private JLabel labelNomAgence 		= new JLabel("Nom de l'Agence");
	private JLabel labelSoldeCritique	= new JLabel("Solde Critique");

	
	private JTextField numeroCompte = new JTextField(20);
	private JTextField codeGuichet = new JTextField(6);
	private JTextField nomBanque = new JTextField(45);
	private JTextField nomAgence = new JTextField(45);
	private JTextField soldeCritique = new JTextField(10);
	private JTextField password = new JTextField(8);
    private JCheckBox  soldeCritiqueActif    = new JCheckBox("Actif");
	
       
    
	public AccountManagementWindow(MainWindow parent)
	{
		super("Créations, modifications, suppression comptes",
                false,  // on peut changer la taille
                true,  // on peut la fermer
                true,  // on peut la maximiser
                false); // on peut l'iconifier
		
		setLayout(null);

		_parent = parent;
		
		setSize((int)(parent.getWidth()*0.8), (int)(parent.getHeight()*0.8));
		setLocation((int)(parent.getWidth()*0.1), (int)(parent.getHeight()*0.1));
		
		int width = getWidth();
		int height = getHeight();

		int margeGauche = (int) (width * 0.1);
		int margeDroite = margeGauche;

		int margeHaut = (int) (height * 0.1);
		int margeBas  = margeHaut;
		
		
		
		
		JPanel panRensBanc = new JPanel();
		panRensBanc.setBackground(Color.white);
		panRensBanc.setPreferredSize(new Dimension(220, 60));
		panRensBanc.setBorder(BorderFactory.createTitledBorder("Renseignements bancaires"));
		panRensBanc.setLayout(null);
				
	//	labelNumeroCompte.setHorizontalAlignment(SwingConstants.LEFT);
//		labelNumeroCompte.
		
		labelNumeroCompte.setSize(new Dimension(labelNumeroCompte.getText().length()*labelNumeroCompte.getFont().getSize()/2, labelNumeroCompte.getFont().getSize()*2));
		numeroCompte.setSize(new Dimension(numeroCompte.getColumns()*numeroCompte.getFont().getSize()*2,numeroCompte.getFont().getSize()*2));
	
		int deuxiemeColonne = margeGauche + labelNumeroCompte.getWidth() + margeGauche;


		labelNumeroCompte.setLocation(margeGauche,margeHaut);
		numeroCompte.setLocation(deuxiemeColonne, margeHaut);
		
		panRensBanc.add(labelNumeroCompte);
		panRensBanc.add(numeroCompte);
	/*	
		JPanel premiereLigne = new JPanel();
		premiereLigne.setLayout(new GridLayout(0,4));
		
		premiereLigne.add(labelNumeroCompte);
		premiereLigne.add(numeroCompte);
		premiereLigne.add(labelPassword);
		premiereLigne.add(password);
		

		JPanel deuxiemeLigne = new JPanel();
		deuxiemeLigne.setLayout(new FlowLayout());
		
		deuxiemeLigne.add(labelCodeGuichet);
		deuxiemeLigne.add(codeGuichet);
		
		
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(3,0));
		content.add(premiereLigne);
		content.add(deuxiemeLigne);
		*/
		setContentPane(panRensBanc);
		
		/*
		addComponent(labelNumeroCompte,0,0,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(numeroCompte,1,0,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(labelPassword,2,0,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(password,3,0,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		

		addComponent(labelNomBanque,0,1,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(nomBanque,1,1,2,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
	this.*/
		
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        addInternalFrameListener(this);
        setVisible(true);
		
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

}
