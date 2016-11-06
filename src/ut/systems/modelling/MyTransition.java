package ut.systems.modelling;

import java.util.ArrayList;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyTransition {
    private String id;
    private ArrayList<MyPlace> incomingMyPlace;
    private ArrayList<MyPlace> outgoingMyPlace;

    public String getId() {
        if (id == ""){
            return "Pasal pole id'd";
        }
        return id;
    }

    public MyTransition(String id) {
        incomingMyPlace = new ArrayList<>();

        outgoingMyPlace = new ArrayList<>();
        this.id = id;
    }

    public void setIncomingMyPlace(MyPlace incomingMyPlace) {
        this.incomingMyPlace.add(incomingMyPlace);
    }

    public void setOutgoingMyPlace(MyPlace outgoingMyPlace) {
        this.outgoingMyPlace.add(outgoingMyPlace);
    }

    public ArrayList<MyPlace> getIncomingPlaces() {return incomingMyPlace;}

    public ArrayList<MyPlace> getOutgoingPlaces() { return outgoingMyPlace;}
}
