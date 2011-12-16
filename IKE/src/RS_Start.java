/**
 * Class which creates a GUI and Model and starts up the whole of the recommender system
 * @author Roald van der Heijden, Thomas van Helden & Robbert van Staveren
 * @date 27 nov 2011
 */
public class RS_Start {
	
	/**
	 * Creates the model and gui and starts up the system.
	 */
	public static void main(String[] args) {
			RS_Model model = new RS_Model();
			RS_View view = new RS_View(model);
		}	
}
