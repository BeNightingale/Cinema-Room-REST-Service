package cinema;

import cinema.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaService {
    private static final int CINEMA_ROWS_NUMBER = 9;
    private static final int CINEMA_COLUMNS_NUMBER = 9;

    public Map<SeatCoordinates, Seat> createFullCinemaSeatsList(int rowsNumber, int columnsNumber) {
        if (rowsNumber <= 0 || columnsNumber <= 0) {
            return Collections.emptyMap();
        }
        final Map<SeatCoordinates, Seat> cinemaHall = new TreeMap<>();
        for (int i = 1; i <= rowsNumber; i++) {
            for (int j = 1; j <= columnsNumber; j++) {
                cinemaHall.put(new SeatCoordinates(i, j), new Seat(i, j, i <= 4 ? 10 : 8));
            }
        }
        return cinemaHall;
    }

    public CinemaDto toCinemaDto(Cinema cinema) {
        final List<SeatDto> seatDtoList = new ArrayList<>(cinema.availableSeats().values()
                .stream()
                .filter(Seat::isAvailable)
                .map(this::toSeatDto)
                .toList());
        return new CinemaDto(cinema.totalRows(), cinema.totalColumns(), seatDtoList);
    }

    public SeatDto toSeatDto(Seat seat) {
        return new SeatDto(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

    public Purchase checkAvailabilityAndBuy(Cinema cinema, int row, int column) {
        if (row <= 0 || row > CINEMA_ROWS_NUMBER || column <= 0 || column > CINEMA_COLUMNS_NUMBER) {
            throw new IllegalArgumentException();
        }
        final Map<SeatCoordinates, Seat> cinemaHall = cinema.availableSeats();
        final SeatCoordinates chosenSeatCoordinates = new SeatCoordinates(row, column);
        final Seat chosenSeat = cinemaHall.get(chosenSeatCoordinates);
        if (chosenSeat.isAvailable()) {
            chosenSeat.setAvailable(false);
            final Purchase purchasedTicket = new Purchase(toSeatDto(chosenSeat));
            cinema.purchasedTicketsList().add(purchasedTicket);
            return purchasedTicket;
        } else {
            throw new IllegalStateException();
        }
    }

    public ReturnedTicket returnTicket(Cinema cinema, Identifier identifier) {
        if (cinema == null || identifier == null)
            throw new IllegalArgumentException();
        final Optional<Purchase> purchase = cinema.purchasedTicketsList().stream()
                .filter(p -> p.getIdentifier().getToken().equals(identifier.getToken()))
                .findFirst();
        final Purchase bought = purchase.orElse(null);
        if (bought != null) {
            final SeatDto seatDto = bought.seatDto();
            final SeatCoordinates seatCoordinates = new SeatCoordinates(seatDto.row(), seatDto.column());
            cinema.availableSeats().get(seatCoordinates).setAvailable(true);
            cinema.purchasedTicketsList().remove(bought);
            return new ReturnedTicket(seatDto);
        }
        throw new IllegalArgumentException();
    }
}
