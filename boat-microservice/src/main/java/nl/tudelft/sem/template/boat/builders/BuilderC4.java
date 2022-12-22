package nl.tudelft.sem.template.boat.builders;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.Type;

@Getter
@Setter
public class BuilderC4 implements BoatBuilder {
    private Boat boat;

    public BuilderC4() {
    }

    public void reset() {
        this.boat = new Boat(Type.C4);
    }

    public void setCox(int value) {
        this.boat.getRequiredRowers().put(Position.COX, value);
    }

    public void setCoach(int value) {
        this.boat.getRequiredRowers().put(Position.COACH, value);
    }

    public void setPort(int value) {
        this.boat.getRequiredRowers().put(Position.PORT, value);
    }

    public void setStarboard(int value) {
        this.boat.getRequiredRowers().put(Position.STARBOARD, value);
    }

    public void setSculling(int value) {
        this.boat.getRequiredRowers().put(Position.SCULLING, value);
    }

    public Boat getBoat() {
        return this.boat;
    }
}