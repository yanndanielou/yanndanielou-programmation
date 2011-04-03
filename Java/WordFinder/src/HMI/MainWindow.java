package HMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.DictionnaryManager;

public class MainWindow extends JFrame implements KeyListener, ActionListener
{
	private JButton clearSearch;
	private JTextField search;
	
	private JList list;
    private DefaultListModel listModel;
	
	public MainWindow()
	{
		super("Word Finder");
		JPanel contentPanel = new JPanel();
		setContentPane(contentPanel);
		
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout());
		
		
		search = new JTextField(20);
		clearSearch = new JButton("clear");
		listModel = new DefaultListModel();
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
     //   list.addListSelectionListener(this);
        //list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
		
		panelTop.add(search);		
		panelTop.add(clearSearch);
		
		
		setLayout(new BorderLayout());
		contentPanel.add(panelTop,BorderLayout.NORTH);
		contentPanel.add(listScrollPane,BorderLayout.CENTER);
		
		//Ecouteurs
		search.addKeyListener(this);
		clearSearch.addActionListener(this);
		
		pack();
		setVisible(true);
		
		updateList();
	}
	
	
	public void addWord(String word)
	{
		listModel.addElement(word);		
	}
	
	public void updateList()
	{
		ArrayList<String> listWords;
		
		listModel.removeAllElements();

		try
		{
			listWords = DictionnaryManager.GetInstance().getWordsThatContain(search.getText());
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException lors de getWordsThatContain");
			listWords = new ArrayList<String>();
		}
		
		Iterator<String> it = listWords.iterator();

		try
		{
			while(it.hasNext())
			{
			    String element = it.next();
			//    System.out.println("list += " + element);
				listModel.addElement(element);
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException lors de listModel.addElement(element)");
		}

	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == search)
		{
			updateList();			
		}
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == clearSearch)
		{
			search.setText("");
			updateList();
		}
	}
	
}
