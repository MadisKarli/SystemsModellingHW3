package ut.systems.modelling;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyPlace {
    private String id;
    private String incomingMyTransition;
    private String outgoingMyTransition;



    public MyPlace(String id) {
        this.id = id;
    }

    public void setIncomingMyTransition(String incomingMyTransition) {
        this.incomingMyTransition = incomingMyTransition;
    }

    public void setOutgoingMyTransition(String outgoingMyTransition) {
        this.outgoingMyTransition = outgoingMyTransition;
    }
}
