package ut.systems.modelling;

/**
 * @(#) MyBPMNNode.java
 */

public class MyBPMNNode {

    // holding the id of the node (used by Flows to get source and target node)
    private String id;



    public MyBPMNNode(String id) {
        this.id = id;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
