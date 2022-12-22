package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoinRequestModelTest {

    private JoinRequestModel model;

    private long activityId;

    private Position position;

    @BeforeEach
    void setUp() {
        model = new JoinRequestModel();
        activityId = 123L;
        position = Position.COACH;
        model.setActivityId(activityId);
        model.setPosition(position);
    }

    @Test
    void getActivityId() {
        assertEquals(activityId, model.getActivityId());
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
    void setPosition() {
        Position position2 = Position.COX;
        model.setPosition(position2);
        assertEquals(position2, model.getPosition());
    }

    @Test
    void testEquals() {
        JoinRequestModel model2 = new JoinRequestModel();
        model2.setActivityId(activityId);
        model2.setPosition(position);
        assertEquals(model, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertFalse(model.canEqual(new Object()));
        assertTrue(model.canEqual(new JoinRequestModel()));
    }

    @Test
    void testHashCode() {
        JoinRequestModel model2 = new JoinRequestModel();
        model2.setActivityId(activityId);
        model2.setPosition(position);
        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "JoinRequestModel(activityId=" + activityId + ", position=" + position + ")";
        assertEquals(expected, model.toString());
    }
}