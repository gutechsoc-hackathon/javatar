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
		
	}
}
