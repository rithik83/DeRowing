package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
@NoArgsConstructor
public class AcceptRequestModel {
    boolean accepted;
    long activityId;
    NetId requestee;
    Position position;

    /**
     * A constructor for AcceptRequestModel.
     *
     * @param accepted if the user is accepted
     * @param activityId the activity id
     * @param requestee the person getting accepted/rejected
     * @param position position the requestee wants to be
     */
    public AcceptRequestModel(boolean accepted, long activityId, NetId requestee, Position position) {
        this.accepted = accepted;
        this.activityId = activityId;
        this.requestee = requestee;
        this.position = position;
    }
}

