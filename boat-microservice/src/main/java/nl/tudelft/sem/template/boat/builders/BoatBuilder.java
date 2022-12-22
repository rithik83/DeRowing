package nl.tudelft.sem.template.boat.builders;

import nl.tudelft.sem.template.boat.domain.Boat;

public interface BoatBuilder {

    /**
     * Reset method which instantiates a new boat for this constructor.
     */
    void reset();

    /**
     * Basic setter for the cox field.
     *
     * @param value the number of required people who must be a cox
     */
    void setCox(int value);

    /**
     * Basic setter for the coach field.
     *
     * @param value the number of required people who must be a coach
     */
    void setCoach(int value);

    /**
     * Basic setter for the port field.
     *
     * @param value the number of required people who must be a port rower
     */
    void setPort(int value);

    /**
     * Basic setter for the starboard field.
     *
     * @param value the number of required people who must be a starboard rower
     */
    void setStarboard(int value);

    /**
     * Basic setter for the sculling field.
     *
     * @param value the number of required people who must be a sculling rower
     */
    void setSculling(int value);

    /**
     * The method which returns the boat which was built.
     *
     * @return the actual boat
     */
    Boat getBoat();
}
