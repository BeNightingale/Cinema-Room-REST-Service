package cinema.exception;

public class AuthorizationException extends IllegalAccessException {

    private final String error;

    public AuthorizationException() {
        super("The password is wrong!");
        this.error = "The password is wrong!";
    }

    public String getError() {
        return error;
    }
}
