import java.io.InvalidObjectException;

public class NoBInexistente extends InvalidObjectException {
    public NoBInexistente(String err){
        super(err);
    }
}