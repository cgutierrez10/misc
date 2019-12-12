import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

// Convert vertex list to gcode path
// Include step down, toolwidth and path clearance (width to carve and also safe Z)
// Allow setting default ramp speed, plunge speed, jog speed

// Format
// s or S for setup commands
// p for point commands (x,y z up, z down, z up)
// v for vertex commands (x,y z down)
// c for circle (point, radius z up, z down, circle out by 1/2 toolwidth, 1 depth step, repeat until full depth)
// c may be implemented later

// s is 2 digit number associated with a setting
// All others are duples in mm coords
public class v2cam_core {
	String filename = "test.vtx";
	parser FileParser = new parser(filename);
	Double[] settings = new Double[256];
	int millspeed,clearspeed,zspeed,zclear, zstep;
	double bitwidth, zmaxdepth, millingspeed;
	
	// Validate stream
	// On fail should output appropriate errors then return bool to block further processing
	// Initially accept only one set for each, global to the machine process
	// Later accept re-set values midway into project
	private void PopulateSettings(LinkedList<cmd> commands) {
		
		cmd cmdItem;
		Iterator<cmd> i = commands.iterator();
		while (i.hasNext()) {
			cmdItem = i.next();
			if (cmdItem.type == cmdTypes.S)
			{
				this.settings[(cmdItem.value1)] = ((double) cmdItem.value2 / 1000);
			}
		}
	}
	
	public String vtx2gcode() {
		// x,y,z will be used for internal tracking of jog coordinates
		int x = 0;
		int y = 0;
		int z = 0;
		LinkedList<triplet> VertexCut = null;
		// These variables come from the settings codes
		// *speed values control movement when milling, clear of surface and ramp/plunge cuts
		// zclear and zmaxdepth control minimum z to be assumed clear of surface and z depth at max
		// bitwidth sets the width of the bit for inside/outside dimension corrections

		// For compiling will hardcode these values
		zmaxdepth=-1.5;
		bitwidth=3.175;
		zclear=5;
		String clearspeedCode = " F" + clearspeed + "\n";
		
		
		LinkedList<cmd> commands = this.FileParser.getCmds();
		cmd cmdItem;
		String commandStr = "";
		Iterator<cmd> i = commands.iterator();
		while (i.hasNext()) {
			cmdItem = i.next();
			// Skip any type S commands (later may want to active them to modify some settings mid-file)
			if (cmdItem.type != cmdTypes.S) {
				if (cmdItem.type == cmdTypes.C) { /* skip at present */ }
				else if (cmdItem.type == cmdTypes.D) {
					// Drop the z axis with a $J=Z[value] offset from safez
					commandStr = commandStr + "$J=Z" + ((Double) (cmdItem.value1/1000.0)).toString() + getSpeedCode(zspeed);
					commandStr = commandStr + "$J=Z" + ((Double) (cmdItem.value2/1000.0)).toString() + getSpeedCode(zspeed);
					z = cmdItem.value2; // Save the current z-axis set
				}
				else if (cmdItem.type == cmdTypes.P) {
					// Set to safe z, move to x,y, drop z to zmaxdepth return to safe z
					x = x+cmdItem.value1;
					y = y+cmdItem.value2;
					commandStr = commandStr + getSafeZUpCode();
					commandStr = commandStr + getJogCode(x,y,z);
					commandStr = commandStr + getSafeZUpCode();
				}
				else if (cmdItem.type == cmdTypes.V) {
					x = x+cmdItem.value1;
					y = y+cmdItem.value2;
					if (!(VertexCut.contains(new triplet(x,y,z)))) {
						VertexCut.add(new triplet(x,y,z));
						commandStr = commandStr + getJogCode(x,y,z);	
					} else {
						// Determine ramp down max angle and break jog into 2 steps
						// Several potential problem cases
						// If zstep is too steep may not be able to complete at a reasonable slope for feed
						// Will then need to drop millingspeed to get steeper angle to complete by vertex
						z = z + zstep;
						VertexCut.add(new triplet(x,y,z));
						
					}
					// If this z and the z at vertex at last pass are same attempt to ramp down by 1 zstep else simple straight cutting
					
					
				}
			}
		}
		return "";
	}
	
	private String getSpeedCode(int speed) {
		return " F" + speed + "\n";
	}
	
	private String getSpeedCode(double speed) {
		return " F" + speed + "\n";		
	}
	
	private String getSafeZUpCode()
	{
		return "$J=Z5" + getSpeedCode(clearspeed);
	}
	
	private String getJogCode(int x, int y, int z) {
		return "$J=X" + 
			((Double) (x/1000.0)).toString() + 
			" Y" + 
			((Double) (y/1000.0)).toString() +  
		    ((z<zclear) ? getSpeedCode(millingspeed) : getSpeedCode(clearspeed)) + "\n";
	}
}