package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserDataRequestModelTest {

    private UserDataRequestModel userDataRequestModel;

    private Gender gender;

    private String organization;

    private boolean amateur;

    private Certificate certificate;

    @BeforeEach
    void setUp() {
        userDataRequestModel = new UserDataRequestModel();
        gender = Gender.MALE;
        organization = "organization";
        amateur = true;
        certificate = Certificate.C4;
        userDataRequestModel.setAmateur(amateur);
        userDataRequestModel.setCertificate(certificate);
        userDataRequestModel.setGender(gender);
        userDataRequestModel.setOrganization(organization);
    }

    @Test
    void getGender() {
        assertEquals(gender, userDataRequestModel.getGender());
    }

    @Test
    void getOrganization() {
        assertEquals(organization, userDataRequestModel.getOrganization());
    }

    @Test
    void isAmateur() {
        assertEquals(amateur, userDataRequestModel.isAmateur());
    }

    @Test
    void getCertificate() {
        assertEquals(certificate, userDataRequestModel.getCertificate());
    }

    @Test
    void setGender() {
        Gender newGender = Gender.FEMALE;
        userDataRequestModel.setGender(newGender);
        assertEquals(newGender, userDataRequestModel.getGender());
    }

    @Test
    void setOrganization() {
        String newOrganization = "newOrganization";
        userDataRequestModel.setOrganization(newOrganization);
        assertEquals(newOrganization, userDataRequestModel.getOrganization());
    }

    @Test
    void setAmateur() {
        boolean newAmateur = false;
        userDataRequestModel.setAmateur(newAmateur);
        assertEquals(newAmateur, userDataRequestModel.isAmateur());
    }

    @Test
    void setCertificate() {
        Certificate newCertificate = Certificate.PLUS4;
        userDataRequestModel.setCertificate(newCertificate);
        assertEquals(newCertificate, userDataRequestModel.getCertificate());
    }

    @Test
    void testEquals() {
        UserDataRequestModel userDataRequestModel2 = new UserDataRequestModel();
        UserDataRequestModel userDataRequestModel3 = new UserDataRequestModel();
        assertEquals(userDataRequestModel3, userDataRequestModel2);
    }

    @Test
    void canEqual() {
        assertFalse(userDataRequestModel.canEqual(null));
        assertFalse(userDataRequestModel.canEqual(new Object()));
    }

    @Test
    void testHashCode() {
        UserDataRequestModel userDataRequestModel1 = new UserDataRequestModel();
        UserDataRequestModel userDataRequestModel2 = new UserDataRequestModel();
        assertEquals(userDataRequestModel2.hashCode(), userDataRequestModel1.hashCode());
    }

    @Test
    void testToString() {
        UserDataRequestModel userDataRequestModel2 = new UserDataRequestModel();
        assertEquals("UserDataRequestModel(gender=null, organization=null, amateur=false, certificate=null)",
                userDataRequestModel2.toString());
    }
}