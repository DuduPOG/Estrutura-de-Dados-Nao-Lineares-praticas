public class No {

    private No pai;
    private No FE;
    private int value;
    private No FD;

    public No(){
        this.pai = null;
        this.FE = null;
        this.value = 0;
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

}
