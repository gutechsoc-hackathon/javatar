public class Node {
	
	public AdjList dislikes;
	public AdjList friendOf;
	public AdjList knows;
	public AdjList marriedTo;
	public AdjList hasDated;
	
	public Node() {
		this.dislikes = new AdjList();
		this.friendOf = new AdjList();
		this.knows = new AdjList();
		this.marriedTo = new AdjList();
		this.hasDated = new AdjList();
	}

	public long NumberOfdislikes() {
		return this.dislikes.getListSize();
	}

	public long NumberOfFriends() {
		return this.friendOf.getListSize();
	}

	public long NumberOfMarried() {
		return this.marriedTo.getListSize();
	}

	public long NumberOfknows() {
		return this.knows.getListSize();
	}

	public long NumberOfDated() {
		return this.hasDated.getListSize();
	}
}
