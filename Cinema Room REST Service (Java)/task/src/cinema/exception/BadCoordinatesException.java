package cinema.exception;

public class BadCoordinatesException extends IllegalArgumentException {

    private final String error;

    public BadCoordinatesException() {
        super("The number of a row or a column is out of bounds!");
        this.error = "The number of a row or a column is out of bounds!";
    }

    public String getError() {
        return error;
    }
}
