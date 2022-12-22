package nl.tudelft.sem.template.activity.domain.entities;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;

@Entity(name = "Training")
@NoArgsConstructor
public class Training extends Activity {

    public Training(NetId netId, String activityName, long boatId, long startTime,
                    Type type) {
        super(netId, activityName, boatId, startTime, type);
    }
}
