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
		assertEquals(0, sal.size());
	}
	
	@Test
	public void haveSizeOneAfterAddingOneElement() {
		sal.add("A");
		assertEquals(1, sal.size());
	}
	
	@Test
	public void haveSizeTwoAfterAddingTwoUniqueElements() {
		sal.add("B");
		sal.add("C");
		assertEquals(2, sal.size());
	}
	
	@Test
	public void beEmptyAfterClearing() {
		sal.add("1");
		sal.add("2");
		//System.out.println("Inserting testdata into sortedArrayList\n");
		//	sal.print();
		//System.out.println("Clearing sortedArrayList\n");
			sal.clear();
		//System.out.println("Array is empty: "+ sal.isEmpty());
		assertEquals(true, sal.isEmpty());
	}
	
	@Test
	public void beAbleToAddUniqueElements() {
		sal.add("A");
		sal.add("AA");
		sal.add("AAA");
		sal.add("B");
		sal.add("ABBA");
		assertEquals(5, sal.size());
	}
	
	@Test
	public void denyAddingLikeObjects() {
		sal.add("X");
		sal.add("Y");
		assertEquals(false, sal.add("Y"));
	}
	
	@Test
	public void haveAContainsThatSaysTrueIfAnElementIsContained() {
		sal.add("three");
		assertEquals(true, sal.contains("three"));
	}
	
	@Test
	public void haveAContainsThatSaysFalseIfAnElementIsNotContained() {
		sal.add("four");
		assertEquals(false, sal.contains("five"));
	}
	
	@Test
	public void LetIndexOfReturnMinus1IfAnElementIsNotInIt() {
		sal.add("4");
		assertEquals(-1, sal.indexOf("5"));
	}
	
	@Test
	public void LetIndexOfReturnTheRightIndexOfAnElementThatItContains() {
		sal.add("6");
		sal.add("1934");
		sal.add("231");
		//sal.print();
		assertEquals(0, sal.indexOf("1934"));
		//assertEquals(2, sal.indexOf("6"));
		//assertEquals(1, sal.indexOf("231"));
	}
	
	@Test
	public void LetAddDoItsJob() {
		int check = sal.size();
		sal.add("fine");
		assertEquals(check+1, sal.size());
	}
	
	@Test
	public void haveBinSearchReturnTheCorrectIndexOfAContainedElement() {
		sal.add("9");
		sal.add("99");
		sal.add("988");
		assertEquals(2, sal.binsearch("99"));
	}
	
	@Test
	public void haveBinSearchReturnIndexToPlaceWhenNotContainsAnElement() {
		sal.add("twentyone");
		sal.add("twentythree");
		assertEquals(1, sal.binsearch("twentyso"));
	}
	
//	@Test
//	public void printContentsOfASortedArrayList() {
//		sal.add("101");
//		sal.add("102");
//		sal.add("103");
//		sal.add("104");
//		assertEquals("101\n102\n103\n104\n", sal.print());
//	}
	// print moet nog
	// setComparator en test + inner class
}
