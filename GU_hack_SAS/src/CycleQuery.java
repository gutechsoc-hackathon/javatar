import java.rmi.Naming;
 
public class CycleQuery { 
    public static void main(String args[]) throws Exception {
    	
    	UserFunctions obj = (UserFunctions)Naming.lookup("//localhost/DataStructure");
        
    	long time = System.currentTimeMillis();
    	
        //call the cycle finding function
        
        System.out.println("Time for stats execution: " + (System.currentTimeMillis()-time)/1000);
        
    }
}