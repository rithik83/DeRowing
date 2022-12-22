package nl.tudelft.sem.template.activity.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class BoatChangeEventTest {

    private BoatChangeEvent boatChangeEvent;

    long boatId;

    Position position;

    NetId acceptee;

    @BeforeEach
    void setup() {
        boatId = 1L;
        position = Position.COACH;
        acceptee = new NetId("123");
        boatChangeEvent = new BoatChangeEvent(boatId, position, acceptee);
    }

    @Test
    void getBoatId() {
        assertEquals(boatId, boatChangeEvent.getBoatId());
    }

    @Test
    void setBoatId() {
        boatChangeEvent.setBoatId(2L);
        assertEquals(2L, boatChangeEvent.getBoatId());
    }

    @Test
    void getPosition() {
        assertEquals(position, boatChangeEvent.getPosition());
    }

    @Test
    void setPosition() {
        boatChangeEvent.setPosition(Position.COX);
        assertEquals(Position.COX, boatChangeEvent.getPosition());
    }

    @Test
    void getAcceptee() {
        assertEquals(acceptee, boatChangeEvent.getAcceptee());
    }

    @Test
    void setAcceptee() {
        boatChangeEvent.setAcceptee(new NetId("321"));
        assertEquals(new NetId("321"), boatChangeEvent.getAcceptee());
    }
}