package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ContactsManager
{
	private static ContactsManager Instance = null;
	
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	

	   //Nous allons commencer notre arborescence en créant la racine XML
	   //qui sera ici "personnes".
	   private static Element racine = new Element("personnes");
	   
	   //On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
	   private static org.jdom.Document document = new Document(racine);
	   
	   private String outputFileName = "output.xml";
	   private String inputFileName = outputFileName;
	
	private ContactsManager()
	{

	}
	
	public static ContactsManager GetInstance()
	{
		if(Instance == null)
			Instance = new ContactsManager();
		
		return Instance;		
	}
	
	public void saveContacts()
	{

		//On parcourt tous les contacts que l'on va ensuite sauvegarder
		
		
		Iterator<Contact> it = contacts.iterator();

		try
		{
			while(it.hasNext())
			{
				Contact contactCourant = it.next();

			  //On crée un nouvel Element etudiant et on l'ajoute
		      //en tant qu'Element de racine
		      Element elementContact = new Element("contact");
		      racine.addContent(elementContact);

		      //On crée un nouvel Element nom, on lui assigne du texte
		      //et on l'ajoute en tant qu'Element de etudiant
		      Element lastName = new Element("lastName");
		      lastName.setText(contactCourant.getLastName());
		      elementContact.addContent(lastName);

		      Element firstName = new Element("firstName");
		      firstName.setText(contactCourant.getFirstName());
		      elementContact.addContent(firstName);

		      Element birthDay = new Element("birthday");
		      if(contactCourant.getBirthDay() != null)
		    	  birthDay.setText(contactCourant.getBirthDay().toString());
		      elementContact.addContent(birthDay);

		      Element adress = new Element("adress");
		      adress.setText(contactCourant.getAdress());
		      elementContact.addContent(adress);

		      Element zipCode = new Element("zipCode");
		      zipCode.setText(""+contactCourant.getZipCode());
		      elementContact.addContent(zipCode);

		      Element city = new Element("city");
		      city.setText(contactCourant.getCity());
		      elementContact.addContent(city);

		      Element email = new Element("email");
		      email.setText(contactCourant.getEmailAdress());
		      elementContact.addContent(email);

		      Element homePhone = new Element("homePhone");
		      homePhone.setText(""+contactCourant.getHomePhoneNumber());
		      elementContact.addContent(homePhone);

		      Element mobile = new Element("mobile");
		      mobile.setText(""+contactCourant.getMobileNumber());
		      elementContact.addContent(mobile);
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException lors de listModel.addElement(element)");
		}
		



		//affiche	
		try
	   {
	      //On utilise ici un affichage classique avec getPrettyFormat()
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      sortie.output(document, System.out);
	   }
	   catch (java.io.IOException e){}
		

		//enregistre
	   try
	   {
	      //On utilise ici un affichage classique avec getPrettyFormat()
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
	      //avec en argument le nom du fichier pour effectuer la sérialisation.
	      sortie.output(document, new FileOutputStream(outputFileName));
	   }
	   catch (java.io.IOException e){}
		
	}
	
	public void loadContacts()
	{
	      //On crée une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé ;)
	         document = sxb.build(new File(inputFileName));
	      }
	      catch(Exception e){}

	      //On initialise un nouvel élément racine avec l'élément racine du document.
	      racine = document.getRootElement();

	      //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
	      List listEtudiants = racine.getChildren("contact");

	      //On crée un Iterator sur notre liste
	      Iterator i = listEtudiants.iterator();
	      while(i.hasNext())
	      {
	         //On recrée l'Element courant à chaque tour de boucle afin de
	         //pouvoir utiliser les méthodes propres aux Element comme :
	         //selectionner un noeud fils, modifier du texte, etc...
	         Element courant = (Element)i.next();
	         //On affiche le nom de l'element courant
	         System.out.println(courant.getChild("firstName").getText());
	         
	         //On reconstruit le contact avec les infos contenues dans le xml
	         String firstName;
	         String lastName;
	         Date birthDay = null;
	         String adress;
	         int  zipCode;
	         String city;
	         String emailAdress;
	         int homePhone;
	         int mobilePhone;
	         
	         try
	         {
	        	 firstName = courant.getChild("firstName").getText();
	         }
	         catch(NullPointerException e){firstName = "";}

	         try
	         {
	        	 lastName = courant.getChild("lastName").getText();
	         }
	         catch(NullPointerException e){lastName = "";}

	         try
	         {
	        	String birthDayString = courant.getChild("birthDay").getText();
	        	if(birthDayString.isEmpty() == false)
	        	{
	        		birthDay = new Date(birthDayString);
	        		System.out.println("date = " + birthDay.toString());
	        	}
	         }
	         catch(NullPointerException e){}
	         catch(IllegalArgumentException e){}

	         try
	         {
	        	 adress = courant.getChild("adress").getText();
	         }
	         catch(NullPointerException e){adress = "";}

	         try
	         {
	        	 zipCode = new Integer(courant.getChild("zipCode").getText()).intValue();;
	         }
	         catch(NullPointerException e){zipCode = 0;}

	         try
	         {
	        	 city = courant.getChild("city").getText();
	         }
	         catch(NullPointerException e){city = "";}

	         try
	         {
	        	 emailAdress = courant.getChild("emailAdress").getText();
	         }
	         catch(NullPointerException e){emailAdress = "";}

	         try
	         {
	        	 homePhone = new Integer(courant.getChild("homePhone").getText()).intValue();
	         }
	         catch(NullPointerException e){homePhone = 0;}

	         try
	         {
	        	 mobilePhone = new Integer(courant.getChild("mobilePhone").getText()).intValue();
	         }
	         catch(NullPointerException e){mobilePhone = 0;}
	         
	         Contact toCreate = new Contact(firstName,lastName,birthDay,adress,zipCode,city,homePhone,mobilePhone,emailAdress);
	         contacts.add(toCreate);
	      
	      }
		
	}
	
	public void addContact(Contact toadd)
	{
		contacts.add(toadd);
	}
	
	public void removeContact(Contact toDelete)
	{
		contacts.remove(toDelete);
	}

	/**
	 * @return the contacts
	 */
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
}
