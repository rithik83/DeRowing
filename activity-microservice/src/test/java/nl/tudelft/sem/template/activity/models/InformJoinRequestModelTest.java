package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InformJoinRequestModelTest {

    private InformJoinRequestModel model;

    private long activityId;

    private NetId owner;

    private Position position;

    @BeforeEach
    void setUp() {
        model = new InformJoinRequestModel();
        model.setOwner(owner);
        model.setActivityId(activityId);
        model.setPosition(position);
    }

    @Test
    void getActivityId() {
        assertEquals(activityId, model.getActivityId());
    }

    @Test
    void getOwner() {
        assertEquals(owner, model.getOwner());
    }

    @Test
    void getPosition() {
        assertEquals(position, model.getPosition());
    }

    @Test
    void setActivityId() {
        long activityId2 = 2;
        model.setActivityId(activityId2);
        assertEquals(activityId2, model.getActivityId());
    }

    @Test
    void setOwner() {
        NetId owner2 = new NetId("netId2");
        model.setOwner(owner2);
        assertEquals(owner2, model.getOwner());
    }

    @Test
    void setPosition() {
        Position position2 = Position.COX;
        model.setPosition(position2);
        assertEquals(position2, model.getPosition());
    }

    @Test
    void testEquals() {
        InformJoinRequestModel model2 = new InformJoinRequestModel();
        model2.setOwner(owner);
        model2.setActivityId(activityId);
        model2.setPosition(position);
        assertEquals(model, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertTrue(model.canEqual(new InformJoinRequestModel()));
    }

    @Test
    void testHashCode() {
        InformJoinRequestModel model2 = new InformJoinRequestModel();
        model2.setOwner(owner);
        model2.setActivityId(activityId);
        model2.setPosition(position);
        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "InformJoinRequestModel(activityId="
                + activityId + ", owner=" + owner + ", position=" + position + ")";
        assertEquals(expected, model.toString());
    }
}