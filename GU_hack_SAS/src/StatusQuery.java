import java.rmi.Naming;
 
public class StatusQuery { 
    public static void main(String args[]) throws Exception {
    	
    	UserFunctions obj = (UserFunctions)Naming.lookup("//localhost/DataStructure");
        
        System.out.println("Total number of people: " + obj.getNum());
        obj.getAve();
        System.out.println("People with relationship with themselves: " + obj.getRelHimself());
        System.out.println("People with FRIEND_TO relationships: " + obj.getFROfRel());
        System.out.println("The most disliked person: " + obj.getDisliked());
        
        
    }
}