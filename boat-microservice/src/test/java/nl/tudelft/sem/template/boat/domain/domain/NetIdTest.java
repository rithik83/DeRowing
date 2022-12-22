package nl.tudelft.sem.template.boat.domain.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.sem.template.boat.domain.NetId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Objects;

public class NetIdTest {
    private NetId netId;

    @BeforeEach
    void setup() {
        netId = new NetId("user");
    }

    @Test
    void getNetIdValueTest() {
        assertEquals("user", netId.getNetIdValue());
    }

    @Test
    void setNetIdValueTest() {
        String newValue = "newUser";
        netId.setNetIdValue(newValue);
        assertEquals(newValue, netId.getNetIdValue());
    }

    @Test
    void equalsSameObjectTest() {
        assertEquals(netId, netId);
    }

    @Test
    void equalsNotNetIdTest() {
        Object other = "user";
        assertNotEquals(other, netId);
    }

    @Test
    void equalsDifferentValuesTest() {
        NetId netId1 = new NetId("user1");
        assertNotEquals(netId1, netId);
    }

    @Test
    void equalsSameValuesTest() {
        NetId netId1 = new NetId("user");
        assertEquals(netId1, netId);
    }

    @Test
    void hashCodeTest() {
        Object value = Objects.hash("user");
        assertEquals(value, netId.hashCode());
    }
}
