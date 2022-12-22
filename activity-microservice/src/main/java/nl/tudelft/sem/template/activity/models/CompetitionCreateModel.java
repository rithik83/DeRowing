package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;

@Data
public class CompetitionCreateModel {
    //private NetId netId;
    private String competitionName;
    private GenderConstraint genderConstraint;
    private boolean allowAmateurs;
    private boolean singleOrganization;
    private String organization;
    private long startTime;
    private Type type;

    /**
     *  Constructor for CompetitionCreateModel.
     *
     * @param competitionName the name of the competition
     * @param genderConstraint wether there is a gender constraint
     * @param allowAmateurs whether amateurs are allowed
     * @param singleOrganization whether only one organization is allowed
     * @param organization the organization
     * @param startTime the start time of the competition
     * @param type the type of the competition
     */
    public CompetitionCreateModel(String competitionName, GenderConstraint genderConstraint, boolean allowAmateurs,
                                  boolean singleOrganization, String organization, long startTime,
                                  Type type) {
        this.competitionName = competitionName;
        this.genderConstraint = genderConstraint;
        this.allowAmateurs = allowAmateurs;
        this.singleOrganization = singleOrganization;
        this.organization = organization;
        this.startTime = startTime;
        this.type = type;
    }
}
