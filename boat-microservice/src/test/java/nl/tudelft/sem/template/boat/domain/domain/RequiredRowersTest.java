package nl.tudelft.sem.template.boat.domain.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.RequiredRowers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequiredRowersTest {
    private Map<Position, Integer> differentAmountOfPositions;
    private Map<Position, Integer> sameAmountOfPositions;
    private Map<Position, Integer> amountOfPositions;
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
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        assertEquals(sameAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void emptyRequiredRowers() {
        Map<Position, Integer> emptyRequiredRowers = new HashMap<>();
        for (Position position :  Position.values()) {
            emptyRequiredRowers.put(position, Integer.MIN_VALUE);
        }

        requiredRowers = new RequiredRowers();
        assertEquals(emptyRequiredRowers, requiredRowers.getAmountOfPositions());
    }

    @Test
    void setAmountOfPositionsTest() {
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        requiredRowers.setAmountOfPositions((HashMap<Position, Integer>) differentAmountOfPositions);
        assertEquals(differentAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsSameObjectTest() {
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        assertEquals(amountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsNotRowersTest() {
        Object other = new HashMap<Position, List<NetId>>();
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        assertNotEquals(other, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsNotSameValuesTest() {
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        assertNotEquals(differentAmountOfPositions, requiredRowers.getAmountOfPositions());
    }

    @Test
    void equalsSameValuesTest() {
        requiredRowers = new RequiredRowers((HashMap<Position, Integer>) amountOfPositions);
        assertEquals(sameAmountOfPositions, requiredRowers.getAmountOfPositions());
    }
}