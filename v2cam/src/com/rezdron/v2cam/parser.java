package com.rezdron.v2cam;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;


// Hardcoded to take all units in mm as thousandth mm and truncate decimal values longer than that for comparison safety
public class parser {

	LinkedList<cmd> cmds;
	parser(String filename) {
		this.cmds = this.ReadFileTokens(filename);
	}
	
	public LinkedList<cmd> getCmds() { return this.cmds; }
	
	// Validate stream
	// On fail should output appropriate errors then return bool to block further processing
	// Initially accept only one set for each, global to the machine process
	// Later accept re-set values midway into project
	protected boolean ValidateHeader(LinkedList<cmd> commands) {
		// Init to a blank value
		Boolean[] required = new Boolean[16];
		Arrays.fill(required, false);
		cmd cmdItem;
		Iterator<cmd> i = commands.iterator();
		while (i.hasNext()) {
			cmdItem = i.next();
			if (cmdItem.type == cmdTypes.S)
			{
				if (required[((int) cmdItem.value1/1000)]) {
					System.out.printf("Invalid setting, resetting a value at line %d\n", cmdItem.line);
					return false;
				} 
				if (cmdItem.value1/1000 >= 16) {
					System.out.printf("Invalid setting, no settings beyond 16 permitted at line %d\nvalue: %d\n", cmdItem.line, cmdItem.value1);
					return false;
				}
				try { required[cmdItem.value1/1000] = true; } 
				catch (Exception e) { 
					System.out.println(e.getMessage());
					System.out.printf("At command from line %d\nValue1: %d\nValue2: %d\n",  cmdItem.line, cmdItem.value1, cmdItem.value2); 
				}
			}
		}
		
		int j = 0;
		for(boolean b : required) {
			j++;
			if(!b) {System.out.printf("Invalid settings, not all values populated missing item %d\n", j); return false;}
		}
		return true;
	}
	
	// All commands must have 2 values following, two values must be positive doubles, can flip coords at output if desired
	protected boolean ValidateCmds(LinkedList<cmd> commands) {
		cmd cmdItem;
		Iterator<cmd> i = commands.iterator();
		while (i.hasNext()) {
			cmdItem = i.next();
			//System.out.println(cmdItem.toString());
			
			// This should only be validating +/- on settings not on pathinh
			//cmdItem.type is 'validated' at read as assignment to enum expected to fail for an invalid type
			//if (cmdItem.value1 < 0 || cmdItem.value2 < 0) {
			//	System.out.printf("Invalid command, coordinate or values at line %d", cmdItem.line);
			//	return false;
			//}
		}
		return true;
	}
	
	protected LinkedList<cmd> ReadFileTokens(String fileName) {
		LinkedList<cmd> commands = new LinkedList<cmd>();
		int lineNum = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));) {
			String line;
			do {
			line = br.readLine();
			lineNum++;
			// # is the comment character
			if (line != null && !(line.substring(0,1).contentEquals("#"))) {
				String[] tokens = line.split(" ");
				//System.out.println(line);
				//System.out.println(!(line.substring(0,1).contentEquals("#")));
				// Create blank command and start assigning out tokens
				cmd newCmd = new cmd();
				try { newCmd.type = cmdTypes.valueOf(tokens[0]); }
				catch (Exception E) { System.out.printf("Type code invalid integer at line %d\n", lineNum); }
				//try { newCmd.command = Integer.parseInt(tokens[1]); } 
				//catch (Exception E) { System.out.printf("Command code not integer at line %d\n", lineNum); }
			
				try { newCmd.value1 = ((int) (1000* Double.parseDouble(tokens[1].split(",")[0]))); } 
				catch (Exception E) { System.out.printf("Value1 not integer at line %d", lineNum);}
			
				if (tokens[1].split(",").length > 1) {
					try { newCmd.value2 = ((int) (1000 * Double.parseDouble(tokens[1].split(",")[1]))); } 
					catch (Exception E) { System.out.printf("Value2 not integer at line %d", lineNum);}
				}
				newCmd.line = lineNum;
				commands.add(newCmd);
			}
			} while (line != null); 
		} catch (FileNotFoundException e) {
			System.out.println("Read failed, file not found\n");
		} catch (IOException e) {
			System.out.println("Read failed, file not readable\n");
			//e.printStackTrace();
		}
		// Will test ValidateCmds later
		if (ValidateHeader(commands) && ValidateCmds(commands)) { return commands; }
		//ValidateHeader(commands);
		System.out.println("Failed to validate file returning blank gcode");
		return null;
	}
}