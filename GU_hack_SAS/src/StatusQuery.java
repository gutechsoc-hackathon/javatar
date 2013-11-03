import java.rmi.Naming;
 
public class StatusQuery { 
    public static void main(String args[]) throws Exception {
    	
    	UserFunctions obj = (UserFunctions)Naming.lookup("//localhost/DataStructure");
        obj.getAve();
        System.out.println(obj.getNum() + " all ppl");
        System.out.println(obj.getDisliked()+ " disliked");
        System.out.println(obj.getFROfRel()+" fr of rel");
        System.out.println(obj.getRelHimself()+" to himself relation");
    }
}