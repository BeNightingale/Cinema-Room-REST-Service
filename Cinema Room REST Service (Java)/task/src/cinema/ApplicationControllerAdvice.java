package cinema;

import cinema.exception.AuthorizationException;
import cinema.exception.BadCoordinatesException;
import cinema.exception.SeatNotAvailableException;
import cinema.exception.WrongTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BadCoordinatesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadCoordinatesException> handleBadCoordinates(BadCoordinatesException ex) {
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(SeatNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SeatNotAvailableException> handleSeatNotAvailable(SeatNotAvailableException sEx) {
        return ResponseEntity.badRequest().body(sEx);
    }

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WrongTokenException> handleWrongToken(WrongTokenException wEx) {
        return ResponseEntity.badRequest().body(wEx);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AuthorizationException> handleBadPassword(AuthorizationException autEx) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(autEx);
    }
}
