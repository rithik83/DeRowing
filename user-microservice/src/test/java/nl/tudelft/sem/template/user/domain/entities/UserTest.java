package nl.tudelft.sem.template.user.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User sut;

    @BeforeEach
    public void setup() {
        sut = new User(new NetId("123"), Gender.MALE, Certificate.PLUS4, "Proteus", false);
    }

    @Test
    void testToString() {
        String toString = sut.toString();
        assertEquals(
                "Your NetId: 123\n"
                        + " Gender: MALE\n"
                        + " Certification: PLUS4\n"
                        + " Organization: Proteus\n"
                        + " Status: Professional", toString);
    }

    @Test
    void testGetNetId() {
        assertEquals(new NetId("123"), sut.getNetId());
    }

    @Test
    void testGetGender() {
        assertEquals(Gender.MALE, sut.getGender());
    }

    @Test
    void testGetCertificate() {
        assertEquals(Certificate.PLUS4, sut.getCertificate());
    }

    @Test
    void testGetOrganization() {
        assertEquals("Proteus", sut.getOrganization());
    }

    @Test
    void testIsAmateur() {
        assertEquals(false, sut.isAmateur());
    }
}
