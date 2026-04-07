import java.io.InvalidObjectException;

public class NoInexistente extends InvalidObjectException {
    public NoInexistente(String err){
        super(err);
    }
}
