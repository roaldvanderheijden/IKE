import javax.swing.UIManager.*; // to use the Nimbus look-and-feel
import javax.swing.*; // to use JButton, JTextArea, ImageIcon etc...md
import de.umass.lastfm.*; // to put the tracks in an Container that we can give to the model

import java.awt.*; // for borderlayout & gridbagcontraints
import java.awt.event.*;
import java.util.*;
/**
* class to represent the GUI for handling Last.FM-subset
* The GUI is built using the Model-View-Controller principle
*
* @author Roald van der Heijden
* @date 2 December 2011
*/
public class DB_AddView extends JFrame implements Observer {
	
	// instance variables
	//What are these vectors for????
	Vector<String> artNames;
	Vector<String> titles;
		
	protected JPanel main;
	protected JLabel anf, ttf, nuf, status, nr, errorMSG;
	protected JTextField artistName_field, trackTitle_field, numUsers_field;
	protected JButton go_add;
	protected String DBName;
	
	private LocalDatabase local;
	/**
	 * Create a view for managing a local database
	 */
	public DB_AddView(LocalDatabase model) {
		super("Last.FM-subset adder");
		local = LocalDatabase.dbSetup("local");
	}
	
	/**
	 * Builds up the GUI of the DB application
	 */
	// vanuit main uit andere klasse buildGUI() aanroepen (NIET in constructor)
	// Because Uncle Bob says so :)
	protected void buildGUI() {
		setLookAndFeel();
		initializeGUIFields();
		initializeSearchTerms();
		addComponentsToGUI();
		
		// Uncle Bob can go screw himself ;)
		this.getContentPane().add(main);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Initializes search terms
	 */
	protected void initializeSearchTerms() {
		artNames = new Vector<String>(20);
		titles = new Vector<String>(20);
	}

	/**
	 * Sets Look and Feel to the Nimbus Look-and-feel
	 */
	protected void setLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		       		break;
		       	}
		   	}
		} catch (Exception e) {
			// very evil way of catching exceptions, yes, I know! but it'll do for now...
		}
	}
	
	/**
	 * Initializes fields of the GUI
	 */
	protected void initializeGUIFields() {
		main = new JPanel(new GridLayout(5,2));
		
		anf = new JLabel("Write artist name here ->");
		artistName_field = new JTextField(30);
		
		ttf = new JLabel("Write track title here ->");
		trackTitle_field = new JTextField(30);
		
		nuf = new JLabel("maximum number of users to add");
		numUsers_field = new JTextField(10);
		
		status = new JLabel("STATUS: # of users in local testbank: ");
		nr = new JLabel("0");
		
		errorMSG = new JLabel("Error MSG: NONE :D");
		go_add = new JButton("add");
		go_add.addActionListener(new DB_AddView_Controller());
	}
	
	/**
	 * Adds the components on the GUI
	 */
	protected void addComponentsToGUI() {
		main.add(anf);
		main.add(artistName_field);
		main.add(ttf);
		main.add(trackTitle_field);
		main.add(nuf);
		main.add(numUsers_field);
		main.add(status);
		main.add(nr);
		main.add(errorMSG);
		main.add(go_add);
	}
	
	/**
	 * Updates whenever something happens to the database
	 * Sets every field empty and 
	 */
	public void update(Observable o, Object arg) {
		// how to find out what Observable is
		// i.e. if an error was caught, update should change JTF's to "" (both) and change label to errormsg given (in arg?)
	}
	
	/**
	* class to represent the controller for directing input/output to and from the GUI of the database application
	*
	* @author Roald van der Heijden
	* @date 9 December 2011
	*/
	private class DB_AddView_Controller implements ActionListener {
		
		/**
		 * the model to which this database_viewer belongs
		 */
		
		/**
		 * Creates the actionListener for this database_viewer
		 * @param model the model to which this database_viewer and actionListener belongs.
		 */
		public DB_AddView_Controller() {
			//DB_AddView.this.go_add.addActionListener(this); 
			/* 
				from the outer class (which is a DB_AddView)
				take the go_add JButton and add this class as a ActionListener.
				for which this class is a special case ;)
			*/
		}

		/**
		 * Defines what happens when an ActionEvent is generated within the database viewer
		 */
		public void actionPerformed(ActionEvent e) 
		{
			local.fillLocalDatabase(artistName_field.getText(), trackTitle_field.getText());
		}
		
		/**
		 * checks if searchTerms already used
		 * returns true iff artist AND title match
		 */
		protected boolean searchTermsUsed(String artist, String title) {
			return artNames.contains(artist) && titles.contains(title);
		} // && klopt hier :o
	}	
	
}