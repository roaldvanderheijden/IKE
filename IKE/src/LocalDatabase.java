import java.sql.*;
import java.util.Collection;

import de.umass.lastfm.User;
import de.umass.lastfm.Track;

/**
 * Class to create and manage a local SQLite database to use as a Last.FM-subset databank
 * @author Roald van der Heijden
 * @date 5th december 2011
 */
public class LocalDatabase {
	
	// instance variables
	private String name;
	private Class driver;
	private Connection conn;
	private boolean connectionOpened = false;
	
	/**
	 * Creates a database with given name
	 * @param DBName, the name for the database file
	 * @require DBName !=null;
	 * @ensure this.DB_Exists()==TRUE;
	 */
	public LocalDatabase(String DBName) {
		name = DBName;	
	}
	
	/**
	 * Returns the name of the current database
	 * @return the name of the current database
	 */
	protected String getName() {
		return name;
	}
	
	/**
	 * Returns the name of the driver to connect to the database
	 */
	protected String getDriverName() {
		return driver.getName();
	}
	
	
	/**
	 * Loads SQLite driver
	 */
	protected void loadDriver() {
		try {
			driver = Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			System.out.println("Can't load JDBC Driver.  Make sure classpath is correct");
		}
	}
	
	/**
	 * Setup connection to local DB and sets flag
	 */
	protected void openConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + name);
			if (conn == null) {
				connectionOpened = false;
			} else {
				connectionOpened = true;
			}
		} catch (SQLException e) {
			System.out.println("Connect problem: " + e.getMessage());
			conn = null;
		}
	}
	
	/**
	 * Checks for open connection to local database
	 * @return TRUE iff connection to local database is open, FALSE otherwise
	 */
	protected boolean connectionOpened() {
		return connectionOpened;
	}
	
	/**
	 * Creates a table by excuting given statement in query
	 */
	protected void createTable(String query) {
		// no check in method for exists
		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query);
			stat.close(); // check again closing stats/conns
		} catch (SQLException e) {
			System.out.println("Something went horribly wrong creating tables\n");
			System.out.println("Following statement was NOT executed:\n" + query);
		}
	}
	
	/**
	 * Creates a table Persons and adds it to the current database if it wasn't already there
	 * Persons contain, last.fm-username, age, country, language & gender.
	 */
	protected void createTablePersons() {
		String query = 
				"CREATE TABLE IF NOT EXISTS Persons (" 	+
				"lfm_username	VARCHAR(128)," 			+
				"age 			INTEGER," 				+
				"country		VARCHAR(128),"	 		+
				"language		VARCHAR(64),"	 		+
				"gender			VARCHAR(8),"			+
				"PRIMARY KEY 	(lfm_username)"			+
				");"
				;
		createTable(query);
	}
	
	/**
	 * Creates a table Recordings and adds it to the current database if it wasn't already there
	 * Recordings contains: artist name/track title
	 */
	protected void createTableRecordings() {
		String query = 
				"CREATE TABLE IF NOT EXISTS Recordings (" 							+
				"artist_name	VARCHAR(128)," 										+
				"track_name		VARCHAR(128)," 										+
				"CONSTRAINT unique_track PRIMARY KEY (artist_name, track_name)" 	+
				");"
				;
		createTable(query);
	}
	/**
	 * Creates a table Listenings and adds it to the current database if it wasn't already there
	 * Recordings contains: which user listens to which track
	 */
	protected void createTableListenings() {
		String query = 
				"CREATE TABLE IF NOT EXISTS Listenings (" 							+
				"user			VARCHAR(128)," 										+
				"listens_to		VARCHAR(259)," 										+ 
				"FOREIGN KEY (user) REFERENCES Persons(lfm_username)," 				+
				"FOREIGN KEY (listens_to) REFERENCES Recordings(unique_track)"		+
				");"																
				;
		createTable(query);
	}

	/**
	 * Checks if given name is already a table in the current database
	 * @return TRUE iff name is indeed a table in the current database, FALSE otherwise
	 */
	protected boolean hasTable(String tableName) {
		boolean tableNameAlreadyExists = false;
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			
			ResultSet tableNameSet = dbm.getTables(null, null, "Persons", null);
			if (tableNameSet.next()) {
				tableNameAlreadyExists = true;
			}
			tableNameSet.close();
		} catch (SQLException e) {
			System.out.println("ERROR in addTable(String tableName)...");
		}
		return tableNameAlreadyExists;
	}
	/**
	 * Closes any open connection to the database, assuming the database isn't locked.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("ERROR: cannot close connection to local database");
		}
	}
		
	/**
	 * Inserts given data into persons table
	 * At this moment in time, still stubs :)
	 * 
	 * @param DBName the name of the Database for which this method will insert example data.
	 */
	protected void insertDataIntoPersons(Collection<User> users) {
		// what happens if data already there?
		// big issue :(
		
			try {
				openConnection();
				PreparedStatement prep = conn.prepareStatement("INSERT INTO Persons VALUES (?, ?, ?, ?, ?);");
				for (User u : users) {
					if(u.getName() != null)
					prep.setString(1, u.getName());
					System.out.println(u.getName());
					if(u.getAge() != 0)
					prep.setInt(2, u.getAge());
					if(u.getCountry() != null)
					prep.setString(3, u.getCountry());
					if(u.getLanguage() != null)
					prep.setString(4, u.getLanguage());
					if(u.getGender() != null)
					prep.setString(5, u.getGender());
					prep.addBatch();
				}
				
				conn.setAutoCommit(false);
	        	prep.executeBatch();
	        	conn.setAutoCommit(true);
	        	
	        	conn.close();
			} catch (SQLException e) {
				System.out.println("Something went wrong with inserting batched data into Persons");
				System.out.println("Check System.err for details");
				e.printStackTrace(System.err);
			}
	}
	
	/**
	 * Inserts given data into persons table
	 * At this moment in time, still stubs :)
	 * 
	 * @param DBName the name of the Database for which this method will insert example data.
	 */
	protected void insertDataIntoRecordings(Collection<Track> tracks) {
		// what happens if data already there?
		// big issue :(
		
			try {
				PreparedStatement prep = conn.prepareStatement("INSERT INTO Recordings values (?, ?);");
				
				for (Track t : tracks) {
					prep.setString(1, t.getArtist());
					prep.setString(2, t.getName());
					prep.addBatch();
				}
				
				conn.setAutoCommit(false);
	        	prep.executeBatch();
	        	conn.setAutoCommit(true);
	        	
	        	conn.close();
			} catch (SQLException e) {
				System.out.println("Something went wrong with inserting batched data into Recordings");
				System.out.println("Check System.err for details");
				e.printStackTrace(System.err);
			}
			
	}
	
	/**
	 * Inserts given data into persons table
	 * At this moment in time, still stubs :)
	 * 
	 * @param DBName the name of the Database for which this method will insert example data.
	 */
	protected void insertDataIntoListenings(User u, Collection<Track> tracks) {
		// what happens if data already there?
		// big issue :(
		
			try {
				PreparedStatement prep = conn.prepareStatement("INSERT INTO Listenings values (?, ?, ?);");
				
				for (Track t : tracks) {
					prep.setString(1, u.getName());
					prep.setString(2, t.getName());
					//DONNO IF THIS WORKS, LastFM Api doesnt show it but eclipse does....
					prep.setInt(3, u.getPlaycount());
					prep.addBatch();
				}
				
				conn.setAutoCommit(false);
	        	prep.executeBatch();
	        	conn.setAutoCommit(true);
	        	
	        	conn.close();
			} catch (SQLException e) {
				System.out.println("Something went wrong with inserting batched data into Listenings");
				System.out.println("Check System.err for details");
				e.printStackTrace(System.err);
			}
			
	}
	
	/**
	 * Counts the number of entries in Persons table
	 * @return the number of users in persons
	 */
	protected int countUsers() {
		int result = -1;
		try {
			Statement stat = conn.createStatement();
			System.out.println(stat.toString());
			ResultSet rs = stat.executeQuery("SELECT COUNT(lfm_username) FROM Persons;");
			result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("Something went wrong counting users from Persons");
		}
		
		return result;
	}
	/**
	 * Counts the number of entries in the designated table
	 * @return the number of users in the designated table
	 */
	protected int countRows(String tableName) {
		int result = -1;
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select count(*) from " + tableName + ";");
			result = rs.getInt(rs.getRow());
			// freakin hell wat een @#*$@#($* werk om deze op te zoeken :/
		} catch (SQLException e) {
			System.out.println("Something went wrong counting users from Persons");
		}
		
		return result;
	}
	
	/**
	 * Selects entries from Persons and returns a collection of Users complying with
	 * the demands made in where-statement
	 */
	protected Collection<RS_User> selectUsersFromPersons(String query) {
		Collection<RS_User> users = new SortedArrayList<RS_User>();
		//TODO Robbert: is deze keuze hier ok???
		
		ResultSet rs;
		try {
			Statement stat = conn.createStatement();
			rs = stat.executeQuery(query);
			
			while (rs.next()) {	
				String username = rs.getString("lfm_username");
				int age = rs.getInt("age");
				String country = rs.getString("country");
				String language = rs.getString("language");
				String gender = rs.getString("gender");
				RS_User rsu = new RS_User(username, age, country, language, gender);
				users.add(rsu);
			}	
		} catch (SQLException e) {
			System.out.println("Something went wrong selecting results from Persons");
		}
		return users;
	}
	
	/**
	 * Selects entries from Recordings and returns a collection of Tracks complying with
	 * the demands made in where-statement
	 */
	protected Collection<RS_Track> selectTracksFromRecordings(String query) {
		Collection<RS_Track> tracks = new SortedArrayList<RS_Track>();
		//TODO Robbert: is deze keuze hier ok???
		
		ResultSet rs;
		try {
			Statement stat = conn.createStatement();
			rs = stat.executeQuery(query);
			
			while (rs.next()) {	
				String artistName = rs.getString("artist_name");
				String trackName = rs.getString("track_name");
				RS_Track rst = new RS_Track(trackName, null, artistName, 0);
				tracks.add(rst);
				
				//TODO overleg met Thomas/Robbert welke info hier opslaan/ophalen??
			}	
		} catch (SQLException e) {
			System.out.println("Something went wrong selecting results from Recordings");
		}
		return tracks;
	}
	
	public static LocalDatabase dbSetup(String name)
	{
		LocalDatabase local = new LocalDatabase(name);
		local.loadDriver();
		local.openConnection();
		local.createTablePersons();
		local.createTableListenings();
		local.createTableRecordings();
		local.closeConnection();
		return local;
	}

	
	public Collection<RS_Track> getTracks(String user)
	{
//		TODO
		String query =
			"SELECT artist_name, track_name FROM Recordings, Listenings "	+
			"WHERE Recordings.unique_track = Listenings.unique_track "		+
			"AND user = " + user + ";";
		return selectTracksFromRecordings(query);
	}

	public Collection<RS_User> getListeners(Track t)
	{
		//TODO
		String query =
			"SELECT user.lfm_username AS lfm_username, age, country, language, gender FROM Persons, Listenings, Recordings "	+
			"WHERE Persons.user = Listenings.user "		+
			"AND Recordings.unique_track = Listenings.unique_track "	+
			"AND artist_name = " + t.getArtist()	+
			"AND track_name = " + t.getName() + ";";
		return selectUsersFromPersons(query);
	}
	
}

	// 4 things you're busy doing...
	// writing test
	// writing prod code
	// refactoring test
	// refactoring prod code

	// should we have used object serialization? or is sql scalable / will it help?