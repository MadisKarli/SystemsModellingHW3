package ut.systems.modelling;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Joonas Papoonas on 26/10/2016.
 */
public class MyCompoundTask extends MyTask {

    // holding all the nodes, just like in our application model from HW2
    private Set<MyBPMNNode> nodes;
    // mySequenceFlows
    private Collection<MySequenceFlow> sequenceFlows;


    //constructor
    public MyCompoundTask() {
        super("");
    }


    public BPMN convertToBPMN(){
        BPMN myBPMNModel = new BPMN();
        myBPMNModel.setNodes(nodes);
        myBPMNModel.setMySequenceFlows(sequenceFlows);

        return myBPMNModel;
    }


    //setters
    public void setNodes(Set<MyBPMNNode> nodes) {
        this.nodes = nodes;
    }

    public void setMySequenceFlows(Collection<MySequenceFlow> sequenceFlows) {
        this.sequenceFlows = sequenceFlows;
    }


    //getters
    public Set<MyBPMNNode> getNodes() {
        return nodes;
    }

    public Collection<MySequenceFlow> getSequenceFlows() {
        return sequenceFlows;
    }



}
