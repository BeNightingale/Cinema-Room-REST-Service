package cinema;

import cinema.exception.AuthorizationException;
import cinema.exception.BadCoordinatesException;
import cinema.exception.SeatNotAvailableException;
import cinema.exception.WrongTokenException;
import cinema.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController()
public class Controller {

    private final CinemaService service = new CinemaService(); // This bean is needed during creating Controller object, so it can't be injected here
    private final Cinema cinema = new Cinema(
            CinemaConstants.CINEMA_ROWS_NUMBER, CinemaConstants.CINEMA_COLUMNS_NUMBER,
            service.createFullCinemaSeatsList(),
            new ArrayList<>());

    @GetMapping(value = "/seats") // shows only available seats
    public CinemaDto getCinemaState() {
        return service.toCinemaDto(cinema);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> buyTicket(@RequestBody SeatCoordinates seatCoordinates) {
        if (seatCoordinates == null || seatCoordinates.row() == null || seatCoordinates.column() == null) {
            throw new BadCoordinatesException();
        }
        try {
            final Purchase soldTicket = service.checkAvailabilityAndBuy(
                    this.cinema, seatCoordinates.row(), seatCoordinates.column());
            return ResponseEntity.ok(soldTicket);
        } catch (IllegalArgumentException argEx) {
            throw new BadCoordinatesException();
        } catch (Exception e) {
            throw new SeatNotAvailableException();
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Identifier identifier) {
        try {
            return ResponseEntity.ok(service.returnTicket(this.cinema, identifier));
        } catch (IllegalArgumentException argEx) {
            throw new WrongTokenException();
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestParam(required = false) String password) throws AuthorizationException {
        if (!"super_secret".equals(password))
            throw new AuthorizationException();
        return ResponseEntity.ok(service.getStatistics(cinema));
    }
}