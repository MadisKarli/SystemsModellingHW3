package ut.systems.modelling;

/**
 * @(#) MyBPMNNode.java
 */

public class MyTask extends MyBPMNNode {

    private String id;

    public MyTask(String id) {
        super(id);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
