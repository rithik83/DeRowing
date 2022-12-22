package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ActivityCancelModelTest {

    private ActivityCancelModel model;

    @BeforeEach
    void setup() {
        model = new ActivityCancelModel(123L);
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
        ActivityCancelModel model1 = new ActivityCancelModel(123L);
        ActivityCancelModel model2 = new ActivityCancelModel(312L);
        Assertions.assertFalse(model1.equals(model2));
    }

    @Test
    void canEqual() {
        ActivityCancelModel model1 = new ActivityCancelModel(123L);
        ActivityCancelModel model2 = new ActivityCancelModel(123L);
        Assertions.assertTrue(model1.equals(model2));
    }

    @Test
    void testHashCode() {
        ActivityCancelModel model1 = new ActivityCancelModel(123L);
        ActivityCancelModel model2 = new ActivityCancelModel(123L);
        Assertions.assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        ActivityCancelModel model1 = new ActivityCancelModel(123L);
        ActivityCancelModel model2 = new ActivityCancelModel(123L);
        Assertions.assertEquals(model1.toString(), model2.toString());
    }
}