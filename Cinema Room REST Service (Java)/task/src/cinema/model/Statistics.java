package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {

    @JsonProperty("current_income")
    private final long currentIncome;
    @JsonProperty("number_of_available_seats")
    private final int availableSeatsNumber;
    @JsonProperty(value = "number_of_purchased_tickets")
    private final int purchasedTicketsNumber;

    public Statistics(long currentIncome, int availableSeatsNumber, int purchasedTicketsNumber) {
        this.currentIncome = currentIncome;
        this.availableSeatsNumber = availableSeatsNumber;
        this.purchasedTicketsNumber = purchasedTicketsNumber;
    }
}
