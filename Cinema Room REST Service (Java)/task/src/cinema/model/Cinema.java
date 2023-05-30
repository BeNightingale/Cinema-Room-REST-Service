package cinema.model;


import java.util.Map;

public record Cinema(int totalRows, int totalColumns, Map<SeatCoordinates, Seat> availableSeats) {

}
