package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityCancelModel {
    long id;

    /**
     * The constructor of the activityCancelModel.
     *
     * @param id the id of the activity
     */
    public ActivityCancelModel(long id) {
        this.id = id;
    }
}
