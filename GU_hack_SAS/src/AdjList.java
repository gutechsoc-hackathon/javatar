import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjList {
	private HashSet<AdjListNode> list;
	private long id;

	public AdjList(long id) {
		this.id = id;
		this.list = new HashSet<AdjListNode>();
	}

	public void add(long id) {
		this.list.add(new AdjListNode(id));
	}

	public void add(AdjListNode node) {
		this.list.add(node);
	}

	public Set<AdjListNode> getList() {
		return list;
	}

	public boolean has(long id) {
		return list.contains(new AdjListNode(id));
	}

	public long getListSize() {
		return list.size();
	}

	public String toString() {
		String output = "";

		for (AdjListNode n : list) {
			output += " " + n;
		}
		return "ID: " + this.id + "HASHSET items: " + output;
	}

}
