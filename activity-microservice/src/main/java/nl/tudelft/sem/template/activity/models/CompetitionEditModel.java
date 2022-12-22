package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Type;

@Data
@NoArgsConstructor
public class CompetitionEditModel {
    private String competitionName;
    private GenderConstraint genderConstraint;
    private boolean allowAmateurs;
    private boolean singleOrganization;
    private String organization;
    private long startTime;
    private long id;

    /**
     * Constructor for CompetitionEditModel.
     *
     * @param competitionName Name of the competition
     * @param genderConstraint Gender constraint
     * @param allowAmateurs if allowing amateurs
     * @param singleOrganization if single organization constraint
     * @param organization the organization
     * @param startTime the starttime
     * @param id the boat id
     */
    public CompetitionEditModel(String competitionName, GenderConstraint genderConstraint, boolean allowAmateurs,
                                boolean singleOrganization, String organization, long startTime, Type type,
                                long id) {
        this.competitionName = competitionName;
        this.genderConstraint = genderConstraint;
        this.allowAmateurs = allowAmateurs;
        this.singleOrganization = singleOrganization;
        this.organization = organization;
        this.startTime = startTime;
        this.id = id;
    }
}
