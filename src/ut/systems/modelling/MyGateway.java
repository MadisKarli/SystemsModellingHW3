package ut.systems.modelling;

/**
 * Created by Philosoraptor on 03/11/2016.
 */
public class MyGateway extends MyBPMNNode {

    private String id;
    private String type;


    public MyGateway(String id, String type) {
        super(id);
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
