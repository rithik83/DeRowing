package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.services.CompetitionServiceServerSide;
import nl.tudelft.sem.template.activity.domain.services.CompetitionServiceUserSide;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**
 * The controller for competitions.
 * <p>
 * This controller handles API calls related to competitions.
 * </p>
 */
@RequestMapping("/competition")
@RestController
public class CompetitionController {

    private final transient AuthManager authManager;

    private final transient CompetitionServiceServerSide competitionServiceServerSide;

    private final transient CompetitionServiceUserSide competitionServiceUserSide;

    /**
     * Instantiates a new controller.
     *
     * @param authManager         Spring Security component used to authenticate and authorize the user
     * @param competitionServiceServerSide  the service provider of all activities
     * @param competitionServiceUserSide the service provider of userSide
     */
    @Autowired
    public CompetitionController(AuthManager authManager, CompetitionServiceServerSide competitionServiceServerSide,
                                 CompetitionServiceUserSide competitionServiceUserSide) {
        this.authManager = authManager;
        this.competitionServiceServerSide = competitionServiceServerSide;
        this.competitionServiceUserSide = competitionServiceUserSide;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());
    }

    /**
     * the method to create a competition.
     *
     * @param request a competition create model, which contains all information about the competition
     * @return a competition
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCompetition(@RequestBody CompetitionCreateModel request) {
        try {
            String status = competitionServiceServerSide.createCompetition(request, new NetId(authManager.getNetId()));
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }


    /**
     * Mapping to join a competition.
     *
     * @param request the join request model
     * @return a string informing status
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinCompetition(@RequestBody JoinRequestModel request) {
        try {
            String response = competitionServiceUserSide.joinCompetition(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when joining the competition.");
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
            String status = competitionServiceUserSide.informUser(model);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when informing users.");
        }
    }

    /**
     * The method to cancel a specified competition.
     *
     * @param activityCancelModel The id of the specified competition.
     * @return a message with the status
     * @throws Exception The exception to be thrown when facing internal error
     */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelCompetition(@RequestBody ActivityCancelModel activityCancelModel) throws Exception {
        try {
            String status = competitionServiceServerSide.deleteCompetition(activityCancelModel.getId(),
                    authManager.getNetId());
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when canceling the competition.");
        }
    }

    /**
     * The method to edit a specified competition.
     *
     * @param request The request which contains all information about the competition to be edited.
     * @return A message showing the result of edition.
     * @throws Exception An exception to show the internal error.
     */
    @PostMapping("/edit")
    public ResponseEntity<String> editCompetition(@RequestBody CompetitionEditModel request) throws Exception {
        try {
            String status = competitionServiceServerSide.editCompetition(request, authManager.getNetId());
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when editing competition.");
        }
    }

    /**
     * Gets a list of competitions available for a suitable position.
     *
     * @param position Position in the boat for which to check
     * @return a List of competitions
     * @throws Exception Activity not found exception
     */
    @GetMapping("/find")
    public ResponseEntity<List<Competition>> getCompetitions(@RequestBody PositionEntryModel position) {
        try {
            //System.out.println("you reached the method");
            List<Competition> result = competitionServiceUserSide.getSuitableCompetition(position);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.OK, "No suitable competition found.");
        }
    }
}
