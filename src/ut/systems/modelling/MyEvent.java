package ut.systems.modelling;

/**
 * Created by Philosoraptor on 03/11/2016.
 */
public class MyEvent extends MyBPMNNode{

    private String id;


    public MyEvent(String id) {
        super(id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
