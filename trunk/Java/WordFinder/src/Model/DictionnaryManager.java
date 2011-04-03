package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import HMI.MainWindow;


public class DictionnaryManager
{
	private String fileName;
	private MainWindow mainWindow;
	
	ArrayList<String> words;
	
	private boolean dictionnaryRead = false;
	
	public static DictionnaryManager Instance = null;
	
	private DictionnaryManager()
	{		
		fileName = "words2.txt";
		words = new ArrayList<String>();
	}
	
	public static DictionnaryManager GetInstance()
	{
		if(Instance == null)
			Instance = new DictionnaryManager();
		
		return Instance;		
	}
	
	public void loadDictionnary()
	{
		BufferedReader reader = null;
		String lineRead;
		
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Le fichier "+ fileName + " n'éxiste pas");
		}
		
		try
		{
			while((lineRead = reader.readLine()) != null)
			{
				words.add(lineRead);
			//	System.out.println(lineRead);			
			}
			dictionnaryRead = true;
			reader.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException:words.add(lineRead)");
		}
	}
	
	public ArrayList<String> getWords()
	{
		System.out.println("getWords");
		if(dictionnaryRead == false)
			loadDictionnary();
		
		return words;
	}
	
	public ArrayList<String> getWordsThatContain(String containt)
	{
		System.out.println("getWordsThatContain " + containt);
		
		if(dictionnaryRead == false)
			loadDictionnary();
		
		if(containt.isEmpty())
		{
			return words;
		}
		else
		{
			String containtIgnoreCase = containt.toLowerCase();
			ArrayList<String> result = new ArrayList<String>();
			
			Iterator<String> it = words.iterator();
	
			while(it.hasNext())
			{
			    String element = it.next();
			    String elementIgnoreCase = element.toLowerCase();
	
			    if(elementIgnoreCase.indexOf(containtIgnoreCase) != -1)
			    {
			    	try
			    	{
			    	result.add(element);
			    	}
					catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.println("ArrayIndexOutOfBoundsException:result.add(element)");
					}
			    }
			}
			
			return result;
		}
			
	}

}
