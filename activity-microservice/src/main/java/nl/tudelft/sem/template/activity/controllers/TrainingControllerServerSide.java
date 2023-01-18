package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthInterface;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceServerSide;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/training")
@RestController
public class TrainingControllerServerSide {

    private final transient AuthInterface authManager; // changed the AuthManager to an AuthInterface to decrease coupling

    private final transient TrainingServiceServerSide trainingServiceServerSide;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param trainingServiceServerSide  the service provider of all activities
     */
    @Autowired
    public TrainingControllerServerSide(AuthInterface authManager, TrainingServiceServerSide trainingServiceServerSide) {
        this.authManager = authManager;
        this.trainingServiceServerSide = trainingServiceServerSide;
    }

    /**
     * the method to create a training.
     *
     * @param request   a training create model, which contains all information about the competition
     * @return a training
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createTraining(@RequestBody TrainingCreateModel request) throws Exception {
        try {
            String response = trainingServiceServerSide.createTraining(request, new NetId(authManager.getNetId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when creating training.");
        }
    }

    /**
     * A method to edit the existing training.
     *
     * @param request The information that the training should have
     * @return a message containing the information about the editing
     */
    @PostMapping("/edit")
    public ResponseEntity<String> editTraining(@RequestBody TrainingEditModel request) {
        try {
            String response = trainingServiceServerSide.editTraining(request, authManager.getNetId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when editing training.");
        }
    }

    /**
     * The method to delete a specified training.
     *
     * @param activityCancelModel the id of the training to be deleted.
     * @return a message containing the information about the canceling.
     */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelTraining(@RequestBody ActivityCancelModel activityCancelModel) {
        try {
            String response = trainingServiceServerSide.deleteTraining(activityCancelModel.getId(), authManager.getNetId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when canceling training.");
        }

    }
}
