import java.util.*;

//import de.umass.lastfm.Artist;
//import de.umass.lastfm.Track;
import de.umass.lastfm.User;

/**
 * @author	Robbert van Staveren
 * @since	2011-12-09
 */

public class RS_Algorithm_Improved implements RS_Algorithm {
	
	DB_Handler DB = new DB_Handler();
	RS_Comparators comparators = new RS_Comparators();
	
	public RS_Algorithm_Improved()
	{
	}
	
/**
 * @param	tracks	A collection of tracks where the recommendation is based on
 * @return	String	A String representation of the recommendated tracks
 */
	public String getRecommendations(Collection<RS_Track> tracks)
	{	
		String results = "";

		SortedArrayList<RS_User> topListeners = getTopListeners(tracks);
		
		

//		topListeners.setComparator(comparators.userRating);
//		topListeners.setComparator(new compareRating());

		List<RS_Track> rec = getTopTracks(topListeners, 500);
		
			
	//	voor het printen van de aanbevolen tracks
		results+= rec.size() + " tracks gevonden:\n";
		for (RS_Track track : rec)
		{
			results += track.getArtist() + ":\t" + track.getName() + "\t" + track.getRating() + "\n";
		}
		
		System.out.println();
		return results;
	}

/**
 * Gives back the top listeners based on the rating in RS_User
 * @param	tracks	A collection of tracks where the recommendation is based on
 * @param	max	The maximum amount of users the function gives back
 * @return	The top listeners based on the rating in RS_User
 */
	public List<RS_User> getTopListeners(Collection<RS_Track> tracks, int max)
	{
		SortedArrayList<RS_User> top = getTopListeners(tracks);
		if(max==0 || max>top.size())
			return top;
		else
		{
			top.setComparator(comparators.userRating);
			return top.subList(0, max);
		}
	}
	
/**
 * Gives back the top listeners based on the rating in RS_User
 * @param	tracks	A collection of tracks where the recommendation is based on
 * @return	The top listeners based on the rating in RS_User
 */
	public SortedArrayList<RS_User> getTopListeners(Collection<RS_Track> tracks)
	{
		SortedArrayList<RS_User> topListeners = new SortedArrayList<RS_User>();
		
		int dubbeleUsers=0;
		
	//	zoek van alle tracks de luisteraars en zet deze in de lijst users
		for (RS_Track t : tracks)
		{
			Collection<RS_User> tusers = DB.getListeners(t); //De luisteraars van track t
			for(RS_User u : tusers)
			{
				if(topListeners.add(u))
				{
					//u kon toegevoegd worden
					topListeners.get(topListeners.indexOf(u)).increaseRating();
				}
				else //u was al toegevoegd
				{
					dubbeleUsers++;
					topListeners.get(topListeners.indexOf(u)).increaseRating();
//					topListeners;
				}
			}
		}
		
		System.out.println("users: " + topListeners.size());
		System.out.println("dubbeleUsers: " + dubbeleUsers);
		
		return topListeners;
	}
	
	/**
	 * Gives back the top tracks based on the rating in RS_Track
	 * @param	topListeners	A collection of RS_User
	 * @param	max	The maximum amount of Tracks the function gives back
	 * @return	The top tracks based on the rating in RS_Track
	 */
	public List<RS_Track> getTopTracks(Collection<RS_User> topListeners, int max)
	{
		SortedArrayList<RS_Track> top = getTopTracks(topListeners);
		if(max==0 || max>top.size())
		{
			return top;
		}
		else
		{
			top.setComparator(comparators.trackRating);
			return top.subList(0, max);
		}
	}
	
	/**
	 * Gives back the top tracks based on the rating in RS_Track
	 * @param	topListeners	A collection of RS_User
	 * @return	The top tracks of topListeners based on the rating in RS_Track
	 */	
	public SortedArrayList<RS_Track> getTopTracks(Collection<RS_User> topListeners)
	{
		int dubbeleTracks=0;
		int fouteUsers=0;
		
		SortedArrayList<RS_Track> rec = new SortedArrayList<RS_Track>();
	//	vraag aan de luisteraars naar welke nummers ze luisteren
		for(RS_User u : topListeners)
		{
			try
			{
				Collection<RS_Track> currTracks = DB.getTracks(u);
				for(RS_Track track : currTracks)
				{
					if(rec.add(track))
					{
						//de track is toegevoegd
						rec.get(rec.indexOf(track)).increaseRating();
					}
					else
					{
						//de track staat al in de lijst
						rec.get(rec.indexOf(track)).increaseRating(u.getRating());
						dubbeleTracks++;
					}
				}
			}catch (Exception e) {
				fouteUsers++;
				}
		}
		System.out.println("fouteUsers: " + fouteUsers);
		System.out.println("Tracks " + rec.size());
		System.out.println("dubbeleTracks: " + dubbeleTracks);
		
		return rec;
	}
}
