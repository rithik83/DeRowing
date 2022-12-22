package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/training")
@RestController
public class TrainingController {

    private final transient AuthManager authManager;

    private final transient TrainingService trainingService;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingController(AuthManager authManager, TrainingService trainingService) {
        this.authManager = authManager;
        this.trainingService = trainingService;
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
            String response = trainingService.createTraining(request, new NetId(authManager.getNetId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when creating training.");
        }
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
            String status = trainingService.informUser(model);
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
            String response = trainingService.joinTraining(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when joining training.");
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
            String response = trainingService.editTraining(request);
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
            String response = trainingService.deleteTraining(activityCancelModel.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when canceling training.");
        }

    }

}
