package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;

@Data
@NoArgsConstructor
public class BoatRowerEditModel {
    private long boatId;
    private Position position;
    private NetId netId;

    /**
     * Basic constructor for this type of model.
     *
     * @param boatId the boat's id
     * @param position the desired position
     * @param netId the user's netId
     */
    public BoatRowerEditModel(long boatId, Position position, NetId netId) {
        this.boatId = boatId;
        this.position = position;
        this.netId = netId;
    }
}
