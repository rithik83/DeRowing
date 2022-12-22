package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBoatModelTest {

    private CreateBoatModel model;

    @BeforeEach
    void setUp() {
        model = new CreateBoatModel();
        model.setType(Type.C4);
    }

    @Test
    void getType() {
        assertEquals(Type.C4, model.getType());
    }

    @Test
    void setType() {
        model.setType(Type.PLUS4);
        assertEquals(Type.PLUS4, model.getType());
    }

    @Test
    void testEquals() {
        CreateBoatModel model = new CreateBoatModel();
        model.setType(Type.C4);
        CreateBoatModel model2 = new CreateBoatModel();
        model2.setType(Type.C4);
        CreateBoatModel model3 = new CreateBoatModel();
        model3.setType(Type.PLUS4);
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
    }

    @Test
    void canEqual() {
        CreateBoatModel model = new CreateBoatModel();
        model.setType(Type.C4);
        assertTrue(model.canEqual(new CreateBoatModel()));
    }

    @Test
    void testHashCode() {
        CreateBoatModel model = new CreateBoatModel();
        model.setType(Type.C4);
        CreateBoatModel model2 = new CreateBoatModel();
        model2.setType(Type.C4);
        CreateBoatModel model3 = new CreateBoatModel();
        model3.setType(Type.PLUS4);
        assertEquals(model.hashCode(), model2.hashCode());
        assertNotEquals(model.hashCode(), model3.hashCode());
    }

    @Test
    void testToString() {
        CreateBoatModel model = new CreateBoatModel();
        model.setType(Type.C4);
        CreateBoatModel model2 = new CreateBoatModel();
        model2.setType(Type.C4);
        assertEquals(model2.toString(), model.toString());
    }
}