package ut.systems.modelling;

import org.processmining.models.graphbased.directed.ContainableDirectedGraphElement;
import org.processmining.models.graphbased.directed.bpmn.*;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.bpmn.elements.*;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Arc;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;
import org.processmining.plugins.bpmn.BpmnFlow;

import java.util.*;


/**
 * Created by Philosoraptor on 31/10/2016.
 */

public class MyParser {

    protected static PetrinetImpl getOuputPetrinet(MyPetrinet myPetrinet) {
        PetrinetImpl outputPetrinet = new PetrinetImpl("test");
        Collection<MyPlace> myPlaces = myPetrinet.getMyPlaces();
        MyPlace current = null;

        for (MyPlace myPlace : myPlaces) {
            if (myPlace.getId().equals("myStartPlace")) {
                current = myPlace;
            }
        }
        outputPetrinet = addToModel(current, outputPetrinet);

        return outputPetrinet;
    }


    private static PetrinetImpl addToModel(MyPlace current, PetrinetImpl outputPetrinet){
        //Tegelt seda try catch saasta saaks vähemaks kui esimene place ja transition eraldi lisada
        while (true) {

            //add current to output
            //avoid double adding
            Boolean a = true;
            for (Place p : outputPetrinet.getPlaces()) {
                if (p.getLabel().equals(current.getId())) {
                    a = false;
                }
            }
            if (a) {
                outputPetrinet.addPlace(current.getId());
            }

            //Add incoming transition and incoming place for it
            try {
                //Add incoming tranisition
                Boolean b = true;
                for (Transition t : outputPetrinet.getTransitions()) {
                    if (t.getLabel().equals(current.getIncomingTransition().getId())) {
                        b = false;
                    }}
                if(b){
                    outputPetrinet.addTransition(current.getIncomingTransition().getId());
                }

                //add incoming place for transition above
                for (Place p : outputPetrinet.getPlaces()) {

                    if (current.getId().equals(p.getLabel())) {
                        MyTransition incoming = current.getIncomingTransition();
                        for (Transition t : outputPetrinet.getTransitions()) {
                            if (incoming.getId().equals(t.getLabel())) {
                                outputPetrinet.addArc(t, p);
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            //Add outgoing transition and outgoing place for it
            try {
                //Add outgoing transition
                Boolean c = true;
                for (Transition t : outputPetrinet.getTransitions()) {
                    if (t.getLabel().equals(current.getOutgoingTransition().getId())) {
                        c = false;
                    }}
                if(c){
                    outputPetrinet.addTransition(current.getOutgoingTransition().getId());
                }
                //add outgoing place for transition above
                for (Place p : outputPetrinet.getPlaces()) {
                    if (current.getId().equals(p.getLabel())) {
                        MyTransition outgoing = current.getOutgoingTransition();
                        for (Transition t : outputPetrinet.getTransitions()) {
                            if (outgoing.getId().equals(t.getLabel())) {
                                outputPetrinet.addArc(p, t);

                                current = outgoing.getOutgoingPlaces().get(0); //TODO fixx here
                                //Make sure to add all out

                                    Boolean b = true;
                                    for (Place pb : outputPetrinet.getPlaces()) {
                                        if (pb.getLabel().equals(current.getId())) {
                                            b = false;
                                        }
                                    }
                                    if(b) {
                                        outputPetrinet.addPlace(current.getId());
                                    }
                                    for (Place np : outputPetrinet.getPlaces()) {
                                        if (current.getId().equals(np.getLabel())) {
                                            outputPetrinet.addArc(t, np);
                                        }
                                    }
                                }

                            }
                        }
                    }
            } catch (Exception e) {
                break;//End}
            }
        }
        return outputPetrinet;
    }


    protected static MyBPMNModel getMyBPMNModel(BPMNDiagram diagram) {

        MyBPMNModel myBPMNModel = new MyBPMNModel();

        //going step by step here and adding stuff to the myBPMNModel thing
        myBPMNModel.setNodes(getMyNodes(diagram));
        myBPMNModel.setMySequenceFlows(getMySequenceFlows(diagram));
        myBPMNModel.setMyCompoundTasks(getMyCompundTasks(diagram));
        myBPMNModel.setMyGateways(getMyGateways(diagram));
        myBPMNModel.setMyEvents(getMyEvents(diagram));
        myBPMNModel.setMyTasks(getMyTasks(diagram));


        return myBPMNModel;
    }

    /////////////////////////////////// NODE STUFF /////////////////////////////////////////////////
    //converts BPMNDiagram nodes into our Node format
    private static Set<MyBPMNNode> getMyNodes(BPMNDiagram diagram) {
        Set<BPMNNode> nodes = diagram.getNodes();
        Set<MyBPMNNode> myNodes = new HashSet<MyBPMNNode>();

        for (BPMNNode element : nodes) {
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
    private static MyBPMNNode convertBPMNNode2MyBPMNNode(BPMNNode node) {
        MyBPMNNode myNode = new MyBPMNNode(node.getId().toString());
        return myNode;
    }


    ////////////////////////////// SEQUENCEFLOW STUFF ////////////////////////////////////////
    private static Collection<MySequenceFlow> getMySequenceFlows(BPMNDiagram diagram) {
        Collection<Flow> flows = diagram.getFlows();
        Collection<MySequenceFlow> myFlows = new ArrayList<MySequenceFlow>();

        for (Flow element : flows) {
            myFlows.add(convertFlow2MySequenceFlow(element, diagram));
            //System.out.println(element.toString());
        }
        return myFlows;
    }

    //just inserting target id and source id to the sequenceflow object
    private static MySequenceFlow convertFlow2MySequenceFlow(Flow element, BPMNDiagram diagram) {
        MyBPMNModel myBPMNModelTemp = new MyBPMNModel();
        myBPMNModelTemp.setNodes(getMyNodes(diagram));

        MyBPMNNode srcNode = myBPMNModelTemp.getMyBPMNNode(element.getSource().getId().toString());
        MyBPMNNode tgtNode = myBPMNModelTemp.getMyBPMNNode(element.getTarget().getId().toString());


        MySequenceFlow myFlow = new MySequenceFlow(srcNode, tgtNode);
        return myFlow;
    }


/////////////////////// TASKS STUFF     ///////////////////////////////////////////////////


    private static Collection<MyTask> getMyTasks(BPMNDiagram diagram) {
        Collection<Activity> activities = diagram.getActivities();
        Collection<MyTask> myTasks = new ArrayList<MyTask>();

        for (Activity element : activities) {
            myTasks.add(convertActivity2MyTask(element));
            System.out.println(element.toString());
            //System.out.println(element.getId());
        }
        return myTasks;
    }

    private static MyTask convertActivity2MyTask(Activity element) {
        MyTask myTask = new MyTask(element.getId().toString());
        return myTask;
    }


/////////////////////////// COMPOUNDTASK STUFF //////////////////////////////////////////

    private static Collection<MyCompoundTask> getMyCompundTasks(BPMNDiagram diagram) {
        Collection<SubProcess> subProcesses = diagram.getSubProcesses();
        Collection<MyCompoundTask> myCompoundTasks = new ArrayList<MyCompoundTask>();

        for (SubProcess element : subProcesses) {
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

        for (ContainableDirectedGraphElement node : nodes) {
            myNodes.add(convertSubNode2MyBPMNNode(node));
        }

        for (Flow flow : flows) {
            myFlows.add(convertFlow2MySequenceFlow(flow, diagram));
        }
        return myCompoundTask;
    }


    private static MyBPMNNode convertSubNode2MyBPMNNode(ContainableDirectedGraphElement node) {
        MyBPMNNode myBPMNNode = new MyBPMNNode(node.toString());
        return myBPMNNode;
    }

////////////////////////////////// MYGATEWAY STUFF ////////////////////////////////////////////

    private static Collection<MyGateway> getMyGateways(BPMNDiagram diagram) {
        Collection<Gateway> gateways = diagram.getGateways();
        Collection<MyGateway> myGateways = new ArrayList<MyGateway>();

        for (Gateway element : gateways) {
            myGateways.add(convertGateway2MyGateway(element));
        }
        return myGateways;
    }

    private static MyGateway convertGateway2MyGateway(Gateway element) {
        MyGateway myGateway = new MyGateway(element.getId().toString(), element.toString());
        return myGateway;
    }


////////////////////////// MYEVENT STUFF //////////////////////////////////////////////////////


    private static Collection<MyEvent> getMyEvents(BPMNDiagram diagram) {
        Collection<Event> events = diagram.getEvents();
        Collection<MyEvent> myEvents = new ArrayList<MyEvent>();

        for (Event element : events) {
            myEvents.add(convertEvent2MyEvent(element));
            System.out.println(element.getId());
        }
        return myEvents;
    }

    private static MyEvent convertEvent2MyEvent(Event element) {
        MyEvent myEvent = new MyEvent(element.getId().toString());
        return myEvent;
    }


}
