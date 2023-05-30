package cinema;

import cinema.exception.BadCoordinatesException;
import cinema.exception.SeatNotAvailableException;
import cinema.model.Cinema;
import cinema.model.CinemaDto;
import cinema.model.SeatCoordinates;
import cinema.model.SeatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
public class Controller {

    private final CinemaService service = new CinemaService(); // ten bean jest potrzebny już w trakcie tworzenia obiektu Controllera, więc nie może być wstrzykniety
    private final Cinema cinema = new Cinema(
            9, 9,
            service.createFullCinemaSeatsList(9, 9));

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
            final SeatDto soldSeat = service.checkAvailabilityAndBuy(
                    this.cinema, seatCoordinates.row(), seatCoordinates.column());
            return ResponseEntity.ok(soldSeat);
        } catch (IllegalArgumentException argEx) {
            throw new BadCoordinatesException();
        } catch (Exception e) {
            throw new SeatNotAvailableException();
        }
    }
}