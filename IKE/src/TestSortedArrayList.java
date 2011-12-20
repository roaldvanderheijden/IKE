import java.util.*;

/**
 * Class to test our generic <code>SortedArrayList</code>
 * @author Robbert van Staveren
 * @date 6th December 2011
 */
public class TestSortedArrayList {

	/**
	 * Creates a sorted list of <code>Strings</code>, adds some <code>Strings</code> from "A" to "L" in random order
	 * Checks if some of those can be added again (NO! :)), and then prints out the content of the <code>SortedArrayList<String></code>
	 */
	public static void main(String[] args) {
		SortedArrayList<String> sal = new SortedArrayList<String>();
		System.out.println(sal.add("E"));
		sal.print();
		System.out.println(sal.add("A"));
//		sal.print();
		System.out.println(sal.add("B"));
//		sal.print();
		System.out.println(sal.add("C"));
		System.out.println(sal.add("J"));
		System.out.println(sal.add("K"));
		System.out.println(sal.add("D"));
		System.out.println(sal.add("I"));
		System.out.println(sal.add("F"));
		System.out.println(sal.add("G"));
		System.out.println(sal.add("H"));
		System.out.println(sal.add("L"));
//		sal.print();
		System.out.println(sal.add("J"));
		System.out.println(sal.add("K"));
		System.out.println(sal.add("D"));
		System.out.println(sal.add("I"));
		System.out.println(sal.add("F"));
//		sal.print();
		for(char a = 'A' ; a<='L'; a++)
			System.out.println(a + " " + sal.binsearch(Character.toString(a)));
	}

}
