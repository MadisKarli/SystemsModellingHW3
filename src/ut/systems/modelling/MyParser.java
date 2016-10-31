package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.*;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;

import java.util.Set;

/**
 * Created by Philosoraptor on 31/10/2016.
 */
public class MyParser {




    protected  static MyBPMNModel getMyBPMNModel(BPMNDiagram diagram){

        MyBPMNModel myMyBPMNModel = new MyBPMNModel();

        //myMyBPMNModel.setNodes(diagram.getNodes());
        //myMyBPMNModel.setMyTasks(diagram.getActivities());
        //myMyBPMNModel.setMySequenceFlows(diagram.getFlows());
        //myMyBPMNModel.setSubProcesses(diagram.getSubProcesses());

        return myMyBPMNModel;
    }




    //TODO: ADD OTHER CONVERTERS HERE

    //converts BPMNDiagram nodes into our Node format
    protected Set<MyBPMNNode> getMyNodes (BPMNDiagram diagram){
        Set<BPMNNode> nodes = diagram.getNodes();
        Set<MyBPMNNode> myNodes = null;

        for ( BPMNNode element : nodes) {
            myNodes.add(convertBPMNNode2MyBPMNNode(element));
            System.out.println(element.toString());
        }

        return myNodes;
    }

    //TODO: actually this shit ain't workin' because i'm not sure what to add into our node model
    // the BPMNDiagram node has some weird parameters, check later what is useful and what is not
    protected MyBPMNNode convertBPMNNode2MyBPMNNode (BPMNNode node){
        MyBPMNNode myNode = null;
        return myNode;
    }
}
