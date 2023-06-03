package cinema.model;


import java.util.List;
import java.util.Map;

public record Cinema(
        int totalRows,
        int totalColumns,
        Map<SeatCoordinates, Seat> availableSeats,
        List<Purchase> purchasedTicketsList
) {
}
