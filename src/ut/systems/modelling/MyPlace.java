package ut.systems.modelling;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyPlace {
    private String id;
    private MyTransition incomingMyTransition = null;
    private MyTransition outgoingMyTransition = null;


    public MyPlace(String id) {
        this.id = id;
    }

    public void setIncomingMyTransition(MyTransition incomingMyTransition) {
        this.incomingMyTransition = incomingMyTransition;
    }

    public void setOutgoingMyTransition(MyTransition outgoingMyTransition) {
        this.outgoingMyTransition = outgoingMyTransition;
    }

    public MyTransition getIncomingTransition(){return incomingMyTransition;}

    public MyTransition getOutgoingTransition(){return outgoingMyTransition;}
}
