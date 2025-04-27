package cpc.uv.copsboot.user;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;
import java.util.UUID;

@Embeddable
public class AuthServerId {

    private UUID value;

    protected AuthServerId() {}

    public AuthServerId(UUID value) {
        Assert.notNull(value, "The AuthServerId value should not be null");
    }
    public UUID getValue() {
        return value;
    }
}
