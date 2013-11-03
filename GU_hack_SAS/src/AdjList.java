import java.util.HashSet;
import java.util.Set;

public class AdjList {
	private HashSet<Long> list;
	private long id;

	public AdjList(long id) {
		this.id = id;
		this.list = new HashSet<Long>(4);
	}

	public void add(long id) {
		this.list.add(id);
	}
/*
	public void add(AdjListNode node) {
		this.list.add(node);
	}*/

	public Set<Long> getList() {
		return list;
	}

	public boolean has(long id) {
		return list.contains(id);
	}

	public long getListSize() {
		return list.size();
	}

	public String toString() {
		String output = "";

		for (long n : list) {
			output += " " + n;
		}
		return "ID: " + this.id + "HASHSET items: " + output;
	}

}
