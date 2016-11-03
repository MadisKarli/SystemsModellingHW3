package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.elements.Activity;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Joonas Papoonas on 26/10/2016.
 */
public class MyBPMNModel {

    // holding all the nodes, just like in our application model from HW2
    private Set<MyBPMNNode> nodes;
    // mySequenceFlows
    private Collection<MySequenceFlow> sequenceFlows;

    private Collection<MyCompoundTask> myCompoundTasks;

    private Collection<MyGateway> myGateways;





    //constructor
    public MyBPMNModel() {
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



}
