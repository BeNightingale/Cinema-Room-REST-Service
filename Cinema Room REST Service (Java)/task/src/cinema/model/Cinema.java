package cinema.model;


import java.util.List;
import java.util.Map;

public record Cinema(
        int totalRows,
        int totalColumns,
        Map<SeatCoordinates, Seat> availableSeats, // the whole cinema hall; each seat is marked with a flag: available= false/true
        List<Purchase> purchasedTicketsList // sold tickets list
) {
}
