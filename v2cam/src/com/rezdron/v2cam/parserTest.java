package com.rezdron.v2cam;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class parserTest {

	parser testObj = new parser("");
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testParser() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCmds() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateHeader() {
		//Verify all settings are required, pass in cmd sets excluding each one individually then several missing
		// Also pass in full sets of settings that contain some invalid data
		// Finally test with a full set of valid data.
		
		//Requires linkecmdst of commands
		LinkedList<cmd> validCmds = new LinkedList<cmd>();
		LinkedList<cmd> invalidCmds = new LinkedList<cmd>();
		LinkedList<cmd> testCmds = new LinkedList<cmd>();
		validCmds.add(new cmd(cmdTypes.S,  0, 1,  0));
		validCmds.add(new cmd(cmdTypes.S,  1, 1,  1));
		validCmds.add(new cmd(cmdTypes.S,  2, 1,  2));
		validCmds.add(new cmd(cmdTypes.S,  3, 1,  3));
		validCmds.add(new cmd(cmdTypes.S,  4, 1,  4));
		validCmds.add(new cmd(cmdTypes.S,  5, 1,  5));
		validCmds.add(new cmd(cmdTypes.S,  6, 1,  6));
		validCmds.add(new cmd(cmdTypes.S,  7, 1,  7));
		validCmds.add(new cmd(cmdTypes.S,  8, 1,  8));
		validCmds.add(new cmd(cmdTypes.S,  9, 1,  9));
		validCmds.add(new cmd(cmdTypes.S, 10, 1, 10));
		validCmds.add(new cmd(cmdTypes.S, 11, 1, 11));
		validCmds.add(new cmd(cmdTypes.S, 12, 1, 12));
		validCmds.add(new cmd(cmdTypes.S, 13, 1, 13));
		validCmds.add(new cmd(cmdTypes.S, 14, 1, 14));
		validCmds.add(new cmd(cmdTypes.S, 15, 1, 15));
		
		invalidCmds.add(new cmd(cmdTypes.S,  -1, 1,  1));
		invalidCmds.add(new cmd(cmdTypes.S,  -2, 1,  2));
		invalidCmds.add(new cmd(cmdTypes.S,  -3, 1,  3));
		invalidCmds.add(new cmd(cmdTypes.S,  -4, 1,  4));
		invalidCmds.add(new cmd(cmdTypes.S,  -5, 1,  5));
		invalidCmds.add(new cmd(cmdTypes.S,  -6, 1,  6));
		invalidCmds.add(new cmd(cmdTypes.S,  -7, 1,  7));
		invalidCmds.add(new cmd(cmdTypes.S,  -8, 1,  8));
		invalidCmds.add(new cmd(cmdTypes.S,  -9, 1,  9));
		invalidCmds.add(new cmd(cmdTypes.S, -10, 1, 10));
		invalidCmds.add(new cmd(cmdTypes.S, -11, 1, 11));
		invalidCmds.add(new cmd(cmdTypes.S, -11, 1, 12));
		invalidCmds.add(new cmd(cmdTypes.S, -12, 1, 13));
		invalidCmds.add(new cmd(cmdTypes.S, -13, 1, 14));
		invalidCmds.add(new cmd(cmdTypes.S, -14, 1, 15));
		invalidCmds.add(new cmd(cmdTypes.S, -15, 1, 16));
		assertTrue(testObj.ValidateHeader(validCmds), "Failed");
		
		// Additional test cases discovered while writing this, accidentally resetting a value and excluding value S0.
		//fail("Not yet implemented");
	}

	@Test
	void testValidateCmds() {
		//Verify all settings are required, pass in cmd sets excluding each one individually then several missing
				// Also pass in full sets of settings that contain some invalid data
				// Finally test with a full set of valid data.
				
				//Requires linkecmdst of commands
				LinkedList<cmd> validCmds = new LinkedList<cmd>();
				LinkedList<cmd> invalidCmds = new LinkedList<cmd>();
				LinkedList<cmd> testCmds = new LinkedList<cmd>();
				validCmds.add(new cmd(cmdTypes.S,  0, 1,  0));
				validCmds.add(new cmd(cmdTypes.S,  1, 1,  1));
				validCmds.add(new cmd(cmdTypes.S,  2, 1,  2));
				validCmds.add(new cmd(cmdTypes.S,  3, 1,  3));
				validCmds.add(new cmd(cmdTypes.S,  4, 1,  4));
				validCmds.add(new cmd(cmdTypes.S,  5, 1,  5));
				validCmds.add(new cmd(cmdTypes.S,  6, 1,  6));
				validCmds.add(new cmd(cmdTypes.S,  7, 1,  7));
				validCmds.add(new cmd(cmdTypes.S,  8, 1,  8));
				validCmds.add(new cmd(cmdTypes.S,  9, 1,  9));
				validCmds.add(new cmd(cmdTypes.S, 10, 1, 10));
				validCmds.add(new cmd(cmdTypes.S, 11, 1, 11));
				validCmds.add(new cmd(cmdTypes.S, 12, 1, 12));
				validCmds.add(new cmd(cmdTypes.S, 13, 1, 13));
				validCmds.add(new cmd(cmdTypes.S, 14, 1, 14));
				validCmds.add(new cmd(cmdTypes.S, 15, 1, 15));
				
				/*
				invalidCmds.add(new cmd(cmdTypes.S,  -1, 1,  1));
				invalidCmds.add(new cmd(cmdTypes.S,  -2, 1,  2));
				invalidCmds.add(new cmd(cmdTypes.S,  -3, 1,  3));
				invalidCmds.add(new cmd(cmdTypes.S,  -4, 1,  4));
				invalidCmds.add(new cmd(cmdTypes.S,  -5, 1,  5));
				invalidCmds.add(new cmd(cmdTypes.S,  -6, 1,  6));
				invalidCmds.add(new cmd(cmdTypes.S,  -7, 1,  7));
				invalidCmds.add(new cmd(cmdTypes.S,  -8, 1,  8));
				invalidCmds.add(new cmd(cmdTypes.S,  -9, 1,  9));
				invalidCmds.add(new cmd(cmdTypes.S, -10, 1, 10));
				invalidCmds.add(new cmd(cmdTypes.S, -11, 1, 11));
				invalidCmds.add(new cmd(cmdTypes.S, -11, 1, 12));
				invalidCmds.add(new cmd(cmdTypes.S, -12, 1, 13));
				invalidCmds.add(new cmd(cmdTypes.S, -13, 1, 14));
				invalidCmds.add(new cmd(cmdTypes.S, -14, 1, 15));
				invalidCmds.add(new cmd(cmdTypes.S, -15, 1, 16));
				*/
				
				// Validate functions are not checking for line numbers
				// Maybe that should be changed.
				validCmds.add(new cmd(cmdTypes.V, 0,0 , 15));
				validCmds.add(new cmd(cmdTypes.V, -1, 0, 15));
				validCmds.add(new cmd(cmdTypes.V, -1, -1, 15));
				validCmds.add(new cmd(cmdTypes.V, 0, -1, 15));
				
				assertTrue(testObj.ValidateCmds(validCmds), "Failed");
	}

	@Test
	void testReadFileTokens() {
		fail("Not yet implemented");
	}

}
