import java.util.Collection;

//import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;
//import de.umass.lastfm.User;
import java.util.*; // voor Iterator, Observable & Observer

/**
* Class to represent the model used in the Recommender System application
* 
* At this point in time (5th dec 2011) Model consists only of:
* Parts to run algorithm and get results back from Last.FM-queries
* 
* @author Roald van der Heijden, Olaf Maas & Martijn Verhoef
* @date 18th October 2011
*/
public class RS_Model extends Observable {
		
	// instance variables
	// /**
	// * The results from a query to Last.fm
	// */
	//protected String results;
	
	// never used so commented out before deletion.
	/**
	 * Interface used to handle connections to Last.FM-api in our application
	 */
	private DB_Handler DBI = new DB_Handler();
	
	/**
	 * The class used to represent our recommendation algorithm, used in getting recommendations from our Last.FM Handler (RS_DBInterface)
	 */
	private RS_Algorithm algorithm = new RS_Algorithm_Improved();
	
	// @@@ Why the heck do we have getRecommendations from RS_Algorithm_Simple duplicated here
	// @@@ Same applies for GetTrack() from RS_DBInterface ???
	
	/**
	 * Calculates the recommendations and returns them to the caller of this method
	 * 
	 * @param tracks the <code>Collection</code> of tracks on which we base our recommendations
	 * @require tracks != null
	 * @ensure -> NO IDEA what to fill in here :)
	 * @return the list of recommendations returned as one big <code>String</code> object
	 */
	public String getRecommendations(Collection<Track> tracks)
	{	
		return algorithm.getRecommendations(RS_Track.toRS_Track(tracks));
	}
	
	/**
	 * Returns the <code>Track</code> object from Last.FM which belongs to this artist and has tname as track name
	 * @param artist the (performer) name of the artist we want the <code>Track</code> object for
	 * @param tname the title of the <code>Track</code> object we want.
	 * @require artist!=null, tname !=null
	 * @ensure <code>Track</code>t.getArtist().equals(artist)==TRUE &
	 * 			<code>Track</code>t.getTrackname().equals(tname)==TRUE;
	 * 
	 * @return the <code>Track</code> which has artist as performers name and tname as track title.
	 */
	// methods in @ensure allowed? No idea!
	public Track getTrack(String artist, String tname)
	{
		return DBI.getTrack(artist, tname);
	}
}	