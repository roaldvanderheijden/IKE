/**
 * Class which creates a GUI and Model for accessing our local Last.FM subset & testbank
 * @author Roald van der Heijden
 * @date 2 dec 2011
 */
public class DB_Start {
	/**
	 * Creates a model and a view for the Last.FM-testbank and does initial startup
	 */
	public static void main(String[] args) {
		LocalDatabase dbm = new LocalDatabase("Last.FM");
		DB_AddView dbv = new DB_AddView(dbm);
		dbv.buildGUI();

	}
}
