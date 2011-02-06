import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;


public class Preference extends JDialog implements VariablesTraduction{

	private JLabel nomLabel, fenXLabel, fenYLabel, languesLabel;
	private JComboBox langues;
	private JTextField nom, fenX, fenY;
	private JFrame fenParent;
	
	/**
	 * Constructeur
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public Preference(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(550, 370);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);
		this.fenParent = parent;
	}
	

	
	/**
	 * Initialise le contenu de la boîte
	 */
	private void initComponent(){
			
		//Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(220, 60));
		nom = new JTextField(InstanceJoueur.prop.getPropriete("nom_joueur"));
		nom.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Votre nom"));
		nomLabel = new JLabel(InstanceJoueur.lang.getTraduction(trad_entrezNom));
		panNom.add(nomLabel);
		panNom.add(nom);
		

		
		//La taille de la fenetre
		JPanel panFen = new JPanel();
		JButton majEcran = new JButton ("MAJ taille actuelle");

		majEcran.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    fenX.setText(""+InstanceJoueur.maFenetre.getWidth());
			    fenY.setText(""+InstanceJoueur.maFenetre.getHeight());
			}			
		});


		JButton majFichier = new JButton ("MAJ taille fichier");

		majFichier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    fenX.setText(InstanceJoueur.prop.getPropriete("fenetre_x"));
			    fenY.setText(InstanceJoueur.prop.getPropriete("fenetre_y"));
			}			
		});


		panFen.setBackground(Color.white);
		panFen.setPreferredSize(new Dimension(220, 60));
		fenX = new JTextField(InstanceJoueur.prop.getPropriete("fenetre_x"));
		fenX.setPreferredSize(new Dimension(100, 25));
		fenY = new JTextField(InstanceJoueur.prop.getPropriete("fenetre_y"));
		fenY.setPreferredSize(new Dimension(100, 25));
		panFen.setBorder(BorderFactory.createTitledBorder("Taille fenetre"));
		fenXLabel = new JLabel("X");
		fenYLabel = new JLabel("Y");
		//panFen.setLayout(new GridLayout(2,4));
		panFen.add(fenXLabel);
		panFen.add(fenX);
		panFen.add(fenYLabel);
		panFen.add(fenY);
		panFen.add(majEcran);
		panFen.add(majFichier);
		
      


		//La couleur des langues
		JPanel panLangues = new JPanel();
		panLangues.setBackground(Color.white);
		panLangues.setPreferredSize(new Dimension(220, 60));
		panLangues.setBorder(BorderFactory.createTitledBorder("Langue choisie"));
		langues = new JComboBox();
		langues.addItem("français");
		langues.addItem("english");
		langues.addItem("deutsch");

		if(InstanceJoueur.prop.getPropriete("langue").indexOf("fr") >= 0)
		  langues.setSelectedIndex(0);

		else if(InstanceJoueur.prop.getPropriete("langue").indexOf("en") >= 0)
		  langues.setSelectedIndex(1);

		else if(InstanceJoueur.prop.getPropriete("langue").indexOf("de") >= 0)
		  langues.setSelectedIndex(2);
 

		languesLabel = new JLabel("Langues");
		panLangues.add(languesLabel);
		panLangues.add(langues);
		
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(3,1));
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panFen);
		content.add(panLangues);
		
		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");
		
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
		    //Mise a jour du fichier properties

			//Pour la langue, on prend les deux premiers caracteres affichés
			  InstanceJoueur.prop.setPropriete("langue", langues.getSelectedItem().toString().substring(0,2));
			  InstanceJoueur.prop.setPropriete("fenetre_x", fenX.getText());
			  InstanceJoueur.prop.setPropriete("fenetre_y", fenY.getText());
			  InstanceJoueur.prop.setPropriete("nom_joueur", nom.getText());

			  setVisible(false);
			  try{
			    this.finalize();
			  }
			  catch(Throwable e){}
			}
		});
		
		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				try{
				  this.finalize();
				}
				catch(Throwable e){}
			}
		});
		
		control.add(okBouton);
		control.add(cancelBouton);
		
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}
	
}
