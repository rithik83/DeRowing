package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceUserSide;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequestMapping("/training")
@RestController
public class TrainingControllerUserSide {
    private final transient TrainingServiceUserSide trainingServiceUserSide;

    /**
     * The controller of trainings.
     *
     * @param trainingServiceUserSide  the service provider of all activities
     */
    @Autowired
    public TrainingControllerUserSide(TrainingServiceUserSide trainingServiceUserSide) {
        this.trainingServiceUserSide = trainingServiceUserSide;
    }

    /**
     * A REST-mapping designed to accept / reject users from activities.
     *
     * @param model The request body
     * @return A string informing success
     */
    @PostMapping("/inform")
    public ResponseEntity<String> informUser(@RequestBody AcceptRequestModel model) {
        try {
            String status = trainingServiceUserSide.informUser(model);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when informing user.");
        }
    }

    /**
     * A REST-mapping designed to join users to activities.
     *
     * @param request the join request
     * @return status of request
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinTraining(@RequestBody JoinRequestModel request) {
        try {
            String response = trainingServiceUserSide.joinTraining(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when joining training.");
        }
    }

    /**
     * The method to get all trainings.
     *
     * @param model the requestBody of the user
     * @return a list of matching trainings
     */
    @PostMapping("/find")
    public ResponseEntity<List<Training>> getTrainings(@RequestBody PositionEntryModel model) {
        try {
            List<Training> result = trainingServiceUserSide.getSuitableCompetition(model.getPosition());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no competition that you are suitable for", e);
        }
    }
}
