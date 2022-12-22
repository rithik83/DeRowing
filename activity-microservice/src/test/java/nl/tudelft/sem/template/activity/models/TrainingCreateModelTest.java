package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingCreateModelTest {

    private TrainingCreateModel model;

    private String name;

    private long startTime;

    private Type type;

    private long boatId;

    @BeforeEach
    void setUp() {
        name = "name";
        startTime = 123L;
        type = Type.C4;
        boatId = 123L;
        model = new TrainingCreateModel(name, startTime, Type.C4);
        model.setTrainingName(name);
        model.setStartTime(startTime);
        model.setType(type);
    }

    @Test
    void getTrainingName() {
        assertEquals(name, model.getTrainingName());
    }

    @Test
    void getStartTime() {
        assertEquals(startTime, model.getStartTime());
    }

    @Test
    void getType() {
        assertEquals(type, model.getType());
    }


    @Test
    void setTrainingName() {
        String name2 = "name2";
        model.setTrainingName(name2);
        assertEquals(name2, model.getTrainingName());
    }

    @Test
    void setStartTime() {
        long startTime2 = 2;
        model.setStartTime(startTime2);
        assertEquals(startTime2, model.getStartTime());
    }

    @Test
    void setType() {
        Type type2 = Type.PLUS4;
        model.setType(type2);
        assertEquals(type2, model.getType());
    }


    @Test
    void testEquals() {
        TrainingCreateModel model1 = new TrainingCreateModel(name, startTime, Type.C4);
        TrainingCreateModel model2 = new TrainingCreateModel(name, startTime, Type.C4);
        assertEquals(model1, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertFalse(model.canEqual(new Object()));
        assertTrue(model.canEqual(new TrainingCreateModel(name, startTime, Type.C4)));
    }

    @Test
    void testHashCode() {
        TrainingCreateModel model1 = new TrainingCreateModel(name, startTime, Type.C4);
        TrainingCreateModel model2 = new TrainingCreateModel(name, startTime, Type.C4);
        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "TrainingCreateModel(trainingName=" + name + ", startTime=" + startTime
                + ", type=" + type + ")";
        assertEquals(expected, model.toString());
    }
}