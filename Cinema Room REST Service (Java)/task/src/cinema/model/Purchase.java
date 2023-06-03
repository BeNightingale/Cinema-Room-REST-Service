package cinema.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public final class Purchase {

    @JsonUnwrapped
    private Identifier identifier;
    @JsonProperty
    private SeatDto seatDto;

    public Purchase() {
    }

    public Purchase(SeatDto seatDto) {
        this.identifier = new Identifier();
        this.seatDto = seatDto;
    }

    @JsonGetter(value = "ticket")
    public SeatDto seatDto() {
        return seatDto;
    }

    @JsonGetter(value = "token")
    public Identifier getIdentifier() {
        return identifier;
    }
}
