
//package ArvoreBP;

import java.util.ArrayList;
import java.util.Iterator;

public class NoBP {

    private NoBP pai;
    private NoBP FE;
    private int value;
    private NoBP FD;

    public NoBP(NoBP pai, int value){
        this.pai = pai;
        this.FE = null;
        this.value = value;
        this.FD = null;
    }

    public NoBP getPai() {
        return pai;
    }

    public void setPai(NoBP pai) {
        this.pai = pai;
    }

    public NoBP getFE() {
        return FE;
    }

    public void setFE(NoBP FE) {
        this.FE = FE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NoBP getFD() {
        return FD;
    }

    public void setFD(NoBP FD) {
        this.FD = FD;
    }

    public boolean hasLeft(){
        return this.FE != null;
    }
    
    public boolean hasRight(){
        return this.FD != null;
    }

    public Iterator<NoBP> filhos(){
        ArrayList<NoBP> filhos = new ArrayList<>();
        if (this.FE != null){
            filhos.add(this.FE);
        }
        if (this.FD != null){
            filhos.add(this.FD);
        }
        return filhos.iterator();
    }

}
