package cinema.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public final class Purchase {

    @JsonUnwrapped
    private Identifier identifier;
    @JsonProperty
    private Seat seat;

    public Purchase() {
    }

    public Purchase(Seat seat) {
        this.identifier = new Identifier();
        this.seat = seat;
    }

    @JsonGetter(value = "ticket")
    public Seat seat() {
        return seat;
    }

    @JsonGetter(value = "token")
    public Identifier getIdentifier() {
        return identifier;
    }
}
