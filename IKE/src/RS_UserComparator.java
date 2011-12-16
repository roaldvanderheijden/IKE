import java.util.Comparator;


public final class RS_UserComparator implements Comparator<RS_User> {

	public int compare(RS_User a, RS_User b)
	{
		return a.getRating()- b.getRating();
	}

//	@Override
//	public int compare(Object o1, Object o2) {
//		// TODO Auto-generated method stub
//		return this.compare((RS_User)o1, (RS_User)o2);
//	}
}
