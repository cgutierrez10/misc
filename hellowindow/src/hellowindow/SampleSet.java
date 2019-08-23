package hellowindow;

import java.util.Arrays;

public class SampleSet {
/**
 * SampleSet object
 * 
 */
	/**
	 * Methods to include
	 * calc stdev, implimented
	 * calc mean, implimented
	 * calc variance, implimented
	 * min
	 * max
	 * @param values
	 */
	private Double STDev = null;
	private Double Mean = null;
	private Double Var = null;
	private Integer Minimum = null;
	private Integer Maximum = null;
	private Integer[] Samples = null;
	private Integer Sum = null;
	
	public SampleSet(Integer... values) {
		this.Samples = values;
	}
	
	@Override
	public String toString()
	{
		return "Statistics calculation object";
	}
	
	public double getMean() {
		if (this.Mean==null) {
			calcMean();
		}
		return this.Mean;
	}
	
	/**
	 * Implimented
	 * @return
	 */
	public double getSTDev() {
		if (this.STDev == null) {
			if (this.Var == null) {
				//this.getVar();
				
				this.Var = 0.0;
				for (int i = 0; i < this.Samples.length; i++) {
					//System.out.print(this.Var+":");
					//System.out.print(this.getMean()+":");
					//System.out.print(this.Samples[i]+"\n");
					this.Var += Math.pow((double) this.Samples[i] - this.getMean(),2);
				}
				this.Var = this.Var / (this.Samples.length - 1);
				//System.out.print(this.Var+"\n");
				
			}
			this.STDev = Math.sqrt(this.Var);
		}
		return this.STDev;
	}
	public double getVar() {
		if (this.Var == null) {
			this.Var = 0.0;
			for (int i = 0; i < this.Samples.length; i++) {
			this.Var += Math.pow((double) Samples[i] - this.getMean(),2);
			System.out.print(this.Var+"\n");
			}
			this.Var = this.Var / (this.Samples.length - 1);
		}
		return this.Var;
	}

	// These two are a pair, calc one may as well find both
	public Integer getMin() {
		if (this.Minimum==null)
		{
			calcMinMax();
		}
		return this.Minimum;
	}
	
	public Integer getMax() {
		if (this.Maximum==null)
		{
			calcMinMax();
		}
		return this.Maximum;
	}
	
	private void calcMinMax() {
		for (int i = 0; i < this.Samples.length; i++) {
			if (i == 0) {
				this.Minimum = Samples[0];
				this.Maximum = Samples[0];
			}
			if (this.Maximum < Samples[i]) { this.Maximum = Samples[i]; }
			if (this.Minimum > Samples[i]) { this.Minimum = Samples[i]; }
		}
		return;
	}
	
	public Integer getSum()
	{
		if (this.Sum==null) {
			calcSum();
		}
		return this.Sum;
	}
	
	private void calcSum() {
		this.Sum = 0;
		for (int i = 0; i < Samples.length; i++) {
			this.Sum += Samples[i];
		}
		
	}
	private void calcMean() {
		if (this.Sum == null) {
			calcSum();
		}
		this.Mean = this.Sum / ((double) this.Samples.length);
	}
}
