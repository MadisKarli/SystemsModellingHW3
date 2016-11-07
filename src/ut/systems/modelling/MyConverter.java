
package ut.systems.modelling;

import java.util.*;

/**
 * Created by Joonas Papoonas on 4/11/2016.
 * uses rng for id's
 */
public class MyConverter {

    public static MyPetrinet BPMNtoMyPetrinet(BPMN myBPMNModel) {
        Random rng = new Random();
        int rngSize = 10000000;
        boolean first = true;
        boolean last = true;
        int counter = 0;
        MyPlace placeHolder = null;
        MyTransition transitionHolder = null;

        MyPetrinet outputPN = new MyPetrinet("outputPN");


        MyPlace startPlace = new MyPlace("startPlace");
        MyTransition startTransition = new MyTransition("startTransition");

        outputPN.addPlace(startPlace);
        outputPN.addTransition(startTransition);
        outputPN.addArcP2T(startTransition, startPlace);

        MyPlace endPlace = new MyPlace("endPlace");
        MyTransition endTransition = new MyTransition("endTransition");
        outputPN.addPlace(endPlace);
        outputPN.addTransition(endTransition);
        outputPN.addArcT2P(endTransition, endPlace);

        MyPlace place = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
        placeHolder = place;
        outputPN.addPlace(place);
        outputPN.addArcT2P(startTransition, place);


        MyEvent startEvent = myBPMNModel.getStartEvent();
        MyEvent endEvent = myBPMNModel.getEndEvent();

        Map<MyBPMNNode, MyTransition> myMap = new HashMap<>();

        myMap.put(startEvent, startTransition);
        myMap.put(endEvent, endTransition);

        Collection<MySequenceFlow> flows = myBPMNModel.getSequenceFlows();

        Set<MyTransition> xorSplit = new HashSet<>();
        Set<MyTransition> xorJoin = new HashSet<>();
        Map<MyTransition, BPMN> subProcesses = new HashMap<>();


        //22. - 57.
        for(MySequenceFlow mySequenceFlow : flows){

            MyBPMNNode src = mySequenceFlow.getSrc();
            MyBPMNNode tgt = mySequenceFlow.getTgt();

            //28. - 39.
            if(!myMap.containsKey(src)){
                MyCompoundTask myCompoundTask = myBPMNModel.isKindOfCompound(src);
                MyGateway myGateway = myBPMNModel.isKindOfGateway(src);
                MyTransition srcTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));

                //30. - 38.
                if(myCompoundTask != null) {
                    subProcesses.put(srcTransition, myCompoundTask.convertToBPMN());
                }else if(myGateway != null){
                    srcTransition = new MyTransition(src.getId());
                    outputPN.addTransition(srcTransition);
                    outputPN.addArcP2T(srcTransition, place);//not in sequence diagram
                    if(myGateway.getType().equals("XOR-Split")){
                        xorSplit.add(srcTransition);
                    }else if(myGateway.getType().equals("XOR-Join")){
                        xorJoin.add(srcTransition);
                    }
                }else{
                    //MyTransition myTransition = new MyTransition(src.getId());
                    outputPN.addTransition(srcTransition);
                    if(placeHolder == null){
                        place = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
                        outputPN.addArcP2T(srcTransition, placeHolder);//not in sequence diagram
                        transitionHolder = srcTransition;
                    }else{
                        outputPN.addArcP2T(srcTransition, placeHolder);//not in sequence diagram
                        transitionHolder = srcTransition;
                        placeHolder = null;
                    }

                }
                myMap.put(src, srcTransition);
            }


            //45. - 57.
            if(!myMap.containsKey(tgt)){
                MyCompoundTask myCompoundTask = myBPMNModel.isKindOfCompound(tgt);
                MyGateway myGateway = myBPMNModel.isKindOfGateway(tgt);
                MyTransition tgtTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));


                if(myCompoundTask != null) {
                    tgtTransition = new MyTransition(tgt.getId());
                    subProcesses.put(tgtTransition, myCompoundTask.convertToBPMN());
                }else if(myGateway != null){
                    tgtTransition = new MyTransition(tgt.getId());
                    outputPN.addTransition(tgtTransition);
                    outputPN.addArcT2P(tgtTransition, placeHolder);//not in sequence diagram
                    if(myGateway.getType().equals("XOR-Split")){
                        xorSplit.add(tgtTransition);
                    }else if(myGateway.getType().equals("XOR-Join")){
                        xorJoin.add(tgtTransition);
                    }
                }else{
                    //outputPN.addTransition(tgtTransition);
                    if(placeHolder == null){

                        place = new MyPlace("Place " + String.valueOf(rng.nextInt(rngSize)));
                        outputPN.addPlace(place);
                        outputPN.addArcT2P(transitionHolder, place);
                        placeHolder = place;
                        if(counter == flows.size()-1){
                            outputPN.addArcP2T(endTransition, placeHolder);
                        }
                    }else{
                        outputPN.addArcT2P(transitionHolder, placeHolder);

                        placeHolder = null;
                    }

                }
            }

            counter ++;
        }



        //58. - 66.
        for (MyTransition myTransition : xorSplit){

            MyTransition invisibleTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));
            outputPN.addTransition(invisibleTransition);

            outputPN.addArcP2T(invisibleTransition, myTransition.getIncomingPlaces().get(0));
            outputPN.addArcT2P(invisibleTransition, myTransition.getOutgoingPlaces().get(0));

            outputPN.removeArcT2P(myTransition, myTransition.getOutgoingPlaces().get(0));
        }

        //67. - 75.
        for (MyTransition myTransition : xorJoin){

            MyTransition invisibleTransition = new MyTransition("Transition " + String.valueOf(rng.nextInt(rngSize)));
            outputPN.addTransition(invisibleTransition);

            outputPN.addArcP2T(invisibleTransition, myTransition.getIncomingPlaces().get(0));
            outputPN.addArcT2P(invisibleTransition, myTransition.getOutgoingPlaces().get(0));

            outputPN.removeArcT2P(myTransition, myTransition.getOutgoingPlaces().get(0)); //TODO is this needed?, line 75.
        }






        for(MyTransition subProcessTransition: subProcesses.keySet()){


            MyPetrinet subPetrinet = BPMNtoMyPetrinet(subProcesses.get(subProcessTransition));

            MyPlace inPlace = subProcessTransition.getIncomingPlaces().get(0);
            MyPlace outPlace = subProcessTransition.getOutgoingPlaces().get(0);

            outputPN.removeTransition(subProcessTransition);

            //attachPetriNets
            MyPlace subStartPlace = subPetrinet.getStartPlace();
            MyPlace subEndPlace = subPetrinet.getEndPlace();
            MyTransition inTransition = new MyTransition("petrinet to subpetrinet transition");
            MyTransition outTransition = new MyTransition("subpetrinet to petrinet transition");

            outputPN.addTransition(inTransition);
            outputPN.addTransition(outTransition);


            outputPN.addArcP2T(inTransition, inPlace);
            outputPN.addArcT2P(inTransition, subStartPlace);

            outputPN.addArcP2T(outTransition, outPlace);
            outputPN.addArcT2P(outTransition, subEndPlace);

            for(MyTransition myTransition: subPetrinet.getTransitions()){
                outputPN.addTransition(myTransition);
            }

            for(MyPlace myPlace: subPetrinet.getPlaces()){
                outputPN.addPlace(myPlace);
            }
        }
        return outputPN;
    }
}