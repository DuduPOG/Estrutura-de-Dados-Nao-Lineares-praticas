import java.util.ArrayList;
import java.util.Iterator;

public class NoRB {

    private NoRB pai;
    private NoRB FE;
    private NoRB FD;
    private int value;
    private Cores cor;

    public enum Cores{

        V("Vermelho"),
        P("Preto");

        private final String cor;

        private Cores(String cor) {
            this.cor = cor;
        }

        public String getCor() {
            return this.cor;
        }
    }

    public NoRB(NoRB pai, int value){
        this.pai = pai;
        this.FE = null;
        this.value = value;
        this.FD = null;
        this.cor = Cores.V;
    }

    public NoRB getPai() {
        return this.pai;
    }

    public void setPai(NoRB pai) {
        this.pai = pai;
    }

    public NoRB getFE() {
        return this.FE;
    }

    public void setFE(NoRB FE) {
        this.FE = FE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NoRB getFD() {
        return this.FD;
    }

    public void setFD(NoRB FD) {
        this.FD = FD;
    }

    public void setCor(Cores cor){
        this.cor = cor;
    }

    public Cores getCor(){
        return this.cor;
    }

    public boolean hasLeft(){
        return this.FE != null;
    }
    
    public boolean hasRight(){
        return this.FD != null;
    }

    public Iterator<NoRB> filhos(){
        ArrayList<NoRB> filhos = new ArrayList<>();
        if (this.FE != null){
            filhos.add(this.FE);
        }
        if (this.FD != null){
            filhos.add(this.FD);
        }
        return filhos.iterator();
    }

}
