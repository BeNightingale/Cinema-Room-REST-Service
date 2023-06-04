package cinema.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public record ReturnedTicket(@JsonGetter(value = "returned_ticket") Seat seat) {
}
