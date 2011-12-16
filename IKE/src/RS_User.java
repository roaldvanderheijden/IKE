import java.util.*;

import de.umass.lastfm.User;
/**RS_User extends last.fm User class
 * Its the same as User but we added the int rating and implemented some comparable functions,
 * */
public class RS_User implements Comparable<RS_User>{

	private int rating;
	User user;
	//added for queries in db
	private String username;
	private int age;
	private String country;
	private String language;
	private String gender;
	//added for queries in db
	protected RS_User(String username, int age, String country, String language, String gender) {
		this.username = username;
		this.age = age;
		this.country = country;
		this.language = language;
		this.gender = gender;
	} 
	//TODO gets/sets moeten nog
	
	protected RS_User(User u) {
		rating = 0;
		user = u;
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
	
	public int compareRatingTo(RS_User that)
	{
		return this.rating-that.getRating();
	}
	
	public int compareTo(RS_User that)
	{
		return getName().compareTo(that.getName());
	}
	
	public boolean equals(RS_User that)
	{
		System.out.println("B: " + this.getName() + " " + that.getName());
		return getName().equals(that.getName());
	}

	public final class compareRating implements Comparator<RS_User>
	{
		
		public int compare(RS_User a, RS_User b)
		{
			return a.getRating()- b.getRating();
		}
	}
	
	public static Collection<RS_User> toRS_User(Collection<User> that)
	{
		Collection<RS_User> terug = new ArrayList<RS_User>();
		for(User u : that)
			terug.add(RS_User.toRS_User(u));
		return terug;
	}
	
	public static RS_User toRS_User(User that)
	{
		return new RS_User(that);
	}
	
	public String getName()
	{
		return user.getName();
	}
}
