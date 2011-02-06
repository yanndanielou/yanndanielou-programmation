import java.io.*;

class Saisie
{
    public static String lire_String ()
    {
	String ligne_entree = null;
	try {
	    InputStreamReader  isr = new InputStreamReader (System.in);
	    BufferedReader br = new BufferedReader (isr);
	    ligne_entree = br.readLine ();
	}
	catch (IOException e) {
	    System.out.println (e);
	}
	return ligne_entree;
    }

    public static String lire_String (String message)
    {
	System.out.println (message);
	return (lire_String ());
    }
    
    public static int lire_int ()
    {
	return Integer.parseInt(lire_String ());
    }
    
    public static int lire_int (String message)
    {
	System.out.println (message);
	return Integer.parseInt(lire_String ());
    }

    
    public static double lire_double ()
    {
	return Double.parseDouble(lire_String ());
    }
    
    public static double lire_double (String message)
    {
	System.out.println (message);
	return Double.parseDouble(lire_String ());
    }

    
    public static char lire_char ()
    {
	String lettre = lire_String ();
	return lettre.charAt (0);
    }
    
    public static char lire_char (String message)
    {
	System.out.println (message);
	String lettre = lire_String (message);
	return lettre.charAt (0);
    }
}

