
import java.util.ArrayList;
import java.util.Iterator;



public class No {

    private No pai;
    private No FE;
    private int value;
    private No FD;
    private int FB;

    public No(No pai, int value){
        this.pai = pai;
        this.FE = null;
        this.value = value;
        this.FD = null;
        this.FB = 0;
    }

    public No getPai() {
        return this.pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public No getFE() {
        return this.FE;
    }

    public void setFE(No FE) {
        this.FE = FE;
    }
    
    public int getFB() {
        return this.FB;
    }

    public void setFB(int FB) {
        this.FB = FB;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public No getFD() {
        return this.FD;
    }

    public void setFD(No FD) {
        this.FD = FD;
    }

    public boolean hasLeft(){
        return this.FE != null;
    }

    public boolean hasRight(){
        return this.FD != null;
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
