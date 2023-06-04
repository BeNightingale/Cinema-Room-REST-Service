package cinema.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public record CinemaDto(@JsonGetter(value = "total_rows") int totalRows,
                        @JsonGetter(value = "total_columns") int totalColumns,
                        @JsonGetter(value = "available_seats") List<Seat> availableSeats) {

}
