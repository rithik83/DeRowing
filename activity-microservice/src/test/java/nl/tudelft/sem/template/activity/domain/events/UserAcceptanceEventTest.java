package nl.tudelft.sem.template.activity.domain.events;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserAcceptanceEventTest {

    private UserAcceptanceEvent userAcceptanceEvent;

    private boolean isAccepted;

    private Position position;

    private NetId eventRequester;

    @BeforeEach
    void setup() {
        isAccepted = true;
        position = Position.COACH;
        eventRequester = new NetId("test");
        userAcceptanceEvent = new UserAcceptanceEvent(isAccepted, position, eventRequester);
    }

    @Test
    void isAccepted() {
        UserAcceptanceEvent userAcceptanceEvent1 = new UserAcceptanceEvent(isAccepted, Position.COACH, eventRequester);
        assertEquals(isAccepted, userAcceptanceEvent1.isAccepted());
    }

    @Test
    void getPosition() {
        UserAcceptanceEvent userAcceptanceEvent1 = new UserAcceptanceEvent(isAccepted, Position.COACH, eventRequester);
        assertEquals(position, userAcceptanceEvent1.getPosition());
    }

    @Test
    void getEventRequester() {
        assertEquals(eventRequester, userAcceptanceEvent.getEventRequester());
    }

    @Test
    void setAccepted() {
        userAcceptanceEvent.setAccepted(false);
        assertFalse(userAcceptanceEvent.isAccepted());
    }

    @Test
    void setPosition() {
        userAcceptanceEvent.setPosition(Position.COX);
        assertEquals(Position.COX, userAcceptanceEvent.getPosition());
    }

    @Test
    void setEventRequester() {
        NetId newRequester = new NetId("new");
        userAcceptanceEvent.setEventRequester(newRequester);
        assertEquals(newRequester, userAcceptanceEvent.getEventRequester());
    }

    @Test
    void testEquals() {
        UserAcceptanceEvent same = new UserAcceptanceEvent(isAccepted, position, eventRequester);
        assertEquals(userAcceptanceEvent, same);
    }

    @Test
    void canEqual() {
        assertTrue(userAcceptanceEvent.canEqual(new UserAcceptanceEvent(isAccepted, position, eventRequester)));
    }

    @Test
    void testHashCode() {
        UserAcceptanceEvent same = new UserAcceptanceEvent(isAccepted, position, eventRequester);
        assertEquals(userAcceptanceEvent.hashCode(), same.hashCode());
    }

    @Test
    void testToString() {
        String expected = "UserAcceptanceEvent(isAccepted=" + isAccepted + ", position=" + position
                + ", eventRequester=" + eventRequester + ")";
        assertEquals(expected, userAcceptanceEvent.toString());
    }
}