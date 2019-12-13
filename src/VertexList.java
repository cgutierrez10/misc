import java.util.LinkedList;

public class VertexList {
	
	LinkedList<triplet> vtxlist = new LinkedList<triplet>();

	
	public boolean Contains(int a, int b, int c) {
		return vtxlist.contains(new triplet(a,b,c));
	}
	
	public void addVertex(int a, int b, int c) {
		vtxlist.add(new triplet(a,b,c));
	}
	
	// No support for removing vertexes that were added errantly code should address that by not adding them.
	
}
