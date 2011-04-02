package Hmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class AccountManagementWindow extends JInternalFrame implements InternalFrameListener, ActionListener
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
    
    private JButton    saveAccount = new JButton("Créer");
	
    
    private static final int WIDTH_FIRST_COLUMN = 1;
    private static final int WEIGHT_FIRST_COLUMN = 0;
    private static final int WEIGHT_OTHER_COLUMNS = 1;
       
    
	public AccountManagementWindow(MainWindow parent)
	{
		super("Créations, modifications, suppression comptes",
                false,  // on peut changer la taille
                true,  // on peut la fermer
                false,  // on peut la maximiser
                false); // on peut l'iconifier
		
		setLayout(null);

		_parent = parent;
		
		JPanel panRensBanc = new JPanel();
		panRensBanc.setBackground(Color.white);
		panRensBanc.setPreferredSize(new Dimension(220, 60));
		panRensBanc.setBorder(BorderFactory.createTitledBorder("Renseignements bancaires"));
	
		panRensBanc.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//Première ligne
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = WEIGHT_FIRST_COLUMN;
		panRensBanc.add(labelNumeroCompte, c);

		c.gridx += c.gridwidth;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = WEIGHT_OTHER_COLUMNS;
		panRensBanc.add(numeroCompte, c);

		c.gridx += c.gridwidth;
		c.gridy = 0;
		c.gridwidth = 1;
		panRensBanc.add(labelPassword, c);

		c.gridx += c.gridwidth;
		c.gridy = 0;
		c.gridwidth = 1;
		panRensBanc.add(password, c);
		
		//Deuxième ligne
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = WEIGHT_FIRST_COLUMN;
		panRensBanc.add(labelCodeGuichet, c);

		c.gridx += c.gridwidth;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = WEIGHT_OTHER_COLUMNS;
		panRensBanc.add(codeGuichet, c);

		//Troisième ligne
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = WEIGHT_FIRST_COLUMN;
		panRensBanc.add(labelNomBanque, c);

		c.gridx += c.gridwidth;
		c.gridy = 2;
		c.gridwidth = 4;
		c.weightx = WEIGHT_OTHER_COLUMNS;
		panRensBanc.add(nomBanque, c);



		//Quatrième ligne
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = WEIGHT_FIRST_COLUMN;
		panRensBanc.add(labelNomAgence, c);

		c.gridx += c.gridwidth;
		c.gridy = 3;
		c.gridwidth = 4;
		c.weightx = WEIGHT_OTHER_COLUMNS;
		panRensBanc.add(nomAgence, c);
		


		//Cinquième ligne
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = WEIGHT_FIRST_COLUMN;
		panRensBanc.add(labelSoldeCritique, c);

		c.gridx += c.gridwidth;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = WEIGHT_OTHER_COLUMNS;
		panRensBanc.add(soldeCritique, c);

		c.gridx += c.gridwidth;
		c.gridy = 4;
		c.gridwidth = 1;
		panRensBanc.add(soldeCritiqueActif, c);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(panRensBanc, BorderLayout.CENTER);
		
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(saveAccount);
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		
		
		//Création des écouteurs
		saveAccount.addActionListener(this);
		
        addInternalFrameListener(this);
		
	
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setVisible(true);
        
        setSize(500,200);
		
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

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		if(arg0.getSource() == saveAccount)
		{
			System.out.println("Création du compte");
			setVisible(false);
		}
		
	}

}
