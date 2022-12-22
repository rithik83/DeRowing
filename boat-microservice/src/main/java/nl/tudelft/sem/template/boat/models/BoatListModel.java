package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class BoatListModel {
    List<Long> boatId;

    public BoatListModel(List<Long> boatId) {
        this.boatId = boatId;
    }
}
