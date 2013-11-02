import java.util.HashSet;


public class AdjList {
	private HashSet<AdjListNode> list;
	
	public AdjList() {
		this.list = new HashSet<AdjListNode>();
	}
	
	public void add(long id) {
		this.list.add(new AdjListNode(id));
	}
	
	public void add(AdjListNode node) {
		this.list.add(node);
	}
	

	public boolean has(long id) {
		return list.contains(new AdjListNode(id));
	}
	public long getListSize()
	{
		return list.size();
	}
}
