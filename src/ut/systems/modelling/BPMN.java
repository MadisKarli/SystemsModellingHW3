package ut.systems.modelling;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.processmining.models.graphbased.directed.bpmn.elements.Activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Joonas Papoonas on 26/10/2016.
 */
public class BPMN {

    // holding all the nodes, just like in our application model from HW2
    private Set<MyBPMNNode> nodes;
    // mySequenceFlows
    private Collection<MySequenceFlow> sequenceFlows;

    private Collection<MyCompoundTask> myCompoundTasks;

    private Collection<MyGateway> myGateways;

    private Collection<MyEvent> myEvents;

    private Collection<MyTask> myTasks;


    //constructor
    public BPMN() {
    }







    //setters
    public void setNodes(Set<MyBPMNNode> nodes) {
        this.nodes = nodes;
    }


    public void setMySequenceFlows(Collection<MySequenceFlow> sequenceFlows) {
        this.sequenceFlows = sequenceFlows;
    }

    public void setMyCompoundTasks(Collection<MyCompoundTask> myCompundTasks) { this.myCompoundTasks = myCompundTasks; }

    public void setMyGateways(Collection<MyGateway> myGateways) { this.myGateways = myGateways; }

    //getters
    public Set<MyBPMNNode> getNodes() {
        return nodes;
    }


    public Collection<MySequenceFlow> getSequenceFlows() {
        return sequenceFlows;
    }


    public void setMyEvents(Collection<MyEvent> myEvents) {
        this.myEvents = myEvents;
    }

    public void setMyTasks(Collection<MyTask> myTasks) {
        this.myTasks = myTasks;
    }

    public MyBPMNNode getMyBPMNNode(String id){
        for(MyBPMNNode myBPMNNode : nodes){
            if (myBPMNNode.getId().equals(id)){
                return myBPMNNode;
            }
        }
        throw new ArrayIndexOutOfBoundsException("No node with that id found");
    }

    public boolean myEventInCompundTasks(String id){
        boolean isPresent = false;
        for(MyCompoundTask myCompoundTask: myCompoundTasks){
            for (MyBPMNNode myBPMNNode: myCompoundTask.getNodes()){
                if (myBPMNNode.getId().toString().equals(id)){
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }

    public MyEvent getStartEvent() {

        for(MyEvent myEvent: myEvents) {
            boolean myEventInTargets = false;

            if (!myEventInCompundTasks(myEvent.getId())) {
                for (MySequenceFlow mySequenceFlow : sequenceFlows) {

                    if (mySequenceFlow.getTgt().toString().equals(myEvent.getId().toString())) {
                        myEventInTargets = true;
                    }
                }

                if (!myEventInTargets) {
                    System.out.println("Start event is " + myEvent.getId().toString());
                    return myEvent;
                }
            }
        }

        throw new ArrayIndexOutOfBoundsException("Start event not found");
    }

    public MyEvent getEndEvent() {

        for(MyEvent myEvent: myEvents) {
            boolean myEventInSources = false;

            if (!myEventInCompundTasks(myEvent.getId())) {
                for (MySequenceFlow mySequenceFlow : sequenceFlows) {
                    if (mySequenceFlow.getSrc().toString().equals(myEvent.getId().toString())) {
                        myEventInSources = true;
                    }
                }

                if (!myEventInSources) {
                    System.out.println("End event is " + myEvent.getId().toString());
                    return myEvent;
                }
            }
        }

        throw new ArrayIndexOutOfBoundsException("End event not found");
    }

    public MyCompoundTask isKindOfCompound(MyBPMNNode mySequenceFlowSource) {
        for(MyCompoundTask myCompoundTask : myCompoundTasks){
            for(MyBPMNNode myBPMNNode : myCompoundTask.getNodes()){
                if(myBPMNNode.getId().equals(mySequenceFlowSource.getId())){
                    return myCompoundTask;
                }
            }
        }
        return null;
    }

    public MyGateway isKindOfGateway(MyBPMNNode mySequenceFlowSource){
        for(MyGateway myGateway : myGateways){
                if(myGateway.getId().equals(mySequenceFlowSource.getId())){
                    return myGateway;
                }
            }
        return null;
    }
}
