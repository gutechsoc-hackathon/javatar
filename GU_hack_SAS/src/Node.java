public class Node {

	public AdjList DISLIKES = new AdjList();
	public AdjList FRIEND_OF = new AdjList();
	public AdjList KNOWS = new AdjList();
	public AdjList MARRIED_TO = new AdjList();
	public AdjList HAS_DATED = new AdjList();

	public long NumberOfDislikes() {
		return this.DISLIKES.getListSize();
	}

	public long NumberOfFriends() {
		return this.FRIEND_OF.getListSize();
	}

	public long NumberOfMarried() {
		return this.MARRIED_TO.getListSize();
	}

	public long NumberOfKnows() {
		return this.KNOWS.getListSize();
	}

	public long NumberOfDated() {
		return this.HAS_DATED.getListSize();
	}
}
