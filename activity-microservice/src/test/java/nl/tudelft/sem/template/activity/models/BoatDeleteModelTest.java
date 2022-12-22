package nl.tudelft.sem.template.activity.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoatDeleteModelTest {

    private BoatDeleteModel model;

    @BeforeEach
    void setup() {
        model = new BoatDeleteModel(123L);
    }

    @Test
    void getId() {
        Assertions.assertEquals(123L, model.getId());
    }

    @Test
    void setId() {
        model.setId(321L);
        Assertions.assertEquals(321L, model.getId());
    }

    @Test
    void testEquals() {
        BoatDeleteModel model1 = new BoatDeleteModel(321L);
        BoatDeleteModel model2 = new BoatDeleteModel(123L);
        Assertions.assertNotEquals(model1, model2);
    }

    @Test
    void canEqual() {
        BoatDeleteModel model1 = new BoatDeleteModel(123L);
        BoatDeleteModel model2 = new BoatDeleteModel(123L);
        Assertions.assertEquals(model1, model2);
    }

    @Test
    void testHashCode() {
        BoatDeleteModel model1 = new BoatDeleteModel(123L);
        BoatDeleteModel model2 = new BoatDeleteModel(123L);
        Assertions.assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        BoatDeleteModel model1 = new BoatDeleteModel(123L);
        BoatDeleteModel model2 = new BoatDeleteModel(123L);
        Assertions.assertEquals(model1.toString(), model2.toString());
    }
}