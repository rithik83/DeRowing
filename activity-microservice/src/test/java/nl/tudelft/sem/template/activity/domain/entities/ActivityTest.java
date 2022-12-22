package nl.tudelft.sem.template.activity.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class ActivityTest {

    private Activity test;

    @BeforeEach
    public void setup() {
        test = new Training(new NetId("123"), "name", 123L, 123L,
                Type.C4);
    }

    @Test
    void getNetId() {
        assertEquals(new NetId("123"), test.getNetId());
    }

    @Test
    void setNetId() {
        test.setNetId(new NetId("321"));
        assertEquals(new NetId("321"), test.getNetId());
    }

    @Test
    void getActivityName() {
        assertEquals("name", test.getActivityName());
    }

    @Test
    void setActivityName() {
        test.setActivityName("SomethingNew");
        assertEquals("SomethingNew", test.getActivityName());
    }

    @Test
    void getBoatId() {
        assertEquals(123L, test.getBoatId());
    }

    @Test
    void setBoatId() {
        test.setBoatId(321L);
        assertEquals(321L, test.getBoatId());
    }

    @Test
    void getStartTime() {
        assertEquals(123L, test.getStartTime());
    }

    @Test
    void setStartTime() {
        test.setStartTime(321L);
        assertEquals(321L, test.getStartTime());
    }

    @Test
    void testToString() {
        String toString = test.toString();
        assertEquals(
                "The activity is created by: 123\n"
                        + " The name is: name\n"
                        + " The boatId is: 123\n"
                        + " The start time is: 123",
                toString
        );
    }

    @Test
    void getId() {
        assertEquals(0L, test.getId());
    }

    @Test
    void setId() {
        test.setId(321L);
        assertEquals(321L, test.getId());
    }

    @Test
    void getAttendees() {
        List<NetId> ids = new ArrayList<>();
        ids.add(new NetId("123"));
        test.setAttendees(ids);
        assertEquals(1, test.getAttendees().size());
    }

    @Test
    void setAttendees() {
        List<NetId> ids = new ArrayList<>();
        ids.add(new NetId("123"));
        test.setAttendees(ids);
        assertEquals(1, test.getAttendees().size());
    }

    @Test
    void getOwner() {
        assertEquals(new NetId("123"), test.getOwner());
    }

    @Test
    void setOwner() {
        test.setOwner(new NetId("321"));
        assertEquals(new NetId("321"), test.getOwner());
    }

    @Test
    void testToString1() {
        String toString = test.toString();
        assertEquals(
                "The activity is created by: 123\n"
                        + " The name is: name\n"
                        + " The boatId is: 123\n"
                        + " The start time is: 123",
                toString
        );
    }

    @Test
    void getType() {
        assertEquals(Type.C4, test.getType());
    }

    @Test
    void setType() {
        test.setType(Type.PLUS4);
        assertEquals(Type.PLUS4, test.getType());
    }

    @Test
    void testEquals() {
        Activity test2 = new Training(new NetId("123"), "name", 123L, 123L,
                Type.C4);
        assertEquals(test, test2);
    }

    @Test
    void testHashCode() {
        Activity test2 = new Training(new NetId("123"), "name", 123L, 123L,
                Type.C4);
        assertEquals(test.hashCode(), test2.hashCode());
    }
}
