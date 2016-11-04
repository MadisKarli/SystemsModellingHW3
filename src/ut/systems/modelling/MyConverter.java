package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyConverter {
    public static MyPetrinet BPMNtoMyPetrinet(MyBPMNModel myBPMNModel) {

        MyPetrinet myPetrinet = new MyPetrinet("myPetrinet");

        MyPlace myStartPlace = new MyPlace("myStartPlace");
        MyTransition myStartTransition = new MyTransition("myStartTransition");
        myStartPlace.setOutgoingMyTransition("myStartTransition");
        myStartTransition.setIncomingMyPlace("myStartPlace");//TODO should this be addArcP2T?
        myPetrinet.addPlace(myStartPlace);
        myPetrinet.addTransition(myStartTransition);


        MyPlace myEndPlace = new MyPlace("myEndPlace");
        MyTransition myEndTransition = new MyTransition("myEndTransition");
        myEndPlace.setIncomingMyTransition("myEndTransition");
        myEndTransition.setOutgoingMyPlace("myEndPlace");//TODO should this be addArcT2P?
        myPetrinet.addPlace(myEndPlace);
        myPetrinet.addTransition(myEndTransition);


        MyEvent myStartEvent = myBPMNModel.getStartEvent();


        MyEvent myEndEvent = myBPMNModel.getEndEvent();








        return myPetrinet;
    }
}
