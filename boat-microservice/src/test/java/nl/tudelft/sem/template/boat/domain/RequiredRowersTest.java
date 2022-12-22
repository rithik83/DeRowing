package nl.tudelft.sem.template.boat.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RequiredRowersTest {
    private Map<Position, Integer> differentAmountOfPositions, sameAmountOfPositions, amountOfPositions;
    private RequiredRowers requiredRowers;

    @BeforeEach
    void setup() {
        differentAmountOfPositions = new HashMap<>();
        differentAmountOfPositions.put(Position.COX, 1);
        differentAmountOfPositions.put(Position.COACH, 1);
        differentAmountOfPositions.put(Position.PORT, 1);
        differentAmountOfPositions.put(Position.STARBOARD, 2);
        differentAmountOfPositions.put(Position.SCULLING, 0);

        sameAmountOfPositions = new HashMap<>();
        sameAmountOfPositions.put(Position.COX, 1);
        sameAmountOfPositions.put(Position.COACH, 1);
        sameAmountOfPositions.put(Position.PORT, 2);
        sameAmountOfPositions.put(Position.STARBOARD, 2);
        sameAmountOfPositions.put(Position.SCULLING, 0);

        amountOfPositions = new HashMap<>();
        amountOfPositions.put(Position.COX, 1);
        amountOfPositions.put(Position.COACH, 1);
        amountOfPositions.put(Position.PORT, 2);
        amountOfPositions.put(Position.STARBOARD, 2);
        amountOfPositions.put(Position.SCULLING, 0);
    }

    @Test
    void constructorTest() {
        requiredRowers = new RequiredRowers(amountOfPositions);
        assertEquals(sameAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void emptyRequiredRowers () {
        Map<Position, Integer> emptyRequiredRowers = new HashMap<>();
        for (Position position :  Position.values())
            emptyRequiredRowers.put(position, Integer.MIN_VALUE);

        requiredRowers = new RequiredRowers();
        assertEquals(emptyRequiredRowers, requiredRowers.getAmountOfPositions());
    }

    @Test
    void setAmountOfPositionsTest() {
        requiredRowers = new RequiredRowers(amountOfPositions);
        requiredRowers.setAmountOfPositions(differentAmountOfPositions);
        assertEquals(differentAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsSameObjectTest() {
        requiredRowers = new RequiredRowers(amountOfPositions);
        assertEquals(amountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsNotRowersTest() {
        Object other = new HashMap<Position, List<NetId>>();
        requiredRowers = new RequiredRowers(amountOfPositions);
        assertNotEquals(other, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsNotSameValuesTest() {
        requiredRowers = new RequiredRowers(amountOfPositions);
        assertNotEquals(differentAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsSameValuesTest() {
        requiredRowers = new RequiredRowers(amountOfPositions);
        assertEquals(sameAmountOfPositions, requiredRowers.getAmountOfPositions());
    }
}
