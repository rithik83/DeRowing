package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingFindModelTest {

    private TrainingFindModel trainingFindModel;

    private String trainingName;

    private Position preferredPosition;

    @BeforeEach
    void setUp() {
        trainingName = "trainingName";
        preferredPosition = Position.COX;
        trainingFindModel = new TrainingFindModel(trainingName, preferredPosition);
    }

    @Test
    void getTrainingName() {
        assertEquals(trainingName, trainingFindModel.getTrainingName());
    }

    @Test
    void getPreferredPosition() {
        assertEquals(preferredPosition, trainingFindModel.getPreferredPosition());
    }

    @Test
    void setTrainingName() {
        String newTrainingName = "newTrainingName";
        trainingFindModel.setTrainingName(newTrainingName);
        assertEquals(newTrainingName, trainingFindModel.getTrainingName());
    }

    @Test
    void setPreferredPosition() {
        Position newPreferredPosition = Position.COX;
        trainingFindModel.setPreferredPosition(newPreferredPosition);
        assertEquals(newPreferredPosition, trainingFindModel.getPreferredPosition());
    }

    @Test
    void testEquals() {
        TrainingFindModel trainingFindModel2 = new TrainingFindModel(trainingName, preferredPosition);
        assertEquals(trainingFindModel, trainingFindModel2);
    }

    @Test
    void canEqual() {
        assertFalse(trainingFindModel.canEqual(null));
        assertFalse(trainingFindModel.canEqual(new Object()));
        assertTrue(trainingFindModel.canEqual(new TrainingFindModel(trainingName, preferredPosition)));
    }

    @Test
    void testHashCode() {
        TrainingFindModel trainingFindModel2 = new TrainingFindModel(trainingName, preferredPosition);
        assertEquals(trainingFindModel.hashCode(), trainingFindModel2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "TrainingFindModel(trainingName=trainingName, preferredPosition=COX)";
        assertEquals(expected, trainingFindModel.toString());
    }
}