import java.util.*;

import de.umass.lastfm.*;

/**
 * Class which acts as a wrapper for the Last.FM-API
 * Used in getting certain information we want for our base Recommendation System
 * @author Robbert van Staveren
 * @date 29th November 2011
 */
public class DB_Handler {
	
	RS_LastFMInterface FMI = new RS_LastFMInterface();
	LocalDatabase DBI = new LocalDatabase("DSB");
	
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
		Collection<RS_Track> temp = DBI.selectTracksFromRecordings("SELECT * FROM Persons WHERE artist = \"" + artist + "\" AND naam = \"" + tname + "\";");
		for(Track t : temp)
			return t;
		return FMI.getTrack(artist, tname);
	}

	/**
	 * Returns the most popular tracks of an artist on Last.FM (based on playcount)
	 * 
	 * @param artist for which we want his/her/their most popular tracks
	 * @require artist!=null
	 * @ensure the most popular tracks of this artist
	 * 
	 * whatever most popular may be :D
	 */
	public Collection<RS_Track> getTracks(String user) {
		Collection<RS_Track> temp = DBI.getTracks(user);
		if(temp != null && (!temp.isEmpty() || temp.size()<10))
			return temp;
		return RS_Track.toRS_Track(FMI.getTracks(user));
	}
	
	/**
	 * Get the top Listeners for a certain <code>Track</code>
	 * @param t the <code>Track</code> for which you like to check the top listeners
	 * @return the collection of users which are listeners for this <code>Track</code>
	 * @require t != null
	 * @ensure ??
	 */
	public Collection<RS_User> getListeners(RS_Track t)
	{
		Collection<RS_User> temp = DBI.getListeners(t);
		if(temp != null && (!temp.isEmpty() || temp.size()<10))
			return temp;
		return RS_User.toRS_User(FMI.getListeners(t));
	}
	
	/**
	 * Method to check for 
	 * @param u the <code>User</code> whom we question for the <code>Tracks</code> he or she listens to.
	 * @return the collection of <code>Tracks</code> he or she listens to
	 * @require u != null 
	 * @ensure ??
	 */
	public Collection<RS_Track> getTracks(RS_User u)
	{
		return this.getTracks(u.getName());
	}
}
