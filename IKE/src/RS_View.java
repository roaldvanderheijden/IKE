import javax.swing.UIManager.*; // to use the Nimbus look-and-feel
import javax.swing.*; // to use JButton, JTextArea, etc...md
import javax.swing.border.*; // to use borders
import java.awt.*; // for borderlayout
import java.util.*; // for MVC (Observer)
import java.awt.event.*; // for event handling

import de.umass.lastfm.Track; // to put the tracks in an Container that we can give to the model
/**
* class to represent the GUI used in the Recommender System application
* The GUI is built using the Model-View-Controller principle
*
* @author Roald van der Heijden, Thomas van Helden & Robbert van Staveren
* @date 25th November 2011
*/
public class RS_View extends JFrame implements Observer {
	
		// instance variables
	/**
	 * intermediate container for gui-elements, left part of the screen
	 */
	protected JPanel left;
	
	/**
	 * intermediate container for gui-elements, right part of the screen.
	 */
	protected JPanel right;
	
	/**
	 * form is the panel on which the textfields are displayed.
	 */
	protected JPanel form;
	
	/**
	 * results is the <code>JTextArea</code> element on which results from our recommendation system are displayed
	 */
	protected JTextArea results;
	
	/**
	 * Is the first message that is displayed on the left part of the screen of our recommender system.
	 */
	protected JTextArea welcomeMessage;
	
	/**
	 * the <code>JButton</code> used to push through the input to the recommender system.
	 */
	protected JButton execute;
	
	/**
	 * Textfield for name input of <b>first</b> artist
	 */
	protected JTextField firstArtist;
	
	/**
	 * Textfield for track title input of <b>first</b> track
	 */
	protected JTextField firstSong;
	
	/**
	 * Textfield for name input of <b>second</b> artist
	 */
	protected JTextField secondArtist;
	
	/**
	 * Textfield for track title input of <b>second</b> track
	 */
	protected JTextField secondSong;

	/**
	 * Textfield for name input of <b>third</b> artist
	 */
	protected JTextField thirdArtist;
	
	/**
	 * Textfield for track title input of <b>third</b> track
	 */
	protected JTextField thirdSong;
	
	/**
	 * text which is displayed for the <b>first</b> line of the artist-title combination input fields 
	 */
	protected JLabel firstLabel;
	
	/**
	 * text which is displayed for the <b>second</b> line of the artist-title combination input fields
	 */
	protected JLabel secondLabel;
	
	/**
	 * text which is displayed for the <b>third</b> line of the artist-title combination input fields
	 */
	protected JLabel thirdLabel;
	
	/**
	 * responsible for dealing with eventlistening
	 */
	protected RS_Controller controller;
	
	/**
	 * Creates a GUI and gets ready to receive input from the user
	 * 
	 * @param model the <code>Model</code> to which this <code>View</code> belongs
	 */
	public RS_View(RS_Model model) {
		super("Tina Turner's Recommendation Service");
		
		/* for a bit more nicer look we change the 'look-and-feel'
		 * code ripped from java tutorial :)
		 */
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
		
		/*
		Conceptual model of our GUI
		
		
		***********************************  *************************************************
		* JPanel left:			          *  * JPanel right:		                         *
		* 							      *  * JTextArea results on JSCrollPane sp			 *
		* JTextArea welcomeMSG @north     *  *                                               *
		* JPanel with form 3x3 @center    *  *                                               *
		* JButton execute      @south     *  *                                               *
		*                                 *  *                                               *
		***********************************  *************************************************
		
		Just to 'visualize' our arrangement of Objects
		*/
		
			// fix intermediate containers to use with layouting
		left = new JPanel(new BorderLayout());
		right = new JPanel();
		
			// define content to be displayed
		welcomeMessage = new JTextArea(20,10);
				
		form = new JPanel();
		
		firstLabel = new JLabel("1st artist & number");
		secondLabel = new JLabel("2nd artist & number");
		thirdLabel = new JLabel("3rd artist & number");
		firstArtist = new JTextField(20);
		firstSong = new JTextField(20);
		secondArtist = new JTextField(20);
		secondSong = new JTextField(20);
		thirdArtist = new JTextField(20);
		thirdSong = new JTextField(20);
		
		form.setLayout(new GridLayout(3,3));
		form.add(firstLabel);
		form.add(firstArtist);
		form.add(firstSong);
		form.add(secondLabel);
		form.add(secondArtist);
		form.add(secondSong);
		form.add(thirdLabel);
		form.add(thirdArtist);
		form.add(thirdSong);
		
		execute = new JButton("Get me my recommendations!!!");
		
		results = new JTextArea(30,40);
		JScrollPane scrollPane = new JScrollPane(results);
		
		// define properties of content to be displayed
		String message = "Welcome user, please input three artists you like \n and one of their songs you like and Tina will try \nand get you new music you might like";
		welcomeMessage.setText(message);
		
		results.setEditable(false);
		results.setLineWrap(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		results.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red, 1),"Results", TitledBorder.RIGHT, TitledBorder.TOP));
		
		
		// add everything to their respective containers
		
			// whole left side
			left.add(welcomeMessage, BorderLayout.NORTH);
			left.add(form, BorderLayout.CENTER);
			left.add(execute, BorderLayout.SOUTH);
			
			//whole right side
			right.add(scrollPane, BorderLayout.CENTER);
			
		// then finally add left/right to the main displaypanel
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(right, BorderLayout.CENTER);

		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// layout & refresh
		this.pack();
		this.setVisible(true);
		model.addObserver(this); 					// so the view knows about the model
		controller = new RS_Controller(model); 		// dito for the controller (which is a private inner class)
		update(model, null);
	}
	
	// @@@ is this method necessary? perhaps we can fulfill req just by using update(Object arg)? if that one exists
	/**
	 * Defines what happens if our model class changes
	 * In our Recommender System that means, input is given, so output (results for recommendations) changes
	 * Output is set accordingly
	 * @require arg != null
	 * @ensure ??
	 */
	public void update(Observable o, Object arg) {
		if (arg!=null) {
			results.setText((String)arg);
		} else {
			results.setText("");
		}
	}

	/**
	 * class to represent the controller for directing input/output between the model and the GUI of the Recommender System application
	 *
	 * @author Roald van der Heijden, Thomas van Helden & Robbert van Staveren
	 * @date 25th November 2011
	 */
	private class RS_Controller implements ActionListener {
		
		/**
		 * The model to which this <code>RS_Controller</code> belongs
		 */
		private RS_Model model;
		
		/**
		 * Creates a Controller for the View it belongs to
		 * @param model The class used for the Model-part in MVC.
		 * @require model != null
		 * @ensure ??
		 */
		public RS_Controller(RS_Model model) {
			this.model = model;
			RS_View.this.execute.addActionListener(this); 
			/* 
				from the outer class (which is a RS_View)
				take the execute JButton and add this class as a ActionListener.
				for which this class is a special case ;)
			*/
		}
		
		/**
		 * Defines the behaviour of what happens to the application once a specific Event, namely
		 * an <code>ActionEvent</code> has taken place.
		 * @param e the <code>ActionEvent</code> which has taken place
		 * @require e !=null 
		 * @ensure ??
		 */
		public void actionPerformed(ActionEvent e) {
			// @@@do we want this here? not perhaps in a getInputFromForm() or something?
			
			//Create an ArrayList named songs, for the tracks that serve as input to the algorithm(in model)
			Collection<Track> songs = new ArrayList<Track>();

			//Adding tracks to the 'songs'-variable
			Track t = model.getTrack(firstArtist.getText(), firstSong.getText());
			if (t!=null)
				songs.add(t);
			t = model.getTrack(secondArtist.getText(), secondSong.getText());
			
			if (t!=null)
				songs.add(t);
			t = model.getTrack(thirdArtist.getText(), thirdSong.getText());
			
			if (t!=null)
				songs.add(t);
			
			// @@@ and here perhaps setOutput() or DisplayOutput() or DisplayOutputOnScreen()?? whatever...
			String resultSet = model.getRecommendations(songs);
			RS_View.this.update(model, resultSet);
	
		}
	}
}