package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.*;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.bpmn.elements.Flow;
import org.processmining.plugins.bpmn.BpmnFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Philosoraptor on 31/10/2016.
 */
public class MyParser {




    protected static MyBPMNModel getMyBPMNModel(BPMNDiagram diagram){

        MyBPMNModel myMyBPMNModel = new MyBPMNModel();

        //going step by step here and adding stuff to the myBPMNModel thing
        myMyBPMNModel.setMySequenceFlows(getMySequenceFlows(diagram));
        //myMyBPMNModel.setNodes(getMyNodes(diagram));

        //myMyBPMNModel.setNodes(diagram.getNodes());
        //myMyBPMNModel.setMyTasks(diagram.getActivities());
        //myMyBPMNModel.setMySequenceFlows(diagram.getFlows());
        //myMyBPMNModel.setSubProcesses(diagram.getSubProcesses());



        Collection<Flow> flows = diagram.getFlows();
        return myMyBPMNModel;
    }




    //TODO: ADD OTHER CONVERTERS HERE

    /////////////////////////////////// NODE STUFF /////////////////////////////////////////////////
    //converts BPMNDiagram nodes into our Node format
    private static Set<MyBPMNNode> getMyNodes(BPMNDiagram diagram){
        Set<BPMNNode> nodes = diagram.getNodes();
        Set<MyBPMNNode> myNodes = new HashSet<MyBPMNNode>();

        for ( BPMNNode element : nodes) {
            MyBPMNNode myNode = convertBPMNNode2MyBPMNNode(element);
            myNodes.add(myNode);
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
}
