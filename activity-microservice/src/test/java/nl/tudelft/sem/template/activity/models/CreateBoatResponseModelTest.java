package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBoatResponseModelTest {


    @Test
    void getBoatId() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        assertEquals(1, model.getBoatId());
    }

    @Test
    void setBoatId() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        assertEquals(1, model.getBoatId());
    }

    @Test
    void testEquals() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        CreateBoatResponseModel model2 = new CreateBoatResponseModel();
        model2.setBoatId(1);
        CreateBoatResponseModel model3 = new CreateBoatResponseModel();
        model3.setBoatId(2);
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
    }

    @Test
    void canEqual() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        assertTrue(model.canEqual(new CreateBoatResponseModel()));
    }

    @Test
    void testHashCode() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        CreateBoatResponseModel model2 = new CreateBoatResponseModel();
        model2.setBoatId(1);
        CreateBoatResponseModel model3 = new CreateBoatResponseModel();
        model3.setBoatId(2);
        assertEquals(model.hashCode(), model2.hashCode());
        assertNotEquals(model.hashCode(), model3.hashCode());
    }

    @Test
    void testToString() {
        CreateBoatResponseModel model = new CreateBoatResponseModel();
        model.setBoatId(1);
        CreateBoatResponseModel model2 = new CreateBoatResponseModel();
        model2.setBoatId(1);
        assertEquals(model2.toString(), model.toString());
    }
}