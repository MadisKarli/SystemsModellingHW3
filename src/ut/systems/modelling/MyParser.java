package ut.systems.modelling;

import org.processmining.models.graphbased.directed.ContainableDirectedGraphElement;
import org.processmining.models.graphbased.directed.bpmn.*;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.bpmn.elements.*;
import org.processmining.plugins.bpmn.BpmnFlow;

import java.util.*;


/**
 * Created by Philosoraptor on 31/10/2016.
 */

public class MyParser {




    protected static MyBPMNModel getMyBPMNModel(BPMNDiagram diagram){

        MyBPMNModel myBPMNModel = new MyBPMNModel();

        //going step by step here and adding stuff to the myBPMNModel thing

        //myMyBPMNModel.setNodes(getMyNodes(diagram)); //does not work

        //myMyBPMNModel.setNodes(diagram.getNodes());
        //myMyBPMNModel.setMyTasks(getMyTasks(diagram));


        myBPMNModel.setMySequenceFlows(getMySequenceFlows(diagram));
        myBPMNModel.setMyCompoundTasks(getMyCompundTasks(diagram));
        myBPMNModel.setMyGateways(getMyGateways(diagram));
        myBPMNModel.setMyEvents(getMyEvents(diagram));




        //Collection<Flow> flows = diagram.getFlows();
        return myBPMNModel;
    }

    /////////////////////////////////// NODE STUFF /////////////////////////////////////////////////
    //converts BPMNDiagram nodes into our Node format
    private static Set<MyBPMNNode> getMyNodes(BPMNDiagram diagram){
        Set<BPMNNode> nodes = diagram.getNodes();
        Set<MyBPMNNode> myNodes = new HashSet<MyBPMNNode>();

        for ( BPMNNode element : nodes) {
            MyBPMNNode myNode = convertBPMNNode2MyBPMNNode(element);
            myNodes.add(myNode); // fucken fails here. np exception
            //element.getLabel();
            //System.out.println(element.toString());
        }
        return myNodes;
    }

    //TODO: actually this shit ain't workin' because i'm not sure what to add into our node model
    //EDIT: maybe just ID is enough?!
    //EDITEDIT: shit, we gotta maybe somehow extend the MyNode class so that we could instead of
    //  nodes create tasks-events-gateways. Make node abstract and make others extend MyNode or some shit
    //Also how gotta look into how to extract if BPMNNode is a task, event or a gateway
    //  since there is no 'getTasks' method for BPMNNode imo
    // the BPMNDiagram node has some weird parameters, check later what is useful and what is not
    private static MyBPMNNode convertBPMNNode2MyBPMNNode(BPMNNode node){
        MyBPMNNode myNode = new MyBPMNNode(node.getId().toString());
        return myNode;
    }




    

////////////////////////////// SEQUENCEFLOW STUFF ////////////////////////////////////////
    private static Collection<MySequenceFlow> getMySequenceFlows(BPMNDiagram diagram){
        Collection<Flow> flows = diagram.getFlows();
        Collection<MySequenceFlow> myFlows = new ArrayList<MySequenceFlow>();

        for (Flow element : flows){
            myFlows.add(convertFlow2MySequenceFlow(element));
            System.out.println(element.toString());
        }
        return myFlows;
    }

    //just inserting target id and source id to the sequenceflow object
    private static MySequenceFlow convertFlow2MySequenceFlow(Flow element) {
        MySequenceFlow myFlow = new MySequenceFlow(element.getSource().getId().toString(),
                element.getTarget().getId().toString());
        return myFlow;
    }




/////////////////////// TASKS STUFF     ///////////////////////////////////////////////////


    private static Collection<MyTask> getMyTasks(BPMNDiagram diagram){
        Collection<Activity> activities = diagram.getActivities();
        Collection<MyTask> myTasks = new ArrayList<MyTask>();

        for (Activity element : activities){
            myTasks.add(convertActivity2MyTask(element));
            System.out.println(element.toString());
        }
        return myTasks;
    }

    private static MyTask convertActivity2MyTask(Activity element) {
        MyTask myTask = new MyTask(element.getId().toString());
        return myTask;
    }


/////////////////////////// COMPOUNDTASK STUFF //////////////////////////////////////////

    private static Collection<MyCompoundTask> getMyCompundTasks(BPMNDiagram diagram){
        Collection<SubProcess> subProcesses = diagram.getSubProcesses();
        Collection<MyCompoundTask> myCompoundTasks = new ArrayList<MyCompoundTask>();

        for (SubProcess element : subProcesses){
            myCompoundTasks.add(convertSubProcess2MyCompoundTask(element, diagram));
            System.out.println(element.toString());
        }
        return myCompoundTasks;
    }

    private static MyCompoundTask convertSubProcess2MyCompoundTask(SubProcess element, BPMNDiagram diagram) {
        Set<ContainableDirectedGraphElement> nodes = element.getChildren();
        Collection<Flow> flows = diagram.getFlows(element);
        MyCompoundTask myCompoundTask = new MyCompoundTask();
        Set<MyBPMNNode> myNodes = new HashSet<MyBPMNNode>();
        Collection<MySequenceFlow> myFlows = new ArrayList<MySequenceFlow>();

        myCompoundTask.setNodes(myNodes);
        myCompoundTask.setMySequenceFlows(myFlows);

        for (ContainableDirectedGraphElement node : nodes){
            myNodes.add(convertSubNode2MyBPMNNode(node));
        }

        for (Flow flow : flows){
            myFlows.add(convertFlow2MySequenceFlow(flow));
        }
        return myCompoundTask;
    }


    private static MyBPMNNode convertSubNode2MyBPMNNode(ContainableDirectedGraphElement node) {
        MyBPMNNode myBPMNNode = new MyBPMNNode(node.toString());
        return myBPMNNode;
    }

////////////////////////////////// MYGATEWAY STUFF ////////////////////////////////////////////

    private static Collection<MyGateway> getMyGateways(BPMNDiagram diagram){
        Collection<Gateway> gateways = diagram.getGateways();
        Collection<MyGateway> myGateways = new ArrayList<MyGateway>();

        for (Gateway element : gateways){
            myGateways.add(convertGateway2MyGateway(element));
        }
        return myGateways;
    }

    private static MyGateway convertGateway2MyGateway(Gateway element) {
        MyGateway myGateway = new MyGateway(element.getId().toString(), element.toString());
        return myGateway;
    }


////////////////////////// MYEVENT STUFF //////////////////////////////////////////////////////


    private static Collection<MyEvent> getMyEvents(BPMNDiagram diagram){
        Collection<Event> events = diagram.getEvents();
        Collection<MyEvent> myEvents = new ArrayList<MyEvent>();

        for (Event element : events){
            myEvents.add(convertEvent2MyEvent(element));
        }
        return myEvents;
    }

    private static MyEvent convertEvent2MyEvent(Event element) {

        MyEvent myEvent = new MyEvent(element.getId().toString());

        return myEvent;
    }









}
