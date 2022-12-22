package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.boat.domain.Type;

@Data
@NoArgsConstructor
public class BoatCreateModel {
    private Type type;

    public BoatCreateModel(Type type) {
        this.type = type;
    }
}
