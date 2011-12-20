import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ASortedArrayListShould {
	/**
	 * the <code>SortedArrayList<String></code> we want to test
	 */
	private SortedArrayList<String> sal;
	
	@Before
	public void init() {
		sal = new SortedArrayList<String>();
	}
	
	@Test
	public void beEmptyJustAfterCreation() {
		assertEquals(sal.size(),0);
	}
	
	@Test
	public void haveSizeOneAfterAddingOneElement() {
		sal.add("A");
		assertEquals(sal.size(), 1);
	}
	
	@Test
	public void haveSizeTwoAfterAddingTwoUniqueElements() {
		sal.add("B");
		sal.add("C");
		assertEquals(sal.size(),2);
	}
	
	@Test
	public void beEmptyAfterClearing() {
		sal.add("1");
		sal.add("2");
		System.out.println("Inserting testdata into sortedArrayList\n");
			sal.print();
		System.out.println("Clearing sortedArrayList\n");
			sal.clear();
		System.out.println("Array is empty: "+ sal.isEmpty());
		assertEquals(sal.isEmpty(), true);
	}
	
	@Test
	public void beAbleToAddUniqueElements() {
		sal.add("A");
		sal.add("AA");
		sal.add("AAA");
		sal.add("B");
		sal.add("ABBA");
		assertEquals(sal.size(), 5);
	}
	
	@Test
	public void denyAddingLikeObjects() {
		sal.add("X");
		sal.add("Y");
		assertEquals(sal.add("Y"), false);
	}
	
	@Test
	public void haveAContainsThatWorks
}
