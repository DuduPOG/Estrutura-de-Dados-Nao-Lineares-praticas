
import java.util.ArrayList;
import java.util.Iterator;



public class No {

    private No pai;
    private No FE;
    private int value;
    private No FD;

    public No(No pai, int value){
        this.pai = pai;
        this.FE = null;
        this.value = value;
        this.FD = null;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public No getFE() {
        return FE;
    }

    public void setFE(No FE) {
        this.FE = FE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public No getFD() {
        return FD;
    }

    public void setFD(No FD) {
        this.FD = FD;
    }

    public Iterator<No> filhos(){
        ArrayList<No> filhos = new ArrayList<>();
        if (this.FE != null){
            filhos.add(this.FE);
        }
        if (this.FD != null){
            filhos.add(this.FD);
        }
        return filhos.iterator();
    }

}
