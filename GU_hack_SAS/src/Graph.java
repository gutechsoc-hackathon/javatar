import java.util.HashMap;


public class Graph {

	public HashMap<Long, Node> fileMap;
	
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
	
}
