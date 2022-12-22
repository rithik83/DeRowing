package nl.tudelft.sem.template.activity.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

class UserJoinEventTest {

    private UserJoinEvent userJoinEvent;
    private NetId owner;
    private Position position;
    private long activityId;


    @BeforeEach
    void setup() {
        owner = new NetId("test");
        position = Position.COACH;
        activityId = 1;
        userJoinEvent = new UserJoinEvent(owner, position, activityId);
    }

    @Test
    void getOwner() {
        assertEquals(owner, userJoinEvent.getOwner());
    }

    @Test
    void getPosition() {
        assertEquals(position, userJoinEvent.getPosition());
    }

    @Test
    void getActivityId() {
        assertEquals(activityId, userJoinEvent.getActivityId());
    }

    @Test
    void testEquals() {
        UserJoinEvent userJoinEvent2 = new UserJoinEvent(owner, position, activityId);
        assertEquals(userJoinEvent, userJoinEvent2);
    }

    @Test
    void canEqual() {
        assertTrue(userJoinEvent.canEqual(new UserJoinEvent(owner, position, activityId)));
    }

    @Test
    void testHashCode() {
        UserJoinEvent userJoinEvent2 = new UserJoinEvent(owner, position, activityId);
        assertEquals(userJoinEvent.hashCode(), userJoinEvent2.hashCode());
    }

    @Test
    void testToString() {
        String string = "UserJoinEvent(owner=" + owner + ", position=" + position + ", activityId=" + activityId + ")";
        assertEquals(string, userJoinEvent.toString());
    }
}