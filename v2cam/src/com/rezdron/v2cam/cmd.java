package com.rezdron.v2cam;

public class cmd {
	cmdTypes type;
	int value1;
	int value2;
	int line;

	public cmd() {
	}
	
	public cmd(cmdTypes myType, int myVal1, int myVal2, int myLine) {
		this.type = myType;
		this.value1 = myVal1;
		this.value2 = myVal2;
		this.line = myLine;
	}
	
	public String toString() {
		return type + " " + value1 + " " + value2 + " " + line;
	}
}
