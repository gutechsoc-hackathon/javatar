import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class XMLGenerator {
	
	private String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
			+ "<gexf xmlns:viz=\"http:///www.gexf.net/1.1draft/viz\" version=\"1.1\" xmlns=\"http://www.gexf.net/1.1draft\">\n"
			+ "<meta lastmodifieddate=\"2010-04-01+00:34\">\n"
			+ "<creator>Gephi 0.7</creator>\n"
			+ "</meta>\n"
			+ "<graph mode=\"static\" defaultedgetype=\"directed\">\n";
	
	private String xmlBody;	
	private String xmlNodes;
	private String xmlEdges;
	private final String nodeClose = "</node>\n";	
	private final String nodeOpen = "<node ";
	private final String edgeOpen = "<edge ";
	private final String vizOpen = "<viz:color ";
	private final String generalClose = "/>\n";
	
	public XMLGenerator ()
	{
		xmlBody = xmlEdges = xmlNodes = "";
	}
	
	public void processEdges(HashMap<Long, Node> fileMap)
	{	
		System.out.println("Entered Process Edges\n");
		xmlEdges += "<edges>\n";
		String currentEdge = "";
		long edgeId = 0;
		for (Node node: fileMap.values())
		{
			for (long id: node.dislikes.getList())
			{
				xmlEdges += createEdgeString(edgeId, node.getId(), id);
				edgeId++;
			}
			
			for (long id: node.knows.getList())
			{
				xmlEdges += createEdgeString(edgeId, node.getId(), id);
				edgeId++;
			}
			
			for (long id: node.marriedTo.getList())
			{
				xmlEdges += createEdgeString(edgeId, node.getId(), id);
				edgeId++;
			}
			
			for (long id: node.hasDated.getList())
			{
				xmlEdges += createEdgeString(edgeId, node.getId(), id);
				edgeId++;
			}
			
			for (long id: node.friendOf.getList())
			{
				xmlEdges += createEdgeString(edgeId, node.getId(), id);
				edgeId++;
			}
			
		}		
		xmlEdges += "</edges>\n";
	}
	
	public String createEdgeString(long edgeId, long source, long target)
	{
		String edge = "";
		edge += edgeOpen;
		edge += "id=\"" + edgeId + "\" ";
		edge += "source=\"" + source + "\" ";
		edge += "target=\"" + target + "\"";
		edge += generalClose;	
		
		return edge;
	}
	
	public void processNodes(Set<Long> keys)
	{
		xmlNodes += "<nodes>\n";
		String currentNode = "";
		System.out.println("Entered Process Nodes with keys size="+ keys.size() + "\n");
		for (long id: keys)
		{
			//System.out.println("Current id is: " + id + "\n");
			currentNode += nodeOpen;
			currentNode += "id=\"" + id + "\" ";
			currentNode += "label=\"" + id + "\">\n";
			currentNode += nodeClose;
			xmlNodes += currentNode;
			currentNode = "";
		}		
		xmlNodes += "</nodes>\n";
	}
	
	public String generateXML(HashMap<Long, Node> fileMap)
	{
		processNodes(fileMap.keySet());
		processEdges(fileMap);
		xmlBody += xmlHeader + xmlNodes + xmlEdges + "</graph>\n" + "</gexf>\n";
		return xmlBody;
	}
	
	public void writeFile(String yourXML){
	    try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("src/resources/graph2.gexf"));
	        out.write(yourXML);
	        out.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
	}	
}
