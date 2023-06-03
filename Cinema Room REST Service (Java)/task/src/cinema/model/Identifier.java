package cinema.model;

import lombok.Getter;

import java.util.UUID;

public class Identifier {
    private UUID token;

    public Identifier(UUID uuid) {
        this.token = uuid;
    }

    public Identifier() {
        this.token = UUID.randomUUID();
    }

    public UUID getToken() {
        return token;
    }
}
