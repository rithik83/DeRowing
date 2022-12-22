package nl.tudelft.sem.template.activity.domain.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;

@MappedSuperclass
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Embedded
    @Column(name = "owner")
    private NetId owner;
    @Column(name = "attendees")
    @ElementCollection(targetClass = NetId.class, fetch = FetchType.EAGER)
    private List<NetId> attendees;
    @Column(name = "activityName")
    private String activityName;
    @Column(name = "boatId")
    private long boatId;
    @Column(name = "startTime")
    private long startTime;
    @Column(name = "numPeople")
    private int numPeople;
    @Column(name = "type")
    private Type type;


    /**
     * Constructor for Activity.
     *
     * @param netId      the netId of the user
     * @param activityName the name of the activity
     * @param boatId    the id of the boat
     * @param startTime the start time of the activity
     * @param type the type of the boat
     */
    public Activity(NetId netId, String activityName, long boatId, long startTime, Type type) {
        this.owner = netId;
        this.activityName = activityName;
        this.boatId = boatId;
        this.startTime = startTime;
        this.type = type;
    }

    public NetId getNetId() {
        return owner;
    }

    public void setNetId(NetId netId) {
        this.owner = netId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<NetId> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<NetId> attendees) {
        this.attendees = attendees;
    }

    public NetId getOwner() {
        return owner;
    }

    public void setOwner(NetId owner) {
        this.owner = owner;
    }

    /**
     * A method provide string format information.
     *
     * @return a string contains information about the activity.
     */
    public String toString() {
        return "The activity is created by: " + owner.getNetId() + "\n The name is: "
                + activityName  + "\n The boatId is: "
                + boatId + "\n The start time is: " + startTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * The method to check whether two activities are equal.
     *
     * @param o Another activity to be compared with
     * @return a boolean value representing the result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return id == activity.id && boatId == activity.boatId && startTime == activity.startTime
                && Objects.equals(owner, activity.owner)
                && Objects.equals(activityName, activity.activityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, activityName, boatId, startTime);
    }
}
