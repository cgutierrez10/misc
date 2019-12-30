package com.rezdron.v2cam;

public class triplet {
	int x;
	int y;
	int z;
	triplet(int a, int b, int c) {
		x = a;
		y = b;
		z = c;
	}
	
	// This int created can be used for loose computations but should not be stored in the vertex list
	triplet(double a, double b, double c) {
		System.out.println("Warning, created int triplet from doubles");
		this.x = (int) a;
		this.y = (int) b;
		this.z = (int) c;
	}
	
	public String toString()
	{
		return "<" + this.x + "," + this.y + "," + this.z + ">";
	}
	
	public triplet add(triplet plus) {
		return new triplet(this.x + plus.x, this.y + plus.y, this.z + plus.z);
	}
	
	public triplet sub(triplet plus) {
		return this.add(plus.mult(-1));
	}
	
	public triplet mult(triplet plus) {
		return new triplet(this.x + plus.x, this.y + plus.y, this.z + plus.z);
	}
	
	public triplet mult(float scale) {
		return new triplet(this.x * scale, this.y * scale, this.z * scale);
	}
	
	public double norm() {
		// return normalized scalar
		return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}
	
	
    // Triplet compare, need to check <x,y,z> are equal between them all 
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof triplet)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        triplet t = (triplet) o; 
        return ((Integer.compare(t.x, x) == 0) && (Integer.compare(t.y, y) == 0) && (Integer.compare(t.z, z) == 0));
    } 
} 
