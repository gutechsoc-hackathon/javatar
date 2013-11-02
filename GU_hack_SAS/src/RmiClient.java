import java.rmi.Naming;
 
public class RmiClient { 
    public static void main(String args[]) throws Exception {
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
        obj.getAve();
        System.out.println(obj.getNum() + " all ppl");
        System.out.println(obj.getDisliked()+ " disliked");
        System.out.println(obj.getFROfRel()+" fr of rel");
        System.out.println(obj.getRelHimself()+" to himself relation");
    }
}