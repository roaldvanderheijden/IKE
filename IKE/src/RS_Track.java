import de.umass.lastfm.Track;

import java.util.*;

/**
 * RS_Track extends last.fm Track class
 * Its the same as track but we added the int rating and implemented some comparable functions, 
 * so we can rate it and give more accurate recommendations
 * and we can sort the Tracks on rating or artist-name
 * */
public class RS_Track extends Track implements Comparable<RS_Track>{

	private int rating;
	
	protected RS_Track(String name, String url, String artist, int rate) {
		super(name, url, artist);
		rating = rate;
	}
	
	protected RS_Track(Track t)
	{
		super(t.getName(), t.getUrl(), t.getArtist());
		rating = 0;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void increaseRating()
	{
		rating++;
	}
	
	public void increaseRating(int i)
	{
		rating+=i;
	}
	
	public int compareRatingTo(RS_Track that)
	{
		return this.rating-that.getRating();
	}
	
	public int compareTo(RS_Track that)
	{
		if(artist.equals(that.getArtist()))
			return name.compareTo(that.getName());
		else
			return artist.compareTo(that.getArtist());
	}
	
	public boolean equals(RS_Track that)
	{
		return artist.equals(that.getArtist()) && name.equals(that.getName());
	}

	public static Collection<RS_Track> toRS_Track(Collection<Track> that)
	{
		Collection<RS_Track> terug = new ArrayList<RS_Track>();
		for(Track u : that)
			terug.add(RS_Track.toRS_Track(u));
		return terug;
	}
	
	public static RS_Track toRS_Track(Track that)
	{
		return new RS_Track(that);
	}
	
	public class compareRating implements Comparator<RS_Track>
	{
		public int compare(RS_Track a, RS_Track b)
		{
			return a.getRating()- b.getRating();
		}
	}
}