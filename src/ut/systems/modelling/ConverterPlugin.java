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


        MyBPMNModel myBPMNModel = MyParser.getMyBPMNModel(diagram);
        MyPetrinet myPetrinet = MyConverter.BPMNtoMyPetrinet(myBPMNModel);
        PetrinetImpl outputPetrinet = MyParser.getOuputPetrinet(myPetrinet);


        //pn = MyConverter.getPN(myMyBPMNModel); // fill this object with data from myPNObject


        return outputPetrinet;
    }
}
