package ut.systems.modelling;

import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

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


        //MyBPMNModel myBPMNModel = Parser.getMyBPMNModel(diagram);
        //MyPetriNet myPetriNet = MyConverter.getPN(myBPMNModel);

        //lets say i have a class called parser
        //with method getmybpmnmodeol
        //lets say we have class myconverter with method getPN

        //sth from practice session question
        Transition t  = new Transition("", pn);
        t.setInvisible(true);


        //Set<BPMNNode> set = diagram.getNodes();



//        for (BPMNNode s : set) {
//            System.out.println(s.toString());
//        }






//        MyBPMNModel myBPMNModel = getMyBPMNModel(diagram);
//        pn = MyConverter.getPN(myBPMNModel); // fill this object with data from myPNObject

        pn = new PetrinetImpl("myPetriNet");
        pn.addPlace("Start_place");

        return pn;
    }
}
