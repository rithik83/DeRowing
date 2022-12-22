package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.NetId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetitionFindModelTest {

    @BeforeEach
    void setUp() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
    }

    @Test
    void getNetId() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        assertEquals(new NetId("123"), model.getNetId());
    }

    @Test
    void setNetId() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        model.setNetId(new NetId("456"));
        assertEquals(new NetId("456"), model.getNetId());
    }

    @Test
    void testEquals() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        CompetitionFindModel model2 = new CompetitionFindModel(new NetId("123"));
        CompetitionFindModel model3 = new CompetitionFindModel(new NetId("456"));
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
    }

    @Test
    void canEqual() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        assertTrue(model.canEqual(new CompetitionFindModel(new NetId("123"))));
    }

    @Test
    void testHashCode() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        CompetitionFindModel model2 = new CompetitionFindModel(new NetId("123"));
        CompetitionFindModel model3 = new CompetitionFindModel(new NetId("456"));
        assertEquals(model.hashCode(), model2.hashCode());
        assertNotEquals(model.hashCode(), model3.hashCode());
    }

    @Test
    void testToString() {
        CompetitionFindModel model = new CompetitionFindModel(new NetId("123"));
        assertEquals("CompetitionFindModel(netId=nl.tudelft.sem.template.activity.domain.NetId@be51)",
                model.toString());
    }
}