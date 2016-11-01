package ut.systems.modelling;


/**
 * @(#) MySequenceFlow.java
 */

public class MySequenceFlow{

    private String src;
    private String tgt;


    public MySequenceFlow(String src, String tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    public String getSrc() {
        return src;
    }

    public String getTgt() {
        return tgt;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }
}
