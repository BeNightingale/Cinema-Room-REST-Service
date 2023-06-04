package cinema;

import cinema.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaService {


    /**
     * Method prepares a layout of the cinema hall as a map
     * @return a map, where key = coordinates of a seat, value = a seat object
     */
    public Map<SeatCoordinates, Seat> createFullCinemaSeatsList() {
        if (CinemaConstants.CINEMA_ROWS_NUMBER <= 0 || CinemaConstants.CINEMA_COLUMNS_NUMBER <= 0) {
            return Collections.emptyMap();
        }
        final Map<SeatCoordinates, Seat> cinemaHall = new TreeMap<>();
        for (int i = 1; i <= CinemaConstants.CINEMA_ROWS_NUMBER; i++) {
            for (int j = 1; j <= CinemaConstants.CINEMA_COLUMNS_NUMBER; j++) {
                cinemaHall.put(new SeatCoordinates(i, j), new Seat(i, j, i <= 4 ? 10 : 8));
            }
        }
        return cinemaHall;
    }

    /**
     * The method returns only available seats and total rows and columns numbers
     * @param cinema - object of the class Cinema that represents cinema hall and has data about sold tickets
     * @return cinemaDto - an object with a list only with available seats
     */
    public CinemaDto toCinemaDto(Cinema cinema) {
        final List<Seat> availableSeatList = new ArrayList<>(cinema.availableSeats().values()
                .stream()
                .filter(Seat::isAvailable)
                .toList());
        return new CinemaDto(cinema.totalRows(), cinema.totalColumns(), availableSeatList);
    }

    /**
     * The method checks if the determined by coordinates (row & column) seat is not sold
     * and - if it's available - marks the seat as unavailable and adds this purchase to the cinema's purchasedTicketsList
     *
     * @param cinema - object of the class Cinema that represents cinema hall and has data about sold tickets
     * @param row - determines row of the seat for which the ticket is being bought
     * @param column - determines column of the seat for which the ticket is being bought
     * @return purchase - object of the class Purchase with bought ticket identifier and seat data
     */
    public Purchase checkAvailabilityAndBuy(Cinema cinema, int row, int column) {
        if (row <= 0 || row > CinemaConstants.CINEMA_ROWS_NUMBER || column <= 0 ||
                column > CinemaConstants.CINEMA_COLUMNS_NUMBER)
            throw new IllegalArgumentException();

        final Map<SeatCoordinates, Seat> cinemaHall = cinema.availableSeats();
        final SeatCoordinates chosenSeatCoordinates = new SeatCoordinates(row, column);
        final Seat chosenSeat = cinemaHall.get(chosenSeatCoordinates);
        if (chosenSeat.isAvailable()) {
            chosenSeat.setAvailable(false);
            final Purchase purchasedTicket = new Purchase(chosenSeat);//toSeatDto(chosenSeat));
            cinema.purchasedTicketsList().add(purchasedTicket);
            return purchasedTicket;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method finds a seat corresponding to the ticket being returned, marks that seat as available
     * and deletes that purchase from the purchasedTicketsList in cinema object
     *
     * @param cinema - object of the class Cinema that represents cinema hall and has data about sold tickets
     * @param identifier - object that has a property with UUID of ticket being returned
     * @return returnedTicket - object with data of the seat corresponding to the returned ticket
     */
    public ReturnedTicket returnTicket(Cinema cinema, Identifier identifier) {
        if (cinema == null || identifier == null)
            throw new IllegalArgumentException();
        final Optional<Purchase> purchase = cinema.purchasedTicketsList().stream()
                .filter(p -> p.getIdentifier().getToken().equals(identifier.getToken()))
                .findFirst();
        final Purchase bought = purchase.orElse(null);
        if (bought != null) {
            final Seat seat = bought.seat();
            seat.setAvailable(true);
            cinema.purchasedTicketsList().remove(bought);
            return new ReturnedTicket(seat);
        }
        throw new IllegalArgumentException();
    }

    /**
     * The method returns information about tickets sale
     * @param cinema  - object of the class Cinema that represents cinema hall and has data about sold tickets
     * @return an object of a class Statistics with cinema data referring to sale (current income, seats available number, sold tickets number)
     */
    public Statistics getStatistics(Cinema cinema) {
        final int soldTicketsNumber = cinema.purchasedTicketsList() == null ? 0 : cinema.purchasedTicketsList().size();
        final int availableTicketsNumber = cinema.totalRows() * cinema.totalColumns() - soldTicketsNumber;
        return new Statistics(
                countCurrentIncome(cinema),
                availableTicketsNumber,
                soldTicketsNumber
        );
    }

    private long countCurrentIncome(Cinema cinema) {
        final List<Purchase> purchasedList = cinema.purchasedTicketsList();
        if (purchasedList == null || purchasedList.isEmpty())
            return 0L;
        return purchasedList.stream().map(p -> p.seat().getPrice())
                .mapToLong(x -> x)
                .sum();
    }
}
