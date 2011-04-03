package Hmi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.Contact;
import Model.ContactsManager;

import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame implements KeyListener, ActionListener
{
	private JButton create;
	
	private JTextField firstName = new JTextField();
	private JTextField lastName = new JTextField();
	private JFormattedTextField  dateNaissance;
	private JDateChooser birthDay = new JDateChooser();
	private JTextField adress = new JTextField();
	private JFormattedTextField  zipCode;
	private JTextField city = new JTextField();
	private JTextField emailAdress = new JTextField();
	private JFormattedTextField homePhoneNumber;
	private JFormattedTextField mobileNumber;
	
	private JLabel labelPrenom 					= new JLabel("Prénom");
	private JLabel labelNom 					= new JLabel("Nom");
	private JLabel labelDateNaissance			= new JLabel("Date de naissance");
	private JLabel labelAdresse 				= new JLabel("Adresse");
	private JLabel labelCodePostal		 		= new JLabel("Code Postal");
	private JLabel labelVille 					= new JLabel("Ville");
	private JLabel labelEmail 					= new JLabel("Adresse Mail");
	private JLabel labelTelephoneFixe 			= new JLabel("Téléphone fixe");
	private JLabel labelTelephonePortable		= new JLabel("Téléphone portable");

	private JButton createContact = new JButton("Add");
	private JButton deleteContact = new JButton("Save");
	private JButton modifyContact = new JButton("Load");
	
	private JList list;
    private DefaultListModel listModel;
    

    private static final int WIDTH_FIRST_COLUMN = 1;
    private static final int WIDTH_SECOND_COLUMN = 2;
    private static final int WEIGHT_FIRST_COLUMN = 0;
    private static final int WEIGHT_OTHER_COLUMNS = 1;
	
	public MainWindow()
	{

		
		super("Repertoire");
		JPanel contentPanel = new JPanel();
		setContentPane(contentPanel);
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout());
		
		JPanel contactForm = new JPanel();
		contactForm.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		formPanel.add(contactForm, BorderLayout.CENTER);
		
		JPanel contactButtons = new JPanel();
		contactButtons.setLayout(new FlowLayout());
		contactButtons.add(createContact);
		contactButtons.add(deleteContact);
		contactButtons.add(modifyContact);
		formPanel.add(contactButtons,BorderLayout.SOUTH);
		
		//Création des controles
		

		dateNaissance 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		zipCode	 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		homePhoneNumber 		= new JFormattedTextField(java.text.NumberFormat.getInstance());
		mobileNumber 	= new JFormattedTextField(java.text.NumberFormat.getInstance());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = WIDTH_FIRST_COLUMN;
		c.weightx = 1;
		
		//Première ligne
		c.gridx = 0;
		c.gridy = 0;
		contactForm.add(labelPrenom, c);

		c.gridx = 1;
		contactForm.add(labelNom, c);

		c.gridx = 2;
		contactForm.add(labelDateNaissance, c);

		//Deuxième ligne
		c.gridx = 0;
		c.gridy = 1;
		contactForm.add(firstName, c);

		c.gridx = 1;
		contactForm.add(lastName, c);

		c.gridx = 2;
		//contactForm.add(dateNaissance, c);
		contactForm.add(birthDay, c);
	//	datePicker.
		
		//Troisième Ligne
		c.gridx = 0;
		c.gridy = 3;
		contactForm.add(labelAdresse, c);

		c.gridx = 1;
		contactForm.add(labelCodePostal, c);

		c.gridx = 2;
		contactForm.add(labelVille, c);
		
		//Quatrième Ligne
		c.gridx = 0;
		c.gridy = 4;
		contactForm.add(adress, c);

		c.gridx = 1;
		contactForm.add(zipCode, c);

		c.gridx = 2;
		contactForm.add(city, c);
		
		//Cinquième Ligne
		c.gridx = 0;
		c.gridy = 6;
		contactForm.add(labelEmail, c);

		c.gridx = 1;
		contactForm.add(labelTelephoneFixe, c);

		c.gridx = 2;
		contactForm.add(labelTelephonePortable, c);
		
		//Sixième Ligne
		c.gridx = 0;
		c.gridy = 7;
		contactForm.add(emailAdress, c);

		c.gridx = 1;
		contactForm.add(homePhoneNumber, c);

		c.gridx = 2;
		contactForm.add(mobileNumber, c);
		
		
		listModel = new DefaultListModel();
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
     //   list.addListSelectionListener(this);
        //list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

		
		setLayout(new BorderLayout());
		contentPanel.add(formPanel,BorderLayout.CENTER);
		contentPanel.add(listScrollPane,BorderLayout.WEST);
		
		//Ecouteurs
		createContact.addActionListener(this);
		deleteContact.addActionListener(this);
		modifyContact.addActionListener(this);
		
		setSize(600,200);
		setVisible(true);
		
		updateList();
	}
		
	public void updateList()
	{				
		ArrayList<Contact> listContacts = ContactsManager.GetInstance().getContacts();
		
		Iterator<Contact> it = listContacts.iterator();
		
		listModel.removeAllElements();

		try
		{
			while(it.hasNext())
			{
				Contact element = it.next();
				listModel.addElement(element.getFirstName() + " " + element.getLastName());
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException lors de listModel.addElement(element)");
		}
	}

	private void resetComponents()
	{
		firstName.setText("");
		lastName.setText("");
		birthDay.setDate(null);
		
		adress.setText("");
		zipCode.setText("");
		city.setText("");
		emailAdress.setText("");
		homePhoneNumber.setText("");
		mobileNumber.setText("");
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		/*if(arg0.getSource() == search)
		{
			updateList();			
		}*/
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == createContact)
		{
			Integer zip = new Integer("0" + zipCode.getText());
			Integer home = new Integer("0" + homePhoneNumber.getText());
			Integer mobile = new Integer("0" + mobileNumber.getText());
			
			Contact newContact = new Contact(firstName.getText(),
											lastName.getText(),
											birthDay.getDate(),
											adress.getText(),
											zip.intValue(),
											city.getText(),
											home,
											mobile,
											emailAdress.getText());
			
			ContactsManager.GetInstance().addContact(newContact);
		//	search.setText("");
			updateList();
			resetComponents();
		}
		else if(arg0.getSource() == deleteContact)
		{
			ContactsManager.GetInstance().saveContacts();
		}
		else if(arg0.getSource() == modifyContact)
		{
			System.out.println("modifyContact");
			ContactsManager.GetInstance().loadContacts();
			updateList();
		}
	}
	
}
