package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcceptRequestModelTest {

    private AcceptRequestModel model;

    private NetId netId;

    @BeforeEach
    void setup() {
        netId = new NetId("123");
        model = new AcceptRequestModel();
        model.setAccepted(false);
        model.setPosition(Position.COACH);
        model.setRequestee(netId);
        model.setActivityId(123L);
    }

    @Test
    void isAccepted() {
        Assertions.assertFalse(model.isAccepted());
    }

    @Test
    void getActivityId() {
        Assertions.assertEquals(123L, model.getActivityId());
    }

    @Test
    void getRequestee() {
        Assertions.assertEquals(netId, model.getRequestee());
    }

    @Test
    void getPosition() {
        Assertions.assertEquals(Position.COACH, model.getPosition());
    }

    @Test
    void setAccepted() {
        model.setAccepted(true);
        Assertions.assertTrue(model.isAccepted());
    }

    @Test
    void setActivityId() {
        model.setActivityId(321L);
        Assertions.assertEquals(321L, model.getActivityId());
    }

    @Test
    void setRequestee() {
        model.setRequestee(new NetId("123"));
        Assertions.assertEquals(new NetId("123"), model.getRequestee());
    }

    @Test
    void setPosition() {
        model.setPosition(Position.COX);
        Assertions.assertEquals(Position.COX, model.getPosition());
    }

    @Test
    void testEquals() {
        AcceptRequestModel model1 = new AcceptRequestModel();
        Assertions.assertNotEquals(model1, model);
    }

    @Test
    void canEqual() {
        AcceptRequestModel model1 = new AcceptRequestModel();
        AcceptRequestModel model2 = new AcceptRequestModel();
        Assertions.assertEquals(model1, model2);
    }

    @Test
    void testHashCode() {
        AcceptRequestModel model1 = new AcceptRequestModel();
        AcceptRequestModel model2 = new AcceptRequestModel();
        Assertions.assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        AcceptRequestModel model1 = new AcceptRequestModel();
        AcceptRequestModel model2 = new AcceptRequestModel();
        Assertions.assertEquals(model1.toString(), model2.toString());
    }
}