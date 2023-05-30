package cinema.exception;

public class SeatNotAvailableException extends IllegalStateException {

    private final String error;

    public SeatNotAvailableException() {
        super("The ticket has been already purchased!");
        this.error = "The ticket has been already purchased!";
    }

    public String getError() {
        return error;
    }
}
