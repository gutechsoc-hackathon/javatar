
public class Node {
	
	public AdjList dislikes;
	public AdjList frindOf;
	public AdjList knows;
	public AdjList marriedTo;
	public AdjList hasDated;
	
	public Node() {
		this.dislikes = new AdjList();
		this.frindOf = new AdjList();
		this.knows = new AdjList();
		this.marriedTo = new AdjList();
		this.hasDated = new AdjList();
	}

}
