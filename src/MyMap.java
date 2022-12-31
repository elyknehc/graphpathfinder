/**
 * MyMap file, creating a graph and storing information to find the path to the destination
 * @author kylechen
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;


//Import java util to help 

public class MyMap {

	//Instance variables
	private int gridSize;
	private int maxPrivateRoads;
	private int maxConstructionRoads;
	private int startingNode;
	private int destinationNode;
	private int mapWidth;
	private int mapLength;
	private Graph myGraph;
	private Stack<Node> stack = new Stack();
	
	//Constructor
	public MyMap(String inputFile) throws MapException {
		
		BufferedReader input;
		try {
			
			//Read from file
			input = new BufferedReader(new FileReader(inputFile));
			this.gridSize = Integer.parseInt(input.readLine()); 
			this.startingNode = Integer.parseInt(input.readLine());
			this.destinationNode = Integer.parseInt(input.readLine());
			this.mapWidth = Integer.parseInt(input.readLine());
			this.mapLength = Integer.parseInt(input.readLine());
			this.maxPrivateRoads = Integer.parseInt(input.readLine());
			this.maxConstructionRoads = Integer.parseInt(input.readLine());
			
			this.myGraph = new Graph(this.mapWidth*this.mapLength);
			 
			String thisLine = input.readLine();
			
			int counter = 0; //Counts the index of the row we are on (either a horizontal or a vertical row)
			
			
			while (thisLine != null) {
	
				
				//Horizontal add P, V, C roads
				if (counter%2 == 0) {
					
					
					for (int i = 1; i < thisLine.length(); i+=2) {
						
						//Add edge for public road, calculation determines the position of the node
						if (thisLine.charAt(i) == 'P') {
							this.myGraph.addEdge(myGraph.getNode((i-1)/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i-1)/2)+1 + (int) counter/2 * this.mapWidth), "Public");
						}
						
						
						//Add edge for private road
						if (thisLine.charAt(i) == 'V') {
							this.myGraph.addEdge(myGraph.getNode((i-1)/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i-1)/2)+1 + (int) counter/2 * this.mapWidth), "Private");
						}
						
						//Add edge for construction road
						if (thisLine.charAt(i) == 'C') {
							this.myGraph.addEdge(myGraph.getNode((i-1)/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i-1)/2)+1 + (int) counter/2 * this.mapWidth), "Construction");
						
						}
					}
					
				}
				
				//Vertical connections, calculations determine the position of the node
				else {
					
					for (int i = 0; i < thisLine.length(); i+=2) {
						
						if (thisLine.charAt(i) == 'P') {
							
							this.myGraph.addEdge(myGraph.getNode(i/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i)/2)+ this.mapWidth + (int) counter/2 * this.mapWidth), "Public");
						}
						
						if (thisLine.charAt(i) == 'V') {
							this.myGraph.addEdge(myGraph.getNode(i/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i)/2)+ this.mapWidth + (int) counter/2 * this.mapWidth), "Private");
						}
						
						if (thisLine.charAt(i) == 'C') {
							this.myGraph.addEdge(myGraph.getNode(i/2 + (int) counter/2 * this.mapWidth), myGraph.getNode( ((i)/2)+ this.mapWidth + (int) counter/2 * this.mapWidth), "Construction");
						}
			
					}
				}
				
				//Go through the text file
				counter = counter+1;
				thisLine = input.readLine();
				
			}
			
			input.close();
		}
		//If we get an error: throw an exception
		catch (Exception e) {
			throw new MapException("Error reading file");
		}
		
	}
	
	//Getter for graph
	public Graph getGraph() {
		return this.myGraph;
		
	}
	
	//Getter for starting node
	public int getStartingNode() {
		return this.startingNode;
	}
	
	//Getter for destinationNode
	public int getDestinationNode() {
		return this.destinationNode;
	}
	
	//Getter for max private roads
	public int maxPrivateRoads() {
		return this.maxPrivateRoads;
	}
	
	//Getter for max construction roads
	public int maxConstructionRoads() {
		return this.maxConstructionRoads;
	}
	
	/**
	 * Find path parameter to find the path
	 * @param start
	 * @param destination
	 * @param maxPrivate
	 * @param maxConstruction
	 * @return
	 */
	public Iterator<Node> findPath(int start, int destination, int maxPrivate, int maxConstruction) {
		
		
		try {
			//Get the starting node
			Node startingNode = this.myGraph.getNode(start);
			
			//Push the node that we are on into the stack
			startingNode.markNode(true);
			this.stack.push(startingNode);
			
			if (start == destination) {
				//If we are at desination, return the stack
				return stack.iterator();
			}
			
			//Create a new iterator to find the incidentEdges
			Iterator<Edge> myIterator = this.myGraph.incidentEdges(startingNode);
				
			while (myIterator.hasNext() == true) {
				
				//Look at the edges and look at the second node
				Edge visit = myIterator.next();
				
				Node endingNode = visit.secondNode();
				
				//If it has not been marked yet, we should visit the node
				if (endingNode.getMark() != true) {
					
					//Recursively call findpath for construction, private and public roads - if a path is found, return the stack iterator
					if (visit.getType().equals("Construction")) {
						
						if (maxConstruction > 0) {
						
							if (findPath(endingNode.getId(), destination, maxPrivate, maxConstruction-1) != null) {
								return stack.iterator();
							}
						}
						
					}
					
					else if (visit.getType().equals("Private")) {
						
						if (maxPrivate > 0) {
							if (findPath(endingNode.getId(), destination, maxPrivate-1, maxConstruction)!=null) {
								return stack.iterator();
							}
						}
					}
						
					else if (visit.getType().equals("Public")) {
							if (findPath(endingNode.getId(), destination, maxPrivate, maxConstruction) != null) {
								return stack.iterator();
							}

						}
						
					}
			
				}
				
			//If we cannot find a path, then pop it and mark the node to be false and return null
				startingNode.markNode(false);
				stack.pop();
				return null;
			
		}
			
		//Error with reading, throw exception
		 catch (GraphException e) {
			System.out.println(e);
			return null;
		}
		
	}
}
