package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;

@Data
@NoArgsConstructor
public class UserDataRequestModel {
    private Gender gender;
    private String organization;
    private boolean amateur;
    private Certificate certificate;

    /**
     * Constructor for UserDataRequestModel.
     *
     * @param gender the gender
     * @param organization the organization
     * @param amateur if amateur
     * @param certificate if certificate
     */
    public UserDataRequestModel(Gender gender, String organization, boolean amateur, Certificate certificate) {
        this.gender = gender;
        this.organization = organization;
        this.amateur = amateur;
        this.certificate = certificate;
    }
}
