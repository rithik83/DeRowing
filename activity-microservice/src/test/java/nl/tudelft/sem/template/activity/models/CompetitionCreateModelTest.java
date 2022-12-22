package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CompetitionCreateModelTest {

    private CompetitionCreateModel model;

    @BeforeEach
    void setup() {
        model = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT, true,
                true, "TUDELFT", 123L, Type.C4);
    }

    @Test
    void getCompetitionName() {
        assertEquals("name", model.getCompetitionName());
    }

    @Test
    void getGenderConstraint() {
        assertEquals(GenderConstraint.NO_CONSTRAINT, model.getGenderConstraint());
    }

    @Test
    void isAllowAmateurs() {
        assertTrue(model.isAllowAmateurs());
    }

    @Test
    void isSingleOrganization() {
        assertTrue(model.isSingleOrganization());
    }

    @Test
    void getOrganization() {
        assertEquals("TUDELFT", model.getOrganization());
    }

    @Test
    void getStartTime() {
        assertEquals(123L, model.getStartTime());
    }

    @Test
    void getType() {
        assertEquals(Type.C4, model.getType());
    }

    @Test
    void setCompetitionName() {
        model.setCompetitionName("newName");
        assertEquals("newName", model.getCompetitionName());
    }

    @Test
    void setGenderConstraint() {
        model.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        assertEquals(GenderConstraint.NO_CONSTRAINT, model.getGenderConstraint());
    }

    @Test
    void setAllowAmateurs() {
        model.setAllowAmateurs(false);
        assertFalse(model.isAllowAmateurs());
    }

    @Test
    void setSingleOrganization() {
        model.setSingleOrganization(false);
        assertFalse(model.isSingleOrganization());
    }

    @Test
    void setOrganization() {
        model.setOrganization("Something");
        assertEquals("Something", model.getOrganization());
    }

    @Test
    void setStartTime() {
        model.setStartTime(321L);
        assertEquals(321L, model.getStartTime());
    }

    @Test
    void setType() {
        model.setType(Type.PLUS4);
        assertEquals(Type.PLUS4, model.getType());
    }

    @Test
    void testEquals() {
        CompetitionCreateModel model1 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        CompetitionCreateModel model2 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        assertEquals(model1, model2);
    }

    @Test
    void canEqual() {
        CompetitionCreateModel model1 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        CompetitionCreateModel model2 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        assertTrue(model1.equals(model2));
    }

    @Test
    void testHashCode() {
        CompetitionCreateModel model1 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        CompetitionCreateModel model2 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        CompetitionCreateModel model1 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        CompetitionCreateModel model2 = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                true, true, "TUDELFT", 123L, Type.C4);
        assertEquals(model1.toString(), model2.toString());
    }
}