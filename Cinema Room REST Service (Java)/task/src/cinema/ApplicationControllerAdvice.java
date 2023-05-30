package cinema;

import cinema.exception.BadCoordinatesException;
import cinema.exception.SeatNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BadCoordinatesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadCoordinates(BadCoordinatesException ex) {
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(SeatNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleSeatNotAvailable(SeatNotAvailableException sEx) {
        return ResponseEntity.badRequest().body(sEx);
    }
}
