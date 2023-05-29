package cinema;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;
public class CinemaDto {

        private final int totalRows;
        private final int totalColumns;
        private final List<Seat> availableSeats;

        public CinemaDto(int totalRows, int totalColumns, List<Seat> availableSeats) {
                this.totalRows = totalRows;
                this.totalColumns = totalColumns;
                this.availableSeats = availableSeats;
        }

        @JsonGetter(value = "total_rows")
        public int getTotalRows() {
                return totalRows;
        }

        @JsonGetter(value = "total_columns")
        public int getTotalColumns() {
                return totalColumns;
        }

        @JsonGetter(value = "available_seats")
        public List<Seat> getAvailableSeats() {
                return availableSeats;
        }
}
