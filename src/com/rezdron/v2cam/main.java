package com.rezdron.v2cam;

import java.util.LinkedList;

public class main {
// Where are my unit tests? 
// Going to write unit tests before moving on to actual run and debug, will reduce debug tracing later
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		v2cam_core v2cam = new v2cam_core();
		System.out.print(v2cam.runFile("testFile.vtx"));
		
		//triplet a = new triplet(1,1,1);
		//triplet b = new triplet(1,1,1);
		//System.out.println(a.equals(b));
		//System.out.println(a == b);
		//LinkedList<triplet> test = new LinkedList<triplet>();
		//test.add(a);
		//System.out.println(test.contains(a));
		//System.out.println(test.contains(b));;
	}

}
