/*
+ * Equation for a binary distribution formula
 * To be compared against random sampling
 * to determine the amount of sampling needed to 
 * get a high accuracy equivalent from a sampling formula
 * 
 * May then evaluate against the standard deviations 
 * and confidence intervals
 * 
 */

// Unit tests
// 
// processtable 
// |>3 selections of 20 d6, one sided, perfect even, lumpy even

package hellowindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.math.BigInteger;


public class binaryDist {
	
	public binaryDist() {
		
	}
	
	int calcCombinations(int pass, int trials) {
		if (pass > trials) {
			throw new ArithmeticException("Exception in nCk, k greater than n");
		}
		BigInteger k = BigInteger.valueOf(1);
		BigInteger permutations = BigInteger.valueOf(1);
		// Permutations without order with replacement
		// P_(n+k-1, k)
		// Ends up being n!/r!(n-r)!, 
		for (int i = (trials); i > 0; i--) {
			
			if (i > (trials - pass)) 
			{
				permutations = permutations.multiply(BigInteger.valueOf(i));
			}			
			if (i <= pass)
			{
				k = k.multiply(BigInteger.valueOf(i));
			}
		}
		return permutations.intValue() / (k).intValue();
	}
	
	public double calcBinaryDist(double rate, int pass, int trials) {
		//double permutations = trials + pass - 1;
		double permutations = this.calcCombinations(pass, trials);
		double sVal = Math.pow(rate,pass);
		double fVal =  (Math.pow((1-rate),trials-pass));
		double probability = permutations * sVal * fVal;
		return probability;
	}

	public double calcCI(double rate, double prob, int trials) {
		return 0.0;
	}
	
	// Take in an array and calcbin distribution across
	// all its values
	// Pass each value in turn forward to a CI formula for samples
	/**
	 * processTable
	 * 
	 * Process a table of values to determine individual probabilities of each item
	 * @param samples Integer array of n length where n is max samples and each element is successes of respective element
	 * @return List<Double> of probability of exact occurrences of original successes of that element
	 */
	public List<Double> processTable(ArrayList<Integer> samples) {
		Integer trials = 0;
		double rate = (1.0 / (samples.size()));
		ArrayList<Double> probSample = new ArrayList<Double>();
		
		//Iterator sampleItr = samples.iterator();
		int length = samples.size();
		for (int i = 0; i < length; i++)
		{
			// Not sure about scoping here, this could potentially sum in here then
			// exit scope and revert back to 0 for reference below
			trials = trials + (Integer) samples.get(i);
		}
		
		// Probability of each occurrence individually among the trials
		// Next step will determine the occurrence rate as a grouping.
		for (int i = 0; i < length; i++)
		{
			probSample.add(calcBinaryDist(rate, (int) samples.get(i), trials));
		}
		
		// Determine confidence interval of each element
		/*
		sampleItr = probSample.iterator();
		while (sampleItr.hasNext())
		{
			calcCI(rate,(double) sampleItr.next(), trials);
		}*/
		
		// Try to pair up sets (opposite faces of dice) and check for abnormalities in the pair
		// I.e., two faces on d20 return more than 10% with high c-i.
		return probSample;
		
	}
	
	public double calcCI() {
		/* For 'fair' dice standard dev and mean are 
		 * pre-computable
		 * as (elements + 1) / 2
		 * stdev is 
		 * sqrt(2*(sum of (mean - y)^2)/elements)
		 * for y=1; y < mean; y--;
		 *  
		 */
		/*
		 * CI (Zvalue)
		 * 0.8 1.282
		 * 0.85 1.44
		 * 0.9 1.645
		 * .095 1.960
		 * 0.99 2.576
		 * 99.5 2.807
		 * 99.9 3.291
		 * 
		 * Standard dev is sqrt(sum((mean - sample(z))^2))
		 * For average difference from average
		 * Formula is
		 * mean +/- Z(stdev/sqrt(n))
		 */
		return 0.0;
	}
}
