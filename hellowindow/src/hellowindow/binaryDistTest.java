/**
 * 
 */
package hellowindow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claire Gutierrez
 *
 */
class binaryDistTest {

	binaryDist testClass = new binaryDist(); 
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link hellowindow.calcCombinations#calcCombinations()}.
	 */
	@Test
	void testCalcCombinations() {
		assertEquals(1365, testClass.calcCombinations(4,15));
		assertEquals(5,testClass.calcCombinations(4, 5));
		assertEquals(10, testClass.calcCombinations(1,10));
		assertEquals(20, testClass.calcCombinations(1,20));
		assertEquals(25, testClass.calcCombinations(1,25));
		assertEquals(190, testClass.calcCombinations(2,20));
		assertEquals(1140, testClass.calcCombinations(3,20));
		assertEquals(9880,testClass.calcCombinations(3,40));
		/*try {
			testClass.calcCombinations(20, 2);
		} catch (ArithmeticException e)
		{
			assertEquals("Exception in nCk, k greater than n", e.getMessage());
			System.out.println(e.getMessage());
		}*/
		
	}
	
	/**
	 * Test method for {@link hellowindow.binaryDist#binaryDist()}.
	 */
	@Test
	void testBinaryDist() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hellowindow.binaryDist#calcBinaryDist(double, int, int)}.
	 */
	@Test
	void testCalcBinaryDist() {
		assertEquals(0.193, testClass.calcBinaryDist(0.5,7,12), 0.01);
		assertEquals(0.31, testClass.calcBinaryDist(0.65, 4, 5), 0.01);
		assertEquals(0.1851, testClass.calcBinaryDist(0.05,3,40), 0.001);
		assertEquals(0.315, testClass.calcBinaryDist(0.05,1,10), 0.001);
		assertEquals(0.401, testClass.calcBinaryDist(0.1666, 1, 6),0.002);
		assertEquals(0.234, testClass.calcBinaryDist(0.1666, 2, 7), 0.001);
	}

	/**
	 * Test method for {@link hellowindow.binaryDist#calcCI(double, double, int)}.
	 */
	@Test
	void testCalcCIDoubleDoubleInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hellowindow.binaryDist#processTable(java.util.ArrayList)}.
	 */
	@Test
	void testProcessTable() {
		ArrayList<Integer> samples = new ArrayList<Integer>();
		samples.add(1);
		samples.add(2);
		samples.add(0);
		samples.add(1);
		samples.add(2);
		samples.add(1);
		List<Double> results = testClass.processTable(samples);
		assertEquals(0.390, results.get(0), 0.001);
		assertEquals(0.234, results.get(1), 0.001);
		assertEquals(0.279, results.get(2), 0.001);
	}

	/**
	 * Test method for {@link hellowindow.binaryDist#calcCI()}.
	 */
	@Test
	void testCalcCI() {
		fail("Not yet implemented");
	}

}
