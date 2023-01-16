package nl.tudelft.sem.template.boat.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boats")
@Getter
@Setter
@NoArgsConstructor
public class Boat {
    /**
     * Identifier for the boat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "type")
    private Type type;

    // TODO: create connection to Users table
    @Convert(converter = RowersAttributeConverter.class)
    private Rowers rowers;
    @Convert(converter = RequiredRowersAttributeConverter.class)
    private RequiredRowers requiredRowers;

    /**
     * Create new boat.
     *
     * @param type the type of the boat
     */
    public Boat(Type type) {
        this.type = type;
        this.rowers = new Rowers();
        this.requiredRowers = new RequiredRowers();
    }

    /**
     * Basic getter for the current rowers.
     *
     * @return the current rowers for the boat
     */
    public Map<Position, List<NetId>> getRowers() {
        return this.rowers.currentRowers;
    }

    /**
     * Basic getter for the required rowers.
     *
     * @return the required rowers for a boat
     */
    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers.amountOfPositions;
    }

    /**
     * Adds rower to boat.
     *
     * @param currentNetId the user's netId to be added
     */
    public void addRowerToPosition(Position position, NetId currentNetId) {
        this.rowers.currentRowers.get(position).add(currentNetId);
        int val = requiredRowers.amountOfPositions.get(position);
        requiredRowers.amountOfPositions.replace(position, val - 1);
    }

    /**
     * Removes rower from the boat.
     *
     * @param currentNetId the user's netId to be removed
     * @return true, if the user was successfully removed, false otherwise
     */
    public boolean removeRower(NetId currentNetId) {
        // find the key that the User is mapped to
        for (Map.Entry<Position, List<NetId>> a : rowers.currentRowers.entrySet()) {
            if (a.getValue().contains(currentNetId)) {
                a.getValue().remove(currentNetId);
                int val = requiredRowers.amountOfPositions.get(a.getKey());
                requiredRowers.amountOfPositions.replace(a.getKey(), val + 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Compare this Boat object to another Object.
     *
     * @param o the other object
     * @return true/false based on whether the 2 objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boat)) {
            return false;
        }
        Boat boat = (Boat) o;
        if (!(id == boat.id && type == boat.type)) {
            return false;
        }
        return Objects.equals(rowers, boat.rowers) && Objects.equals(requiredRowers, boat.requiredRowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, rowers, requiredRowers);
    }
}
