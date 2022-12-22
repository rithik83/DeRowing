package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FindSuitableCompetitionResponseModelTest {

    private FindSuitableCompetitionResponseModel model;

    private List<Competition> competitions;

    @BeforeEach
    void setUp() {
        competitions = new ArrayList<>();
        model = new FindSuitableCompetitionResponseModel();
        model.setCompetitions(competitions);
    }

    @Test
    void getCompetitions() {
        assertEquals(competitions, model.getCompetitions());
    }

    @Test
    void setCompetitions() {
        List<Competition> competitions2 = new ArrayList<>();
        model.setCompetitions(competitions2);
        assertEquals(competitions2, model.getCompetitions());
    }

    @Test
    void testEquals() {
        FindSuitableCompetitionResponseModel model2 = new FindSuitableCompetitionResponseModel();
        model2.setCompetitions(competitions);
        assertEquals(model, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertTrue(model.canEqual(new FindSuitableCompetitionResponseModel()));
    }

    @Test
    void testHashCode() {
        FindSuitableCompetitionResponseModel model2 = new FindSuitableCompetitionResponseModel();
        model2.setCompetitions(competitions);
        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("FindSuitableCompetitionResponseModel(competitions=" + competitions + ")",
                model.toString());
    }
}