package ut.systems.modelling;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Joonas Papoonas on 26/10/2016.
 */
public class MyBPMNModel {

    // holding all the nodes, just like in our application model from HW2
    private Set<MyBPMNNode> nodes;
    // should be myTasks
    private Collection<myTask> tasks;
    // mySequenceFlows
    private Collection<MySequenceFlow> sequenceFlows;
    // for later managing subprocesses
    private Collection<MyBPMNModel> subProcesses;





    //constructor
    public MyBPMNModel() {
    }







    //setters
    public void setNodes(Set<MyBPMNNode> nodes) {
        this.nodes = nodes;
    }

    public void setMyTasks(Collection<myTask> tasks) {
        this.tasks = tasks;
    }

    public void setMySequenceFlows(Collection<MySequenceFlow> sequenceFlows) {
        this.sequenceFlows = sequenceFlows;
    }

    public void setSubProcesses(Collection<MyBPMNModel> subProcesses) {
        this.subProcesses = subProcesses;
    }

    //getters
    public Set<MyBPMNNode> getNodes() {
        return nodes;
    }

    public Collection<myTask> getTasks() {
        return tasks;
    }

    public Collection<MySequenceFlow> getSequenceFlows() {
        return sequenceFlows;
    }

    public Collection<MyBPMNModel> getSubProcesses() {
        return subProcesses;
    }
}
