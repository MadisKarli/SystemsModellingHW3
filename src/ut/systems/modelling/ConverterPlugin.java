package ut.systems.modelling;

import org.jbpt.pm.bpmn.Bpmn;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.BPMNDiagramImpl;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

import java.util.Collection;
import java.util.Set;


@Plugin(
        name = "Converter BPMN-PN",
        parameterLabels = { "BPMNDiagram" },
        returnLabels = { "Petri-Net" },
        returnTypes = { Petrinet.class },
        userAccessible = true,
        help = "Convert a BPMN diagram into a Petri-Net"
)
public class ConverterPlugin {

    @UITopiaVariant(
            affiliation = "University of Tartu",
            author = "Name Surname",
            email = "name.surname@ut.ee"
    )
    @PluginVariant(variantLabel = "Convert BPMN into PN", requiredParameterLabels = {0})
    public static Petrinet optimizeDiagram(UIPluginContext context, BPMNDiagram diagram) {
        Petrinet pn = null;

        // !!! BTW why are there some starnge multiplicities between task and
        // MyBPMNModel in our application model (should be other way around imho)


        //MyBPMNModel myMyBPMNModel = MyParser.getMyBPMNModel(diagram);
        //MyPetriNet myPetriNet = MyConverter.getPN(myMyBPMNModel);

        //lets say i have a class called parser
        //with method getmybpmnmodeol
        //lets say we have class myconverter with method getPN

        //sth from practice session question
        //Transition t  = new Transition("", pn);
        //t.setInvisible(true);



        // did sth, maybe working
        MyBPMNModel myBPMNModel = MyParser.getMyBPMNModel(diagram);
        MyPetrinet myPetrinet = MyConverter.BPMNtoMyPetrinet(myBPMNModel);

        ///////////////TESTING //////////////////////////////////
/*
        Collection<MyTask> myTasks = myMyBPMNModel.getTasks();
        Set<MyBPMNNode> nodes = myMyBPMNModel.getNodes();
        Collection<MySequenceFlow> flows = myMyBPMNModel.getSequenceFlows();
        Collection<MyBPMNModel> subProcesses = myMyBPMNModel.getSubProcesses();

       for ( MyTask element : myTasks) {
            System.out.println(element.toString());
        }

        for ( MyBPMNNode element : nodes) {
            System.out.println(element.toString());
        }

        for ( MySequenceFlow element : flows) {
            System.out.println(element.toString());
        }

        for ( MyBPMNModel element : subProcesses) {
            System.out.println(element.toString());
        }*/


        ///////////////TESTING //////////////////////////////////







        //pn = MyConverter.getPN(myMyBPMNModel); // fill this object with data from myPNObject

        pn = new PetrinetImpl("myPetriNet");
        pn.addPlace("Start_place");


        return pn;
    }

    public static Petrinet shadow(){

        //TODO copy this thing back to where it was before
        //Do not know if there is any point here


        //Create a test BPMNDiagram to speed up testing and development
        BPMNDiagram diagram = new BPMNDiagramImpl("");
        Petrinet pn = null;


        pn = new PetrinetImpl("myPetriNet");
        pn.addPlace("Start_place");

        return pn;
    }
    public static void main(String args[]){
        //Main method for testing
        //Move these things back to optimizeDiagram later
        shadow();
    }
}
