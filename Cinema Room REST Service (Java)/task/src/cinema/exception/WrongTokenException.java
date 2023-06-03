package cinema.exception;

public class WrongTokenException extends IllegalArgumentException {

    private final String error;

    public WrongTokenException() {
        super("Wrong token!");
        this.error = "Wrong token!";
    }

    public String getError() {
        return error;
    }
}
