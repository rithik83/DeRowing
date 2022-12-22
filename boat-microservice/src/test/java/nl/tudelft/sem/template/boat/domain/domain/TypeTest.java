package nl.tudelft.sem.template.boat.domain.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.boat.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeTest {
    private Type type;

    @BeforeEach
    void setup() {
        type = Type.PLUS4;
    }

    @Test
    void simpleTest() {
        assertEquals(Type.valueOf("PLUS4"), type);
    }

    @Test
    void getValueTest() {
        assertEquals(2, type.getValue());
    }
}
