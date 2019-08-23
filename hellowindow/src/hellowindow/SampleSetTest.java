package hellowindow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SampleSetTest {
	SampleSet testObj[];
	
	@BeforeEach
	void setUp() throws Exception {
		this.testObj =  new SampleSet[] {new SampleSet(1,2,3,4,5,6), new SampleSet(1,2,3,4,5,6,7,8,9,10), new SampleSet(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20), new SampleSet(1,2,3,4,5,6,7,8) };
		if (testObj == null) { fail("Object did not initialize\n"); return; }
	}
	
	void refreshObjs() {
		this.testObj =  new SampleSet[] {new SampleSet(1,2,3,4,5,6), new SampleSet(1,2,3,4,5,6,7,8,9,10), new SampleSet(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20), new SampleSet(1,2,3,4,5,6,7,8) };
	}

	@Test
	void testSampleSet() {
		assertEquals(true, testObj[0] != null, "Object failed to initialize");
		assertEquals(4, testObj.length, "Object array ne 4 elements");
	}

	@Test
	void testGetMean() {
		refreshObjs();
		assertEquals(3.5,  testObj[0].getMean(),"Mean miscalculated");
		assertEquals(5.5,  testObj[1].getMean(),"Mean miscalculated");
		assertEquals(10.5, testObj[2].getMean(),"Mean miscalculated");
		assertEquals(4.5,  testObj[3].getMean(),"Mean miscalculated");
	}

	@Test
	void testGetVar() {
		refreshObjs();
		assertEquals(3.5,  testObj[0].getVar(),"Variance miscalculated, 1-6");
		assertEquals(3.5,  testObj[0].getVar(),"Variance mis-stored, 1-6");
		assertEquals(9.16666, testObj[1].getVar(),0.001, "Variance miscalculated");
		/*
		2.5*2.5 (1 - 3.5)
		1.5*1.5 (2 - 3.5)
		0.5*0.5 (3 - 3.5)
		0.5*0.5 (4 - 3.5)
		1.5*1.5 (5 - 3.5)
		2.5*2.5 (6 - 3.5)
		17.5
		*/
		//fail("Not yet implemented");
		// Test including calc standard deviation and check the var value
	}

	@Test
	void testGetStDev() {
		assertEquals(1.87, testObj[0].getSTDev(), 0.001, "Incorrect stdev, 1-6");
		assertEquals(3.02675, testObj[1].getSTDev(), 0.001, "Incorrect stdev, 1-10");
	}

	@Test
	void testGetMin() {
		assertEquals((Integer) 1, testObj[1].getMin(), "Invalid min, 1-6");
		assertEquals((Integer) 1, testObj[3].getMin(), "Invalid min, 1-8");
		testObj[0].getMax(); // Force calculate opposite and retrieve
		assertEquals((Integer) 1, testObj[0].getMin(), "Invalid min after calc max first, 1-10");
		testObj[2].getMin(); // Force calculate same and retrieve
		assertEquals((Integer) 1, testObj[2].getMin(), "Invalid min after calc min first, 1-20");
	}

	@Test
	void testGetMax() {
		assertEquals((Integer) 6, testObj[0].getMax(), "Invalid max, 1-6");
		assertEquals((Integer) 20, testObj[2].getMax(), "Invalid max, 1-20");
		testObj[1].getMin(); // Force calculate opposite and retrieve
		assertEquals((Integer) 10, testObj[1].getMax(), "Invalid max after calc min first, 1-10");
		testObj[3].getMax(); // Force calc same and retrieve
		assertEquals((Integer) 8, testObj[3].getMax(), "Invalid max after precalc max, 1-8");
	}

	@Test
	void testGetSum() {
		refreshObjs();
		assertEquals((Integer) 21 , testObj[0].getSum(), "Sum miscalculation, 1-6");
		assertEquals((Integer) 55 , testObj[1].getSum(), "Sum miscalculation, 1-10");
		assertEquals((Integer) 210, testObj[2].getSum(), "Sum miscalculation, 1-20");
		assertEquals((Integer) 36 , testObj[3].getSum(), "Sum miscalculation, 1-8");
	}

	@Test
	void testToString() {
		assertEquals((String) "Statistics calculation object", testObj[0].toString());
	}
	@Test
	/**
	 * Need one test to check the order of initializations and ensure any arrangement of calls calculates each dependent test correctly
	 * Orders to try
	 */
	void testCalcnCache() {
		
	}
}
