/**
 * Class representing an edge
 * @author kylechen
 *
 */
public class Edge {

	//Instance variables
	private String type;
	private Node u;
	private Node v;
	
	//Edge constructor
	public Edge(Node u, Node v, String type) {
		this.type = type;
		this.u = u;
		this.v = v;
	}
	
	//Getters for instance variables
	
	public Node firstNode() {
		return this.u;
	}
	
	public Node secondNode() {
		return this.v;
	}
	
	public String getType() {
		return this.type;
	}
}
