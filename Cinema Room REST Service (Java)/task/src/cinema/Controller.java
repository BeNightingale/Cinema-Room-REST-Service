package cinema;

import cinema.exception.BadCoordinatesException;
import cinema.exception.SeatNotAvailableException;
import cinema.exception.WrongTokenException;
import cinema.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController()
public class Controller {

    private final CinemaService service = new CinemaService(); // ten bean jest potrzebny już w trakcie tworzenia obiektu Controllera, więc nie może być wstrzyknięty
    private final Cinema cinema = new Cinema(
            9, 9,
            service.createFullCinemaSeatsList(9, 9),
            new ArrayList<>());

    @GetMapping(value = "/seats")
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
            final ReturnedTicket returnedTicket = service.returnTicket(this.cinema, identifier);
            return ResponseEntity.ok(returnedTicket);
        } catch (IllegalArgumentException argEx) {
            throw new WrongTokenException();
        }
    }
}