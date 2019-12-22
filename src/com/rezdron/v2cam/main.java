package com.rezdron.v2cam;

public class main {
// Where are my unit tests? 
// Going to write unit tests before moving on to actual run and debug, will reduce debug tracing later
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		v2cam_core v2cam = new v2cam_core();
		System.out.print(v2cam.runFile("testFile.vtx"));
	}

}
