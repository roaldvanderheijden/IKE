import java.util.*;

import de.umass.lastfm.*;

/**
 * Class which acts as a wrapper for the Last.FM-API
 * Used in getting certain information we want for our base Recommendation System
 * @author Robbert van Staveren
 * @date 29th November 2011
 */
public class RS_LastFMInterface {
	
	/**
	 * A developer's key from one of the authors (rh), necessary for certain requests to the Last.FM-api
	 */
	private String key = "d4a5cfedec3c607cc8c42123a632b5a5";
	
	/** Returns the <code>Track</code> object from Last.FM which belongs to this artist and has tname as track name
	 * @param artist the (performer) name of the artist we want the <code>Track</code> object for
	 * @param tname the title of the <code>Track</code> object we want.
	 * @require artist!=null, tname !=null
	 * @ensure <code>Track</code>t.getArtist().equals(artist)==TRUE &
	 * 			<code>Track</code>t.getTrackname().equals(tname)==TRUE;
	 * 
	 * @return the <code>Track</code> which has artist as performers name and tname as track title.
	 */
	public Track getTrack(String artist, String tname)
	{
		Collection<Track> terug = Track.search(artist, tname, 1, key);
		for(Track t : terug)
		{
			return Track.getInfo(t.getArtist(), t.getName(), key);
		}
		return null;
	} // with a more logical but different implementation than in model, GREAT! :D

	/**
	 * Returns the most popular tracks of an artist on Last.FM (based on playcount)
	 * 
	 * @param artist for which we want his/her/their most popular tracks
	 * @require artist!=null
	 * @ensure the most popular tracks of this artist
	 * 
	 * whatever most popular may be :D
	 */
	public Collection<Track> getTracks(String user) {
		return User.getTopTracks(user, key);
	}
	
	/**
	 * Get the top Listeners for a certain <code>Track</code>
	 * @param t the <code>Track</code> for which you like to check the top listeners
	 * @return the collection of users which are listeners for this <code>Track</code>
	 * @require t != null
	 * @ensure ??
	 */
	public Collection<User> getListeners(Track t)
	{
		return Track.getTopFans(t.getArtist(), t.getName(), key);
	}
	
	/**
	 * Method to check for 
	 * @param u the <code>User</code> whom we question for the <code>Tracks</code> he or she listens to.
	 * @return the collection of <code>Tracks</code> he or she listens to
	 * @require u != null 
	 * @ensure ??
	 */
	public Collection<Track> getTracks(User u)
	{
		return User.getTopTracks(u.getName(), key);
	}
}
