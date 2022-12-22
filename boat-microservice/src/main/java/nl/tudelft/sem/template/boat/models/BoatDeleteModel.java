package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoatDeleteModel {
    private long boatId;

    public BoatDeleteModel(long boatId) {
        this.boatId = boatId;
    }
}
