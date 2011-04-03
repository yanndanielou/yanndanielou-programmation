package Hmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccountManagementWindow extends JInternalFrame implements ActionListener, KeyListener
{
	private MainWindow _parent;
	
	private JLabel labelNumeroCompte 	= new JLabel("Numéro du Compte");
	private JLabel labelPassword 		= new JLabel("Password");
	private JLabel labelCodeGuichet	 	= new JLabel("Code Guichet");
	private JLabel labelNomBanque 		= new JLabel("Nom de la Banque");
	private JLabel labelNomAgence 		= new JLabel("Nom de l'Agence");
	private JLabel labelSoldeCritique	= new JLabel("Solde Critique");

	
	private JFormattedTextField numeroCompte;
	private JFormattedTextField codeGuichet;
	private JTextField 			nomBanque;
	private JTextField 			nomAgence;
	private JFormattedTextField soldeCritique;
	private JTextField 			password;
    private JCheckBox  			soldeCritiqueActif;
    
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
		
		//Construction des contrôles
		numeroCompte 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		codeGuichet 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		nomBanque 			= new JTextField(45);
		nomAgence 			= new JTextField(45);
		soldeCritique 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		password 			= new JTextField(8);
	    soldeCritiqueActif  = new JCheckBox("Actif", true);
	  
		
		
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
		
		
		//Création du JPanel servant de conteneur à la frame
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(panRensBanc, BorderLayout.CENTER);
		
		//Création du Panel contenant les boutons		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(saveAccount);
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		
		
		//Création des écouteurs
		saveAccount.addActionListener(this);
		soldeCritiqueActif.addActionListener(this);
		
		numeroCompte.addKeyListener(this);
		codeGuichet.addKeyListener(this);
		nomBanque.addKeyListener(this);
		nomAgence.addKeyListener(this);
		soldeCritique.addKeyListener(this);
		password.addKeyListener(this);
	    soldeCritiqueActif.addKeyListener(this);
	    saveAccount.addKeyListener(this);
		
	
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setVisible(true);
        
        setSize(500,200);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == soldeCritiqueActif)
		{
			soldeCritique.setEnabled(soldeCritiqueActif.isSelected());			
		}
		else if(arg0.getSource() == saveAccount)
		{
			
			//On vérifie que le compte a au moins un numéro (seule info indispensable)
			if(numeroCompte.getText().isEmpty() ||
			   numeroCompte.isEditValid() == false)
			{
				 new JOptionPane().showMessageDialog(null, "Le numéro de compte ne doit pas être vide", "Info manquante", JOptionPane.ERROR_MESSAGE);
				 numeroCompte.setText("");
			}
			else
			{
				System.out.println("Création du compte " + numeroCompte.getText());
				setVisible(false);	
			}
				
		}
		else 
		{
			System.out.println("Pas d'action définie pour " + arg0.getSource());
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	     if (e.getKeyCode() == KeyEvent.VK_ENTER)
	     {
	    	saveAccount.setSelected(true);
			saveAccount.doClick();
	     }		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

}
