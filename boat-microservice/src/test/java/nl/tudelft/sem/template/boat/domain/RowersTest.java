package nl.tudelft.sem.template.boat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowersTest {
    private NetId coxId;
    private NetId coachId;
    private NetId portId1;
    private NetId portId2;
    private NetId starboardId1;
    private NetId starboardId2;
    private NetId scullingId;

    private Map<Position, List<NetId>> sameRowers;
    private Map<Position, List<NetId>> differentRowers;
    private Map<Position, List<NetId>> currentRowers;
    private Rowers rowers;

    @BeforeEach
    void setup() {
        coxId = new NetId("cox");
        coachId = new NetId("coach");
        portId1 = new NetId("port1");
        portId2 = new NetId("port2");
        starboardId1 = new NetId("starboard1");
        starboardId2 = new NetId("starboard2");
        scullingId = new NetId("sculling");

        sameRowers = new HashMap<>();
        sameRowers.put(Position.COX, new ArrayList<>(List.of(coxId)));
        sameRowers.put(Position.COACH, new ArrayList<>(List.of(coachId)));
        sameRowers.put(Position.PORT, new ArrayList<>(List.of(portId1, portId2)));
        sameRowers.put(Position.STARBOARD, new ArrayList<>(List.of(starboardId1, starboardId2)));
        sameRowers.put(Position.SCULLING, new ArrayList<>(List.of(scullingId)));

        differentRowers = new HashMap<>();
        differentRowers.put(Position.COX, new ArrayList<>(List.of(coxId)));
        differentRowers.put(Position.COACH, new ArrayList<>(List.of(coachId)));
        differentRowers.put(Position.PORT, new ArrayList<>(List.of(portId1)));
        differentRowers.put(Position.STARBOARD, new ArrayList<>(List.of(starboardId1, starboardId2)));
        differentRowers.put(Position.SCULLING, new ArrayList<>(List.of(scullingId)));

        currentRowers = new HashMap<>();
        currentRowers.put(Position.COX, new ArrayList<>(List.of(coxId)));
        currentRowers.put(Position.COACH, new ArrayList<>(List.of(coachId)));
        currentRowers.put(Position.PORT, new ArrayList<>(List.of(portId1, portId2)));
        currentRowers.put(Position.STARBOARD, new ArrayList<>(List.of(starboardId1, starboardId2)));
        currentRowers.put(Position.SCULLING, new ArrayList<>(List.of(scullingId)));
    }

    @Test
    void constructorTest() {
        rowers = new Rowers(currentRowers);
        assertEquals(sameRowers, rowers.getCurrentRowers());
    }

    @Test
    void emptyConstructorTest() {
        Map<Position, List<NetId>> emptyRowers = new HashMap<>();
        for (Position position : Position.values()) {
            emptyRowers.put(position, new ArrayList<>());
        }

        rowers = new Rowers();
        assertEquals(emptyRowers, rowers.getCurrentRowers());
    }

    @Test
    void setCurrentRowersTest() {
        rowers = new Rowers(currentRowers);
        rowers.setCurrentRowers(differentRowers);
        assertEquals(differentRowers, rowers.getCurrentRowers());
    }

    @Test
    void equalsSameObjectTest() {
        rowers = new Rowers(currentRowers);
        assertEquals(currentRowers, rowers.getCurrentRowers());
    }

    @Test
    void equalsNotRowersTest() {
        Object other = new HashMap<Position, List<NetId>>();
        rowers = new Rowers(currentRowers);
        assertNotEquals(other, rowers.getCurrentRowers());
    }

    @Test
    void equalsNotSameValuesTest() {
        rowers = new Rowers(currentRowers);
        assertNotEquals(differentRowers, rowers.getCurrentRowers());
    }

    @Test
    void equalsSameValuesTest() {
        rowers = new Rowers(currentRowers);
        assertEquals(sameRowers, rowers.getCurrentRowers());
    }
}
