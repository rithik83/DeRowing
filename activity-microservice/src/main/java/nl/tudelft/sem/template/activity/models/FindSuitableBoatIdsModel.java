package nl.tudelft.sem.template.activity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import java.util.List;

@Data
@AllArgsConstructor
public class FindSuitableBoatIdsModel {
    List<Long> boatIds;
    Position position;
}
