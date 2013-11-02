public class Node {
	private long id;
	public AdjList dislikes;
	public AdjList friendOf;
	public AdjList knows;
	public AdjList marriedTo;
	public AdjList hasDated;
	public long dislikedByNumOfPeople;
	
	public Node(long id) {
		this.id = id;
		this.dislikes = new AdjList(id);
		this.friendOf = new AdjList(id);
		this.knows = new AdjList(id);
		this.marriedTo = new AdjList(id);
		this.hasDated = new AdjList(id);
	}
	public long getId() {
		return this.id;
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

	public long numberOfRelationships() {
		return dislikes.getListSize() + 
				friendOf.getListSize() + 
				knows.getListSize() + 
				marriedTo.getListSize() + 
				hasDated.getListSize();
	}
	public boolean HasRelationshipWith(long id)
	{
		return this.dislikes.has(id) || this.friendOf.has(id) || this.marriedTo.has(id)
				|| this.hasDated.has(id) || this.knows.has(id);
	}
}
