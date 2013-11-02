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
		for (AdjListNode friend : node.getFriendOfList().getList()) {
			Node friendNode = fileMap.get(friend.getId());
			if (friendNode.isFriendOf(id)) {
				return true;
			}
		}
		return false;
	}
	
	//Which person is disliked the most
	public long theMostDislikedPerson()
	{
		iterateDislikes(this.fileMap);
		Iterator it = this.fileMap.entrySet().iterator();
		Node mostDisliked;
		if (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry)it.next();
			mostDisliked = (Node)pairs.getValue();
		}
		else
			return 0;			
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        Node curNode = (Node)pairs.getValue();
	        if (curNode.getDislikedByNumOfPeople() > mostDisliked.getDislikedByNumOfPeople())
	        	mostDisliked = curNode;
	    }		
		return mostDisliked.getDislikedByNumOfPeople();
	}
	
	//increments the dislikedByNumOfPeople counter for each Node
	public static void iterateDislikes(HashMap<Long, Node> map) {
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        Node curNode = (Node)pairs.getValue();
	        for(AdjListNode n : curNode.getDislikes().getList())
	        {
	        	map.get(n.getId()).dislikedByNumOfPeople++;
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
