package cinema.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public record SeatCoordinates(Integer row, Integer column) implements Comparable<SeatCoordinates> {

    @Override
    public int compareTo(SeatCoordinates sc) {
        if (this.row.compareTo(sc.row) == 0) {
            return this.column.compareTo(sc.column);
        }
        return this.row.compareTo(sc.row);
    }
}
