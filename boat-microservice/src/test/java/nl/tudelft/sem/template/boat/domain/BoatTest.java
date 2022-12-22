package nl.tudelft.sem.template.boat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.boat.builders.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoatTest {

    private transient Director director;

    private NetId coxId;
    private NetId coachId;
    private NetId portId1, portId2;
    private NetId starboardId1, starboardId2;
    private NetId scullingId;

    private Type type;
    private Rowers rowers;
    private RequiredRowers requiredRowers;

    @BeforeEach
    public void setup() {
        type = Type.PLUS4;

        coxId = new NetId("cox");
        coachId = new NetId("coach");
        portId1 = new NetId("port1");
        portId2 = new NetId("port2");
        starboardId1 = new NetId("starboard1");
        starboardId2 = new NetId("starboard2");
        scullingId = new NetId("sculling");

        Map<Position, List<NetId>> currentRowers = new HashMap<>();
        currentRowers.put(Position.COX, new ArrayList<>(List.of(coxId)));
        currentRowers.put(Position.COACH, new ArrayList<>(List.of(coachId)));
        currentRowers.put(Position.PORT, new ArrayList<>(List.of(portId1, portId2)));
        currentRowers.put(Position.STARBOARD, new ArrayList<>(List.of(starboardId1, starboardId2)));
        currentRowers.put(Position.SCULLING, new ArrayList<>(List.of(scullingId)));
        rowers = new Rowers(currentRowers);

        Map<Position, Integer> amountOfPositions = new HashMap<>();
        amountOfPositions.put(Position.COX, 1);
        amountOfPositions.put(Position.COACH, 1);
        amountOfPositions.put(Position.PORT, 2);
        amountOfPositions.put(Position.STARBOARD, 2);
        amountOfPositions.put(Position.SCULLING, 0);
        requiredRowers = new RequiredRowers(amountOfPositions);

        director = new Director();
    }

    @Test
    void getTypeTest() {
        Boat boat = director.constructBoat(Type.PLUS4);
        assertEquals(Type.PLUS4, boat.getType());
    }

    @Test
    void setTypeTest() {
        Boat boat = director.constructBoat(Type.PLUS4);
        boat.setType(Type.C4);
        assertEquals(Type.C4, boat.getType());
    }

    @Test
    void addRowerToPosition() {
        Map<Position, List<NetId>> rowers = new HashMap<>();
        NetId netId = new NetId("a");
        List<NetId> list = new ArrayList<>();
        list.add(netId);
        rowers.put(Position.COX, list);
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        Boat boat = director.constructBoat(Type.PLUS4);
        boat.addRowerToPosition(Position.COX, netId);
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void removePosition() {
        HashMap<Position, Integer> requiredRowers = new HashMap<>();
        requiredRowers.put(Position.COX, 0);
        requiredRowers.put(Position.COACH, 1);
        requiredRowers.put(Position.PORT, 2);
        requiredRowers.put(Position.SCULLING, 0);
        requiredRowers.put(Position.STARBOARD, 2);
        Boat boat = director.constructBoat(Type.C4);
        boat.removePosition(Position.COX);
        assertEquals(requiredRowers, boat.getRequiredRowers());
    }

    @Test
    void canRemoveRower() {
        HashMap<Position, List<NetId>> rowers = new HashMap<>();
        rowers.put(Position.COX, new ArrayList<>());
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        NetId netId = new NetId();
        Boat boat = director.constructBoat(Type.PLUS4);
        boat.addRowerToPosition(Position.COX, netId);
        assertTrue(boat.removeRower(netId));
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void cannotRemoveRower() {
        Boat boat = director.constructBoat(Type.C4);
        NetId netId = new NetId("a");
        assertFalse(boat.removeRower(netId));
    }

    @Test
    void cannotAddRower() {
        Boat boat = director.constructBoat(Type.C4);
        assertFalse(boat.canRowerBeAdded(Position.SCULLING));
    }

    @Test
    void canAddRower() {
        Boat boat = director.constructBoat(Type.PLUS8);
        assertTrue(boat.canRowerBeAdded(Position.COX));
    }

    @Test
    void equalsSameObjectTest() {
        Boat boat = director.constructBoat(Type.PLUS4);
        Boat boat1 = director.constructBoat(Type.PLUS4);
        assertEquals(boat1, boat);
    }

    @Test
    void equalsNotBoatTest() {
        Object other = new Boat(Type.PLUS4);
        Boat boat = director.constructBoat(Type.PLUS4);
        assertNotEquals(other, boat);
    }

    @Test
    void equalsDifferentValueTest() {
        Boat boat = director.constructBoat(Type.C4);
        Boat boat1 = director.constructBoat(Type.PLUS4);
        assertNotEquals(boat1, boat);
    }
}