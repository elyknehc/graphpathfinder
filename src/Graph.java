/**
 * Class for a graph, containing nodes
 * @author kylechen
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
	//Instance variables, nodes and graph
	private Node[] nodes;
	private Edge[][] graph;
	
	//Constructor for graph, stores info of the nodes and starts the graph
	public Graph(int n) {
	
		nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
		}
		
		graph = new Edge[n][n];

	}
	
	/**
	 * GetNode, tries to see if node is in the node array, otherwise throws exception
	 */
	public Node getNode(int id) throws GraphException {

		if (id < 0 || id >= nodes.length) {
			throw new GraphException("Node does not exist");
		}
		else {
			return nodes[id];
		}
	}
	
	/**
	 * addEdge adds an edge to the graph
	 */
	public void addEdge(Node u, Node v, String edgeType) throws GraphException {
		
		//Checks to see if the node id is valid or not
		if ( u.getId() >= nodes.length || v.getId() >= nodes.length || u.getId() < 0 || v.getId() < 0) {
			throw new GraphException("Node does not exist");
		}
		
		else {
			//If its an empty slot, then insert it s
			if (graph[u.getId()][v.getId()] == null) {
				graph[u.getId()][v.getId()] = new Edge(u, v, edgeType);
			}
			else {
				throw new GraphException("Node already exists");
			}
			//Insert on both sides
			if (graph[v.getId()][u.getId()] == null) {
				graph[v.getId()][u.getId()] = new Edge(v, u, edgeType);
			}
			else {
				throw new GraphException("Node already exists");
			}
		}
		
	}
	//Get all edges connecting to the vertex of u
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		
		if (u.getId() < 0 || u.getId() >= nodes.length) {
			throw new GraphException("Node does not exist.");
		}
		
		ArrayList<Edge>  incidents = new ArrayList<Edge>();
		
		//Add edge e to the incidents arraylist
		for(Edge e: graph[u.getId()]) {
			if(e != null) {
				incidents.add(e);
			}
		}
			//Return the iterator
		return incidents.iterator();
		
	}
	
	//Get the edge from a graph, otherwise throw an exception
	public Edge getEdge(Node u, Node v) throws GraphException {
		
		if (u.getId() < 0 || u.getId() >= nodes.length || v.getId() < 0 || v.getId() >= nodes.length) {
			throw new GraphException("Node does not exist.");
		}
		
		if (graph[u.getId()][v.getId()] == null) {
			throw new GraphException("Edge does not exist");
		}
		else {
			return graph[u.getId()][v.getId()];
		}
	}
	//Check to see if the edge is adjacent - throw exception in cases
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		
		if (u.getId() < 0 || u.getId() >= nodes.length || v.getId() < 0 || v.getId() >= nodes.length) {
			throw new GraphException("Node does not exist.");
		}
		
		if (graph[u.getId()][v.getId()] != null) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
