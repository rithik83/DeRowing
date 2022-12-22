package nl.tudelft.sem.template.boat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionTest {
    private Position position;

    @BeforeEach
    void setup() {
        position = Position.COX;
    }

    @Test
    void simpleTest() {
        assertEquals(Position.valueOf("COX"), position);
    }

    @Test
    void getValueTest() {
        assertEquals(1, position.getValue());
    }
}
