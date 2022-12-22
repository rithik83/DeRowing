package nl.tudelft.sem.template.boat.models;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.boat.domain.Position;

@Data
@NoArgsConstructor
public class BoatEmptyPositionsModel {
    List<Long> boatIds;
    Position position;

    public BoatEmptyPositionsModel(List<Long> boatIds, Position position) {
        this.boatIds = boatIds;
        this.position = position;
    }
}
