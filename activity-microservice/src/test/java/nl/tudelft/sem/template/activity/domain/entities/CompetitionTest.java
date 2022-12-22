package nl.tudelft.sem.template.activity.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.Column;

class CompetitionTest {


    private boolean allowAmateurs;

    private GenderConstraint genderConstraint;

    private boolean singleOrganization;

    private String organization;

    private Competition competition;

    private NetId netId;

    private String activityName;

    private long boatId;

    private long startTime;

    private Type type;

    @BeforeEach
    void setup() {
        netId = new NetId("netId");
        activityName = "activityName";
        boatId = 1;
        startTime = 1;
        allowAmateurs = true;
        genderConstraint = GenderConstraint.NO_CONSTRAINT;
        singleOrganization = true;
        organization = "organization";
        type = Type.C4;
        competition = new Competition(netId,  activityName, boatId, startTime, allowAmateurs, genderConstraint,
                singleOrganization, organization, type);
    }

    @Test
    void testToString() {
        String result = competition.toString();
        assertTrue(result.contains("Allow Amateurs: " + allowAmateurs));
    }

    @Test
    void isAllowAmateurs() {
        assertEquals(allowAmateurs, competition.isAllowAmateurs());
    }

    @Test
    void setAllowAmateurs() {
        boolean allowAmateurs = false;
        competition.setAllowAmateurs(allowAmateurs);
        assertEquals(allowAmateurs, competition.isAllowAmateurs());
    }

    @Test
    void getGenderConstraint() {
        assertEquals(genderConstraint, competition.getGenderConstraint());
    }

    @Test
    void setGenderConstraint() {
        GenderConstraint genderConstraint1 = GenderConstraint.ONLY_MALE;
        competition.setGenderConstraint(genderConstraint1);
        assertEquals(genderConstraint1, competition.getGenderConstraint());
    }

    @Test
    void isSingleOrganization() {
        assertTrue(competition.isSingleOrganization());
    }

    @Test
    void setSingleOrganization() {
        boolean singleOrganization = false;
        competition.setSingleOrganization(singleOrganization);
        assertEquals(singleOrganization, competition.isSingleOrganization());
    }

    @Test
    void getOrganization() {
        assertEquals(organization, competition.getOrganization());
    }

    @Test
    void setOrganization() {
        String organization1 = "organization1";
        competition.setOrganization(organization1);
        assertEquals(organization1, competition.getOrganization());
    }

    @Test
    void testEquals() {
        Competition competition2 = new Competition(new NetId("netId"),  "activityName", 1, 1,
                true, GenderConstraint.NO_CONSTRAINT,
                true, "organization", Type.C4);
        assertEquals(competition, competition2);
    }

    @Test
    void testHashCode() {
        Competition competition2 = new Competition(new NetId("netId"),  "activityName", 1, 1,
                true, GenderConstraint.NO_CONSTRAINT,
                true, "organization", Type.C4);
        assertEquals(competition.hashCode(), competition2.hashCode());
    }
}