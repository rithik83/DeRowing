package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FindSuitableCompetitionModelTest {

    private FindSuitableCompetitionModel model;

    private List<Competition> competitions;

    @BeforeEach
    void setUp() {
        competitions = new ArrayList<>();
        model = new FindSuitableCompetitionModel(competitions, Position.COACH);
    }

    @Test
    void getCompetitions() {
        assertEquals(competitions, model.getCompetitions());
    }

    @Test
    void getPosition() {
        assertEquals(Position.COACH, model.getPosition());
    }

    @Test
    void setCompetitions() {
        List<Competition> competitions2 = new ArrayList<>();
        model.setCompetitions(competitions2);
        assertEquals(competitions2, model.getCompetitions());
    }

    @Test
    void setPosition() {
        model.setPosition(Position.COX);
        assertEquals(Position.COX, model.getPosition());
    }

    @Test
    void testEquals() {
        FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, Position.COACH);
        FindSuitableCompetitionModel model2 = new FindSuitableCompetitionModel(competitions, Position.COACH);
        FindSuitableCompetitionModel model3 = new FindSuitableCompetitionModel(competitions, Position.COX);
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
    }

    @Test
    void canEqual() {
        FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, Position.COACH);
        assertTrue(model.canEqual(new FindSuitableCompetitionModel(competitions, Position.COACH)));
    }

    @Test
    void testHashCode() {
        FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, Position.COACH);
        FindSuitableCompetitionModel model2 = new FindSuitableCompetitionModel(competitions, Position.COACH);
        FindSuitableCompetitionModel model3 = new FindSuitableCompetitionModel(competitions, Position.COX);
        assertEquals(model.hashCode(), model2.hashCode());
        assertNotEquals(model.hashCode(), model3.hashCode());
    }

    @Test
    void testToString() {
        FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, Position.COACH);
        assertEquals("FindSuitableCompetitionModel(competitions=" + competitions
                + ", position=" + Position.COACH + ")", model.toString());
    }
}