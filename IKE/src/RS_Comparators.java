import java.util.Comparator;


public class RS_Comparators {

//	Comparator<RS_User> userName = new RS_UserName();
	Comparator<RS_User> userRating = new RS_UserRating();
//	Comparator<RS_Track> trackName = new RS_TrackName();
	Comparator<RS_Track> trackRating = new RS_TrackRating();

/*	public final class RS_UserName implements Comparator<RS_User>
	{
		public int compare(RS_User a, RS_User b)
		{
			return a.compareTo(b);//b.getRating()- a.getRating();
		}
		
		public boolean equals(RS_User a, RS_User b)
		{
			return a.equals(b);
		}
	}*/
	
	public final class RS_UserRating implements Comparator<RS_User>
	{
		public int compare(RS_User a, RS_User b)
		{
			return b.getRating()- a.getRating();
		}
	}
	
/*	public final class RS_TrackName implements Comparator<RS_Track>
	{
		public int compare(RS_Track a, RS_Track b)
		{
			return a.compareTo(b);
		}
	}*/
	
	public final class RS_TrackRating implements Comparator<RS_Track>
	{
		public int compare(RS_Track a, RS_Track b)
		{
			return b.getRating()- a.getRating();
		}
	}

//	@Override
//	public int compare(Object o1, Object o2) {
//		// TODO Auto-generated method stub
//		return this.compare((RS_User)o1, (RS_User)o2);
//	}
}
