package ut.systems.modelling;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyTransition {
    private String id;
    private String incomingMyPlace;
    private String outgoingMyPlace;

    public MyTransition(String id) {
        this.id = id;
    }

    public void setIncomingMyPlace(String incomingMyPlace) {
        this.incomingMyPlace = incomingMyPlace;
    }

    public void setOutgoingMyPlace(String outgoingMyPlace) {
        this.outgoingMyPlace = outgoingMyPlace;
    }
}
