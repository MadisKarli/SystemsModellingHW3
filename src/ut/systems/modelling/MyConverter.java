
package ut.systems.modelling;

import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 * uses rng for id's
 */
public class MyConverter {

    public static MyPetrinet BPMNtoMyPetrinet(MyBPMNModel myBPMNModel) {
        Random rng = new Random();
        int rngSize = 10000000;
        boolean first = true;
        boolean last = true;
        int counter = 0;
        MyPlace placeHolder = null;
        MyTransition transitionHolder = null;

        MyPetrinet myOutputPN = new MyPetrinet("myOutputPN");


        MyPlace myStartPlace = new MyPlace("myStartPlace");
        MyTransition myStartTransition = new MyTransition("myStartTransition");

        myOutputPN.addPlace(myStartPlace);
        myOutputPN.addTransition(myStartTransition);
        myOutputPN.addArcP2T(myStartTransition, myStartPlace);

        MyPlace myEndPlace = new MyPlace("myEndPlace");
        MyTransition myEndTransition = new MyTransition("myEndTransition");
        myOutputPN.addPlace(myEndPlace);
        myOutputPN.addTransition(myEndTransition);
        myOutputPN.addArcT2P(myEndTransition, myEndPlace);

        MyPlace place = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
        placeHolder = place;
        myOutputPN.addPlace(place);
        myOutputPN.addArcT2P(myStartTransition, place);


        MyEvent myStartEvent = myBPMNModel.getStartEvent();
        MyEvent myEndEvent = myBPMNModel.getEndEvent();

        Map<MyBPMNNode, MyTransition> myMap = new HashMap<>();

        myMap.put(myStartEvent, myStartTransition);
        myMap.put(myEndEvent, myEndTransition);

        Collection<MySequenceFlow> mySequenceFlows = myBPMNModel.getSequenceFlows();

        Set<MyTransition> myXORsplits = new HashSet<>();
        Set<MyTransition> myXORjoins = new HashSet<>();
        Map<MyTransition, MyBPMNModel> mySubprocesses = new HashMap<>();


        //22. - 57.
        for(MySequenceFlow mySequenceFlow : mySequenceFlows){



            MyBPMNNode src = mySequenceFlow.getSrc();
            MyBPMNNode tgt = mySequenceFlow.getTgt();

            if(first){

                first = false;
            }

            /*if(counter == mySequenceFlows.size()){
                myOutputPN.addArcP2T(myEndTransition, placeHolder);
            }*/



            //28. - 39.
            if(!myMap.containsKey(src)){
                MyCompoundTask myCompoundTask = myBPMNModel.isKindOfCompound(src);
                MyGateway myGateway = myBPMNModel.isKindOfGateway(src);
                MyTransition srcTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));
                //transitionHolder = srcTransition;

                //30. - 38.
                if(myCompoundTask != null) {
                    //srcTransition = new MyTransition(src.getId());
                    //myOutputPN.addTransition(srcTransition);
                    //myOutputPN.addArcP2T(srcTransition, place);//not in sequence diagram
                    mySubprocesses.put(srcTransition, myCompoundTask.convertToMyBPMNModel());
                }else if(myGateway != null){
                    srcTransition = new MyTransition(src.getId());
                    myOutputPN.addTransition(srcTransition);
                    myOutputPN.addArcP2T(srcTransition, place);//not in sequence diagram
                    if(myGateway.getType().equals("XOR-Split")){
                        myXORsplits.add(srcTransition);
                    }else if(myGateway.getType().equals("XOR-Join")){
                        myXORjoins.add(srcTransition);
                    }
                }else{
                    //MyTransition myTransition = new MyTransition(src.getId());
                    myOutputPN.addTransition(srcTransition);
                    if(placeHolder == null){
                        place = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
                        myOutputPN.addArcP2T(srcTransition, place);//not in sequence diagram
                        transitionHolder = srcTransition;
                    }else{
                        myOutputPN.addArcP2T(srcTransition, placeHolder);//not in sequence diagram
                        transitionHolder = srcTransition;
                        placeHolder = null;
                    }

                }
                myMap.put(src, srcTransition);
            }


            //MyTransition srcTransition = myMap.get(src);
            //myOutputPN.addArcT2P(srcTransition, place);




            //45. - 57.
            if(!myMap.containsKey(tgt)){
                MyCompoundTask myCompoundTask = myBPMNModel.isKindOfCompound(tgt);
                MyGateway myGateway = myBPMNModel.isKindOfGateway(tgt);
                MyTransition tgtTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));


                if(myCompoundTask != null) {
                    tgtTransition = new MyTransition(tgt.getId());
                    //myOutputPN.addTransition(tgtTransition);
                    //myOutputPN.addArcT2P(tgtTransition, place);//not in sequence diagram
                    mySubprocesses.put(tgtTransition, myCompoundTask.convertToMyBPMNModel());
                }else if(myGateway != null){
                    tgtTransition = new MyTransition(tgt.getId());
                    myOutputPN.addTransition(tgtTransition);
                    myOutputPN.addArcT2P(tgtTransition, place);//not in sequence diagram
                    if(myGateway.getType().equals("XOR-Split")){
                        myXORsplits.add(tgtTransition);
                    }else if(myGateway.getType().equals("XOR-Join")){
                        myXORjoins.add(tgtTransition);
                    }
                }else{
                    //myOutputPN.addTransition(tgtTransition);
                    if(placeHolder == null){

                        MyPlace place2 = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
                        myOutputPN.addPlace(place2);
                        myOutputPN.addArcT2P(transitionHolder, place2);
                        placeHolder = place2;
                        if(counter == mySequenceFlows.size()-1){
                            myOutputPN.addArcP2T(myEndTransition, placeHolder);
                        }
                    }else{
                        myOutputPN.addArcT2P(transitionHolder, placeHolder);

                        placeHolder = null;
                    }



                    //MyTransition myTransition = new MyTransition(tgt.getId());
                    //myOutputPN.addTransition(myTransition);
                    //myOutputPN.addArcT2P(myTransition, place);//not in sequence diagram
                }
                //myMap.put(tgt, tgtTransition);
            }

            //MyTransition tgtTransition = myMap.get(tgt);
            //myOutputPN.addArcP2T(tgtTransition, place);

            counter ++;
        }



        //58. - 66.
        for (MyTransition myTransition : myXORsplits){

            MyTransition invisibleTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));
            myOutputPN.addTransition(invisibleTransition);

            //out of bounds
            myOutputPN.addArcP2T(invisibleTransition, myTransition.getIncomingPlaces().get(0));
            myOutputPN.addArcT2P(invisibleTransition, myTransition.getOutgoingPlaces().get(0));

            myOutputPN.removeArcT2P(myTransition, myTransition.getOutgoingPlaces().get(0)); //TODO is this needed?, line 66.
        }

        //67. - 75.
        for (MyTransition myTransition : myXORjoins){

            MyTransition invisibleTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));
            myOutputPN.addTransition(invisibleTransition);

            myOutputPN.addArcP2T(invisibleTransition, myTransition.getIncomingPlaces().get(0));
            myOutputPN.addArcT2P(invisibleTransition, myTransition.getOutgoingPlaces().get(0));

            myOutputPN.removeArcT2P(myTransition, myTransition.getOutgoingPlaces().get(0)); //TODO is this needed?, line 75.
        }






        for(MyTransition subProcessTransition: mySubprocesses.keySet()){


            MyPetrinet subPetrinet = BPMNtoMyPetrinet(mySubprocesses.get(subProcessTransition));

            MyPlace inPlace = subProcessTransition.getIncomingPlaces().get(0);
            MyPlace outPlace = subProcessTransition.getOutgoingPlaces().get(0);

            myOutputPN.removeTransition(subProcessTransition);

            //attachPetriNets
            MyPlace subStartPlace = subPetrinet.getStartPlace();
            MyPlace subEndPlace = subPetrinet.getEndPlace();
            MyTransition inTransition = new MyTransition("petrinet to subpetrinet transition");
            MyTransition outTransition = new MyTransition("subpetrinet to petrinet transition");

            myOutputPN.addTransition(inTransition);
            myOutputPN.addTransition(outTransition);


            myOutputPN.addArcP2T(inTransition, inPlace);
            myOutputPN.addArcT2P(inTransition, subStartPlace);

            myOutputPN.addArcP2T(outTransition, outPlace);
            myOutputPN.addArcT2P(outTransition, subEndPlace);

            for(MyTransition myTransition: subPetrinet.getMyTransitions()){
                myOutputPN.addTransition(myTransition);
            }

            for(MyPlace myPlace: subPetrinet.getMyPlaces()){
                myOutputPN.addPlace(myPlace);
            }
        }
        return myOutputPN;
    }
}