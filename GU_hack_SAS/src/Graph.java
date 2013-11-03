import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graph {

	private static TreeMap<Long, Node> fileMap;
	private static TreeMap<Long, Long> dislikedMap;
	public static long longestCycleStartId;
	public static Stack<Long> longestCycleIds;
	public static int awayFromStart = 0;
	public static Set<Set<Long>> clusterByFriends = new HashSet<Set<Long>>();
	long odd_count, even_count, odd_sum, even_sum;
	public Graph(){
		 fileMap = new TreeMap<Long,Node>();
		 dislikedMap = new TreeMap<Long, Long>();
		 odd_count = odd_sum = even_count = even_sum = 0;
	}
	
	public long getSize(){
		return fileMap.size();
	}
	
	public void addDisliked(long id){
		Long count = dislikedMap.get(id);
		if (count == null)
			count = 0L;
		count++;
		dislikedMap.put(id, count);			
	}
	
	public void setVisitedToFalse(){
		for(long id: fileMap.keySet()){
			fileMap.get(id).visited=false;
		}
	}
	
	public void incrementCount(long id)
	{
		if (id % 2 == 0)
			even_count++;
		else
			odd_count++;
	}
	
	public void incrementSum(long id)
	{
		if (id % 2 == 0)
			even_sum++;
		else
			odd_sum++;
	}
	
	public void averageRelationships(){
		/*long odd_count = 0, even_count = 0, odd_sum = 0, even_sum = 0;
		for (Node node : fileMap.values()) {
			if (node.getId() % 2 == 0) {
				even_count++;
				even_sum += node.numberOfRelationships();
			} else {
				odd_count++;
				odd_sum += node.numberOfRelationships();
			}
		}*/
		//Runner.ui.getEvenAve().setText(String.valueOf((1.0*even_sum) / even_count));
		//Runner.ui.getOddAve().setText(String.valueOf((1.0*odd_sum) / odd_count));
		System.out.println("Average num of relationships for odd: " + (1.0*odd_sum) / odd_count);
		System.out.println("Average num of relationships for even: " + (1.0*even_sum) / even_count);
	}
	
	public long countPeopleWithFriendOfRelationships() {
		long count = 0;
		for (Node node : fileMap.values()) {
			if (hasFriendOfRelationship(node)) {
				count++;
			}
		}
		return count;
		
	}
	
	private boolean hasFriendOfRelationship(Node node) {
		//Node node = fileMap.get(id);
		//System.out.println("for node: " + id + " has friend list size = " + node.getFriendOfList().getListSize());
		for (long friend : node.getFriendOfList().getList()) {
			//System.out.println("check: " + friend);
			Node friendNode = fileMap.get(friend);
			if (friendNode != null) {
				//System.out.println("friend is not null " + friendNode.getId());
			}
			if (friendNode != null && friendNode.isFriendOf(node.getId())) {
				//System.out.println("true");
				return true;
			}
		}
		return false;
	}
	
	//Which person is disliked the most
	public long theMostDislikedPerson()
	{
		/*HashMap<Long, Long> dislikes = new HashMap<Long, Long>();
		iterateDislikes(this.fileMap, dislikes);
		long maxDislikes = -1;
		long maxDislikesId = -1;
		for (long id : dislikes.keySet()) {
			if (dislikes.get(id) > maxDislikes) {
				maxDislikes = dislikes.get(id);
				maxDislikesId = id;
			}
		}
		return maxDislikesId;*/
		long max = -1;
		long maxId = -1;
		for(Map.Entry<Long,Long> entry : dislikedMap.entrySet()) {
			if (entry.getValue() > max){				
				max = entry.getValue();
				maxId = entry.getKey();
			}
		}
		return maxId;
	}
	
	//increments the dislikedByNumOfPeople counter for each Node
	/*
	public static void iterateDislikes(TreeMap<Long, Node> map, 
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
	*/
	public void addNode(long id) {
		this.fileMap.put(id, new Node(id));
	}
	
	public void addFriend(long node, long friend) {
		fileMap.get(node).addFriend(friend);
	}
	/*
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
*/
	public TreeMap<Long, Node> getFileMap() {
		return fileMap;
	}
	
	static long maxLen;
	long curLen;
	long curIndex;
	long finalIndex;
	
	public static Set<Long> longestCycle(long id) {
		//setVisitedToFalse();
		maxLen = 0;
		Node start = fileMap.get(id);
		start.visited = true;
		//start.inPath = true;
		Stack<Long> stack = new Stack<Long>();
		stack.push(id);
		Set<Long> linked = new TreeSet<Long>();
		linked.add(id);
		while (stack.size() > 0) {
			Node node = fileMap.get(stack.pop());
			//node.inPath = true;
			for (long neighbourId : node.getFriendOf().getList()) {
				Node neighbour = fileMap.get(neighbourId);
				if (neighbour != null && !neighbour.visited) {
					neighbour.visited = true;
					stack.push(neighbourId);
					linked.add(neighbourId);
				} else if (neighbour != null){
					//System.out.println("The edge (" + node.getId() + ", " + neighbourId +") forms a cycle");
					//System.out.println("The edge (" + node.getId() + ", " + neighbourId +") is in the tree of ");
					if(neighbourId == id) { //TODO You are checking only against the root not.
						if(awayFromStart <( stack.size() + 1)){
							//System.out.println("Stack size " + stack.size());
							awayFromStart = stack.size() + 1; 
							longestCycleStartId = id;
							longestCycleIds = (Stack<Long>)stack.clone();
							longestCycleIds.push(node.getId());						
						}
					}
					else if (linked.contains(neighbourId)){
						if(awayFromStart < (stack.size() - stack.indexOf(neighbourId))){
							//System.out.println("Stack size " + stack.size());
							awayFromStart = stack.size() - stack.indexOf(neighbourId); 
							longestCycleStartId = neighbourId;
							if(longestCycleIds!=null){longestCycleIds.clear();}
							System.out.println("Stack :" + stack.toString());
							System.out.println(neighbourId);
							System.out.println("stack.size() - stack.indexOf(neighbourId) :" + (stack.size() - stack.indexOf(neighbourId) ));
							/*for (int i = stack.indexOf(neighbourId); i < stack.size(); i++)
								longestCycleIds.push(stack.get(i));
							longestCycleIds.push(node.getId());*/
						}
					}
				
				}
				
			}
		}
		// keeps all visited nodes and their distance from the beginning
		//TODO initialize to unvisited
		return linked;
	}
	public long countConnectedComponents = 0;
	public void partisionByFriends(){
		Set<Long> mainIds = new HashSet<Long>(fileMap.keySet());
		boolean isItRunning = true;
		long a = 0;
		while(isItRunning){
			/*a++;
			if(a%10000==0){
				System.out.println(a);
			}*/
			//System.out.println("Kolko ostavat :"+mainIds.size());
			countConnectedComponents++;
			Set<Long> connectedIds = null;
			for(Long id: mainIds){
				connectedIds=longestCycle(id);
				break;
			}
			if (connectedIds != null) {
				mainIds.removeAll(connectedIds);
				clusterByFriends.add(connectedIds);
			}
			if(mainIds.isEmpty()){
				isItRunning=false;
			}
		}
	}

	public long getOdd_count() {
		return odd_count;
	}

	public void setOdd_count(long odd_count) {
		this.odd_count = odd_count;
	}

	public long getEven_count() {
		return even_count;
	}

	public void setEven_count(long even_count) {
		this.even_count = even_count;
	}

	public long getOdd_sum() {
		return odd_sum;
	}

	public void setOdd_sum(long odd_sum) {
		this.odd_sum = odd_sum;
	}

	public long getEven_sum() {
		return even_sum;
	}

	public void setEven_sum(long even_sum) {
		this.even_sum = even_sum;
	}
}
