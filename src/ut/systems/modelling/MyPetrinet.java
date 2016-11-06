package ut.systems.modelling;

import org.processmining.models.graphbased.directed.petrinet.Petrinet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyPetrinet{
    private String label;
    private Collection<MyPlace> myPlaces;
    private Collection<MyTransition> myTransitions;


    public MyPetrinet(String label) {
        this.label = label;
        myPlaces = new ArrayList<MyPlace>();
        myTransitions = new ArrayList<MyTransition>();
    }



    public void addPlace(MyPlace myPlace) {
        myPlaces.add(myPlace);
    }

    public void addTransition(MyTransition myTransition) {
        myTransitions.add(myTransition);
    }

    public void addArcT2P(MyTransition myTransition, MyPlace myPlace){
        myTransition.setOutgoingMyPlace(myPlace);
        myPlace.setIncomingMyTransition(myTransition);
    }

    public void addArcP2T(MyTransition myTransition, MyPlace myPlace) {
        myTransition.setIncomingMyPlace(myPlace);
        myPlace.setOutgoingMyTransition(myTransition);
    }

    public void removeArcT2P(MyTransition myTransition, MyPlace outgoingPlace) {
        myTransition.setOutgoingMyPlace(null);
        outgoingPlace.setIncomingMyTransition(null);
    }

    public void removeTransition(MyTransition subProcessTransition) {
        myTransitions.remove(subProcessTransition);
    }

    public MyPlace getStartPlace(){
        for(MyPlace myPlace: myPlaces){
            if(myPlace.getIncomingTransition() == null){
                return myPlace;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Petrinet has no start place");
    }

    public MyPlace getEndPlace(){
        for(MyPlace myPlace: myPlaces){
            if(myPlace.getOutgoingTransition() == null){
                return myPlace;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Petrinet has no end place");
    }


    public Collection<MyTransition> getMyTransitions() {
        return myTransitions;
    }

    public Collection<MyPlace> getMyPlaces() {
        return myPlaces;
    }

    public String getLabel() {
        return label;
    }
}
