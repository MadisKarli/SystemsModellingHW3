package ut.systems.modelling;

import org.processmining.models.graphbased.directed.petrinet.Petrinet;

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
}
