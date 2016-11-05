
package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 */
public class MyConverter {
    public static MyPetrinet BPMNtoMyPetrinet(MyBPMNModel myBPMNModel) {

        MyPetrinet myOutputPN = new MyPetrinet("myOutputPN");

        MyPlace myStartPlace = new MyPlace("myStartPlace");
        MyTransition myStartTransition = new MyTransition("myStartTransition");
        myStartPlace.setOutgoingMyTransition("myStartTransition");
        myStartTransition.setIncomingMyPlace("myStartPlace");//TODO should this be addArcP2T?
        myOutputPN.addPlace(myStartPlace);
        myOutputPN.addTransition(myStartTransition);


        MyPlace myEndPlace = new MyPlace("myEndPlace");
        MyTransition myEndTransition = new MyTransition("myEndTransition");
        myEndPlace.setIncomingMyTransition("myEndTransition");
        myEndTransition.setOutgoingMyPlace("myEndPlace");//TODO should this be addArcT2P?
        myOutputPN.addPlace(myEndPlace);
        myOutputPN.addTransition(myEndTransition);


        MyEvent myStartEvent = myBPMNModel.getStartEvent();
        MyEvent myEndEvent = myBPMNModel.getEndEvent();

        Map<MyBPMNNode, MyTransition> myMap = new HashMap<>();
        
        myMap.put(myStartEvent, myStartTransition);
        myMap.put(myEndEvent, myEndTransition);

        Collection<MySequenceFlow> mySequenceFlows = myBPMNModel.getSequenceFlows();

        Set<MyTransition> myXORsplits = new HashSet<>();
        Set<MyTransition> myXORjoins = new HashSet<>();        
        Map<MyTransition, MyCompoundTask> mySubprocesses = new HashMap<>();
        
        for(MySequenceFlow mySequenceFlow : mySequenceFlows){
            MyPlace myPlace = new MyPlace("");
            myOutputPN.addPlace(myPlace);

            MyBPMNNode mySequenceFlowSource = mySequenceFlow.getSrc();
            MyBPMNNode mySequenceFlowTarget = mySequenceFlow.getTgt();

            if(!myMap.containsKey(mySequenceFlowSource)){
                MyCompoundTask myCompoundTask = myBPMNModel.isKindOfCompound(mySequenceFlowSource);
                MyGateway myGateway = myBPMNModel.isKindOfGateway(mySequenceFlowSource);

                //30. - 38.
                if(myCompoundTask != null) {
                    MyTransition myTransition = new MyTransition(mySequenceFlowSource.getId());
                    myOutputPN.addTransition(myTransition);
                    mySubprocesses.put(myTransition, myCompoundTask);
                }else if(myGateway != null){
                    MyTransition myTransition = new MyTransition(mySequenceFlowSource.getId());
                    myOutputPN.addTransition(myTransition);
                    if(myGateway.getType().equals("XOR-Split")){
                        myXORsplits.add(myTransition);
                    }else if(myGateway.getType().equals("XOR-Join")){
                        myXORjoins.add(myTransition);
                    }
                }else{
                    MyTransition myTransition = new MyTransition(mySequenceFlowSource.getId());
                    myOutputPN.addTransition(myTransition);
                }
            }
            
            
            
            
            
            
            
            
            
            
            
            
        }
        
        
        
        

















        











        return myOutputPN;
    }
}
