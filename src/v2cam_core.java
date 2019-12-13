import java.io.*;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
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
	// Init to 2mm in thousandths
	int xinit = 2000;
	int yinit = 2000;
	int zinit = 2000;
	int millspeed,clearspeed,zspeed,zclear, zstep, zmaxdepth, bitwidth, millingspeed;
	
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
	
	// Going to revise this code, break up vertex placement/pathing from output to string
	// Build vertex order first creating a vertex list object, 
	// Pass through an optimizer (remove redundant vtx's, combine movements where possible etc)
	// Then go through the list of vertexes and create the gcode string for the path
	public LinkedList<triplet> cmdstoVertexList() {
		// x,y,z will be used for internal tracking of jog coordinates
		int x = this.xinit;
		int y = this.yinit;
		int z = this.zinit;
		LinkedList<triplet> vtx = new LinkedList<triplet>();
		//VertexList vtx = new VertexList();
		// These variables come from the settings codes
		// *speed values control movement when milling, clear of surface and ramp/plunge cuts
		// zclear and zmaxdepth control minimum z to be assumed clear of surface and z depth at max
		// bitwidth sets the width of the bit for inside/outside dimension corrections

		// For compiling will hardcode these values
		zmaxdepth=-1500; 
		bitwidth=3175;
		zclear=5;
		
		
		LinkedList<cmd> commands = this.FileParser.getCmds();
		cmd cmdItem;
		// Always create 'home point' vertex, all operations assume previous one committed vertex for its end position
		// x,y,z initialized above from settings
		vtx.add(new triplet(x,y,z));
		Iterator<cmd> i = commands.iterator();
		while (i.hasNext()) {
			cmdItem = i.next();
			// Skip any type S commands (later may want to active them to modify some settings mid-file)
			if (cmdItem.type != cmdTypes.S) {
				if (cmdItem.type == cmdTypes.C) { /* skip at present */ }
				else if (cmdItem.type == cmdTypes.D) { // Drop and leave
					// Drop the z axis with a $J=Z[value] offset from safez
					vtx.add(new triplet(x,y,zclear)); // Go to z-safe
					x = x + cmdItem.value1;
					y = y + cmdItem.value2;
					vtx.add(new triplet(x, y, zclear)); // Go to x,y
					vtx.add(new triplet(x, y,zmaxdepth)); // Drop bit to depth
					z = cmdItem.value2; // Save the current z-axis set
					vtx.add(new triplet(x,y,z)); // Raise bit to given value
				}
				else if (cmdItem.type == cmdTypes.P) {
					vtx.add(new triplet(x,y,zclear)); // Go to z-safe at last saved (x,y) coords
					x = x + cmdItem.value1;
					y = y + cmdItem.value2;
					// Using new (x,y)
					vtx.add(new triplet(x, y, zclear)); // Go to x,y
					vtx.add(new triplet(x,y,zmaxdepth)); // Drop bit to depth
					z = zclear; // Save the current z-axis set
					vtx.add(new triplet(x,y,zclear)); // Raise bit to given value
				}
				else if (cmdItem.type == cmdTypes.V) {
					// Optimizer will handle pathing, breaking step down and determining inside/outside angles
					x = x + cmdItem.value1;
					y = y + cmdItem.value2;
					// z will probably be at z-clear from a previous operation
					// 
					vtx.add(new triplet(x,y,z));
				}
			}
		}
		return vtx;
	}
	
	// This should be coded such that putting a path back into it should result in the same path
	// Multiple arrangements of vertexlists may result in equal paths but a computed path should always return the same path
	// Resulting path is a walk from (0,0), last step will convert walk to absolute coords including xinit and yinit
	private LinkedList<triplet> vtxtoPath(LinkedList<triplet> vertexList) {
		// Not going to solve TSP here. The vertex list provided will be followed
		// Will not try to reorder steps to make shorter or simpler paths
		LinkedList<triplet> path = new LinkedList<triplet>();
		triplet current, next, last, newVtx = new triplet(0,0,0);
		//int x = this.xinit;
		//int y = this.yinit;
		//int z = this.zinit; // Usually 0
		int direction = -1; // Direction controls pocketing or profiling, -1 creates outside clearance (profiling), 1 offsets inside the line (pocketting)
		int offset = ((bitwidth*10)/2); // Bit width, to a 1/10,000th of mm, when needed round such that face is correctest and outer is less precise
		int clearance = bitwidth+offset;
		Boolean clearancePass = true;
		
		// Need to make several determinations
		// Is this vertex completing a z step (came back to a vertex that has been done but is not on zmaxdepth
		// Is this vertex completing an outer or inner path (once wide second time at final dimensions) and shifting coordinate offsets?
		// Is the vertex making an inner or outer turn (- or + rotation relative to last line segment)
		// Is this vertex at safe z, can it be combined with another movement to reduce vertices in path?
		// Does this vertex step need to be expanded (ramp step breaks into 2, a maxrate plunge+mill and a mill only path for remainder)
		// Possibly a backup step for ramps to return to start point at current zstep
		
		//This assumed offset to to a profile
		path.add(new triplet(x+(direction*clearance),y+(direction*clearance),zclear));
		int i = 0;
		while (vertexList.size() >= i) 
		{
			current = vertexList.get(i);
			newVtx = new triplet(x + current.x+(direction*clearance),y + current.y+(direction*clearance),z + current.z);
		 	if (i == 0) { last = vertexList.get(0); }
		 	else { last = vertexList.get(i-1); }
		 	if (i+1 < vertexList.size()) { next = vertexList.get(i+1); }
		 	else { next = vertexList.get(i); }

		 	// Determine if this vertex is in the list, toggle clearance cut or milling cut, possibly do a ramp vertex
		 	if (path.contains(newVtx)) {
		 		clearancePass = clearancePass ? false : true;
		 		clearance = clearance + (clearancePass ? offset : -offset);
		 		newVtx.x = current.x+(direction*clearance);
		 		newVtx.y = current.y+(direction*clearance);
		 		newVtx.z = current.z;
			 	// Need to determine ramp settings here and potentially break one movement into two
		 		if (path.contains(newVtx)) {
		 			// made clearance pass and milling pass, time to ramp or finish
		 			// If at finish current == last also
		 			// Ramp mode will break vertexes but never change direction, maybe just create the additional vertex here in the list
		 			// And then let the path continue as it will still reach the destination at the zstep
		 			// Determine length of cut, and should be on a clearance pass
		 			// Length needs to be millingspeed/plungespeed or greater
		 			double length = Math.sqrt((current.x*current.x) + (current.y*current.y));
		 			double ramp = zstep*(millingspeed / zspeed);
		 			double ramplength = Math.sqrt((zstep * zstep) + 1);
		 			// If this then we're good, need to find portion of pass at reduced ramp speed
		 			if (length > ramp) { 
		 				triplet rampVtx = new triplet(newVtx.x * ramplength, newVtx.y * ramplength, newVtx.z - zstep);
		 				path.add(rampVtx);
		 			} /*else if (length * 3 > ramp) {
		 				// Need to slow down the milling speed to complete plunge this jog
		 				// Try ramp at triple distance, double back
		 				
		 				// Todo, impliment this, for the time being will use the fallback, plunge and mill
		 				System.out.println("Tried to do a switchback ramp, not fully implimented do not use the result file");
		 				triplet rampVtx = new triplet(last.x, last.y, last.z - zstep);
		 			} */
		 			else {
		 				// f'it just do a plunge to depth then mill
		 				triplet rampVtx = new triplet(last.x, last.y, last.z - zstep);
		 				path.add(rampVtx);
		 			}
		 		}
		 	}


		 	// Also check for safe z and combine vertexes until next z movement occurs
		 	// If z raising to zclear then at .2mm above 0z can increase to zclear speed for rest of z movement
		 	if (last.z < 0 && current.z > zclear) {
		 		path.add(new triplet(newVtx.x, newVtx.y, (zclear/20)));
		 	}
		 	
		 	// For a profile cut this will start at -1/2 bitwidth, for a pocket it will be + 1/2 bitwidth
		 	// Offset will also include cut-width, presumed at 1.5 bitwidth as minimum clearance required for a clean cut
		 	path.add(newVtx); 	
		 	
		 	// Determine if this is an inside angle or outside angle, alter direction if needed and keep for next vertex to correct with
		 	// Rotation => +/- value used to check direction and alter +/-bitwidth gets added to each step
		 	// Shoelace algorithm, only on 2d coords, no interest in z at this points
		 	double rotation = (last.x * current.y) + (current.x * next.y)+ (next.x * last.y) - (current.x * last.y) - (next.x * current.y) - (last.y * next.x);  
		 	if (rotation < 0 && direction > 0) {
		 		direction = direction * -1;
		 		// Only care if this changes, just check if it is same +/- relative to direction
		 		// Or if pocketing, is opposite relative to direction
			 	// Now with clearance direction for next move set the next vtx will compute with this correct
		 		// If rotation = 0 then set is a line, no need to change offsets here
		 	}
		 	// Handle for this being the last vertex, revert to original direction hardcoded now for profiling cutting
		 	if (vertexList.peekLast() == current) { direction = -1; }
		 	i++;
		}
		
		return path;
	}

	private String pathtoGcode(LinkedList<triplet> vertices) {
		Iterator<triplet> iter = vertices.iterator();
		triplet current = new triplet(0,0,0);
		int x = this.xinit;
		int y = this.yinit;
		int z = this.zinit;
		String commandStr = "";
		while (iter.hasNext()) {
			current = iter.next();
			x = x + current.x;
			y = y + current.y;
			z = z + current.z;
			// Will need to determine motion type based on previous motion and next motion
			// Nevermind, every motion at this point will be a jog motion, optimizer will expand/reduce
			// vertex list as needed. This will only have to determine speeds
			commandStr = commandStr + getJogCode(x,y,z);	
				// If this z and the z at vertex at last pass are same attempt to ramp down by 1 zstep else simple straight cutting
		}
		return commandStr;
	}
	
	private String getJogCode(Integer x, Integer y, Integer z) {
		return getJogCode(x,y,z,((z<=zclear) ? millingspeed : clearspeed));
	}
	
	// Ramp functions can call this directly while others can leave out specifying the speed
	private String getJogCode(Integer x, Integer y, Integer z, Integer speed) {
		Double factor = 1000.0;
		DecimalFormat df = new DecimalFormat("####0.0000"); // This format gives up to 9.99meters of range
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMaximumFractionDigits(4);
		return "$J=X" +	df.format(x) + 
			" Y" + df.format(y/factor) +
			" Z" + df.format(x/factor) + 
		    " F" + speed + "\n";
	}
}