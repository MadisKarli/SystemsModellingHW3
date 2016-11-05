package ut.systems.modelling;


/**
 * @(#) MySequenceFlow.java
 */

public class MySequenceFlow{

    private MyBPMNNode src;
    private MyBPMNNode tgt;


    public MySequenceFlow(MyBPMNNode src, MyBPMNNode tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    public MyBPMNNode getSrc() {
        return src;
    }

    public MyBPMNNode getTgt() {
        return tgt;
    }

    public void setSrc(MyBPMNNode src) {
        this.src = src;
    }

    public void setTgt(MyBPMNNode tgt) {
        this.tgt = tgt;
    }
}
