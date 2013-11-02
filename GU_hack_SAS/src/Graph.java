import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Graph {

	private HashMap<Long, Node> fileMap;
	
	public Graph(){
		 fileMap = new HashMap<Long,Node>();
	}
	
	public long getSize(){
		return fileMap.size();
	}
	
	public void averageRelationships(){
		long odd_count = 0, even_count = 0, odd_sum = 0, even_sum = 0;
		for (long id : fileMap.keySet()) {
			if (id % 2 == 0) {
				even_count++;
				even_sum += fileMap.get(id).numberOfRelationships();
			} else {
				odd_count++;
				odd_sum += fileMap.get(id).numberOfRelationships();
			}
		}
		System.out.println("Average num of relationships for odd: " + (1.0*odd_sum) / odd_count);
		System.out.println("Average num of relationships for even: " + (1.0*even_sum) / even_count);
	}
	
	public long countPeopleWithFriendOfRelationships() {
		long count = 0;
		for (long id : fileMap.keySet()) {
			if (hasFriendOfRelationship(id)) {
				count++;
			}
		}
		return count;
		
	}
	
	private boolean hasFriendOfRelationship(long id) {
		Node node = fileMap.get(id);
		//System.out.println("for node: " + id + " has friend list size = " + node.getFriendOfList().getListSize());
		for (long friend : node.getFriendOfList().getList()) {
			//System.out.println("check: " + friend);
			Node friendNode = fileMap.get(friend);
			if (friendNode != null) {
				//System.out.println("friend is not null " + friendNode.getId());
			}
			if (friendNode != null && friendNode.isFriendOf(id)) {
				//System.out.println("true");
				return true;
			}
		}
		return false;
	}
	
	//Which person is disliked the most
	public long theMostDislikedPerson()
	{
		HashMap<Long, Long> dislikes = new HashMap<Long, Long>();
		iterateDislikes(this.fileMap, dislikes);
		long maxDislikes = -1;
		long maxDislikesId = -1;
		for (long id : dislikes.keySet()) {
			if (dislikes.get(id) > maxDislikes) {
				maxDislikes = dislikes.get(id);
				maxDislikesId = id;
			}
		}
		return maxDislikesId;
	}
	
	//increments the dislikedByNumOfPeople counter for each Node
	public static void iterateDislikes(HashMap<Long, Node> map, 
			HashMap<Long, Long> dislikes) {
		
		for (long key : map.keySet()) {
			Node curNode = map.get(key);
			for (long n : curNode.getDislikes().getList()) {
				if (dislikes.containsKey(n)) {
	        		dislikes.put(n, dislikes.get(n) + 1);
	        	} else {
	        		dislikes.put(n, 1L);
	        	}
			}
		}
	}
	
	public void addNode(long id) {
		this.fileMap.put(id, new Node(id));
	}
	
	public void addFriend(long node, long friend) {
		fileMap.get(node).addFriend(friend);
	}
	
	public void addDislike(long node, long dislikes) {
		fileMap.get(node).addDislikes(dislikes);
	}
	
	public void addMarried(long node, long married) {
		fileMap.get(node).addMarried(married);
	}
	
	public void addDated(long node, long dated) {
		fileMap.get(node).addDated(dated);
	}
	
	public void addKnows(long node, long knows) {
		fileMap.get(node).addKnows(knows);
	}

	public HashMap<Long, Node> getFileMap() {
		return fileMap;
	}

	public void setFileMap(HashMap<Long, Node> fileMap) {
		this.fileMap = fileMap;
	}
	
}
