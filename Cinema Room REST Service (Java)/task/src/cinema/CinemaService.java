package cinema;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class CinemaService {

    public List<Seat> createFullCinemaSeatsList(int rowsNumber, int columnsNumber) {
        if (rowsNumber <= 0 || columnsNumber <= 0) {
            return Collections.emptyList();
        }
        List<Seat> seatsList =  new ArrayList<>();
        for (int i = 1; i <= rowsNumber; i++) {
            for (int j = 1; j <= columnsNumber; j++) {
                Seat seat = new Seat(i, j);
                seatsList.add(seat);
            }
        }
        return seatsList;
    }

    public CinemaDto toCinemaDto(Cinema cinema) {
        return new CinemaDto(cinema.getTotalRows(), cinema.getTotalColumns(), cinema.getAvailableSeats());
    }
}
