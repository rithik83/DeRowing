package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public class ActivityService {

    /** Persists all the changes concerning new accepted attendees in the database.
     *
     * @param model The request body from the request to accept/reject a user.
     * @param repository the repository to persist in.
     * @return if succeeded
     */
    public boolean persistNewActivity(AcceptRequestModel model, JpaRepository repository) {
        long activityId = model.getActivityId();
        Optional<Activity> optionalActivity = repository.findById(activityId);
        if (optionalActivity.isEmpty()) {
            return false;
        }
        if (!model.isAccepted()) {
            return true;
        }
        Activity activity = optionalActivity.get();
        List<NetId> attendees = activity.getAttendees();
        attendees.add(model.getRequestee());
        activity.setAttendees(attendees);
        repository.save(activity);
        return true;
    }

    /**
     * Method to inform the User microservice of acceptance.
     *
     * @param model the request body from the request to accept/reject a user.
     * @param repository  the repository to persist in.
     * @param eventPublisher the event publisher to publish the event to.
     * @return if succeeded
     */
    public String informUser(AcceptRequestModel model, JpaRepository repository, EventPublisher eventPublisher) {
        if (!persistNewActivity(model, repository)) {
            return "Could not find activity";
        }
        eventPublisher.publishAcceptance(model.isAccepted(), model.getPosition(), model.getRequestee());
        if (model.isAccepted()) {
            Activity activity = (Activity) repository.findById(model.getActivityId()).get();
            eventPublisher.publishBoatChange(activity.getBoatId(), model.getPosition(), model.getRequestee());
        }
        return "The user is informed of your decision";
    }
}
