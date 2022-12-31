/**
 * Class Representing a Node 
 * @author kylechen
 */
public class Node {
	
	//Instance variables

	private int id;
	private boolean mark;
	
	
	//Constructor
	public Node(int id) {
		this.id = id;
	}
	
	//Getters and marknode for Node
	public void markNode(boolean mark) {
		this.mark = mark;
	}
	
	//Gets mark
	public boolean getMark() {
		return this.mark;
	}
	
	//Get id
	public int getId() {
		return this.id;
	}
}
