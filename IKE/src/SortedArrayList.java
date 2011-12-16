import java.util.*;


public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E>{
	
//	ArrayList<E> super;
	Comparator<E> c;
	
	public SortedArrayList()
	{
		super();
		c = new DefaultComparator<E>();
//		lijst = new ArrayList<E>();
	}

//	voor een andere comparator
	public SortedArrayList(Comparator<E> compare)
	{
		super();
		c = compare;
	}
	
	public boolean contains(E that)
	{
//		controleerd of that al in lijst zit
		return indexOf(that)>=0;
	}
	
	public int indexOf(E that)
	{
//		geeft de index van that terug of -1 als that niet in lijst zit
		int temp = this.binsearch(that);
		if(super.size()>0 && temp<super.size() && c.compare(super.get(temp), that)==0)//super.get(temp).equals(that))
			return temp;
		else
		{
			return -1;
		}
	}
	
	public boolean add(E that)
	{
		if(super.size()>0)
		{
			int plaats = this.binsearch(that);
//			System.out.println(that + " " + plaats);
			if(plaats==super.size())
			{
				super.add(that);
				return true;
			}
			if(!super.get(plaats).equals(that))
			{
				super.add(plaats, that);
				return true;
			}
//			else
			return false;
		}
		else
		{
			super.add(that);
			return true;
		}
	}
	
	public int binsearch(E that)
	{
//		geeft de plek terug van that als that in lijst zit en anders de plek waar hij ingevoegd zou moeten worden
		if(size()>0)
		{
			int min =0;
			int max =super.size();
			int midden=(min+max)/2;
			while(max-min>1 && !super.get(midden).equals(that))
			{
				if(c.compare(super.get(midden),that)<=0)//super.get(midden).compareTo(that)<=0)
	//			if(((Comparable)lijst.get(midden)).compareTo(that)<=0)
					min=midden;
				else
					max=midden;
				midden = (min+max)/2;
	//			System.out.println(min + "\t" + midden + "\t" + max);
			}
	//		System.out.println("min: " + min + "\tmidden: " + midden + "\tmax: " + max);
	//		if(lijst.get(midden).equals(that))
	//			return midden;
			if(c.compare(super.get(midden), that)<0)//super.get(midden).compareTo(that)<0)
	//		if(((Comparable)lijst.get(midden)).compareTo(that)<0)
				return midden+1;
			else
				return midden;
		}
		else
			return 0;
	}
	
	public void print()
	{
		for(E element : this)
			System.out.println(element.toString());
	}
	
	public E get(int i)
	{
		return super.get(i);
	}
	
	public void setComparator(Comparator<E> compare)
	{
		c = compare;
		Collections.sort(this, c);
	}

    private class DefaultComparator<C extends Comparable<E>> implements Comparator<E>
    {
        public int compare(E a, E b)
        {
            return a.compareTo(b);
        }
    }
	
//	public int size()
//	{
//		return lijst.size();
//	}
}
