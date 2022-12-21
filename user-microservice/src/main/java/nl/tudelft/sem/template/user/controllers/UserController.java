package nl.tudelft.sem.template.user.controllers;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import nl.tudelft.sem.template.user.domain.models.UserAcceptanceUpdateModel;
import nl.tudelft.sem.template.user.domain.models.UserJoinRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class UserController {

    private final transient AuthManager authManager;

    private final transient UserService userService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     * @param userService The user service containing method implementations
     */
    @Autowired
    public UserController(AuthManager authManager, UserService userService) {
        this.authManager = authManager;
        this.userService = userService;
    }

    /**
     * Gets user by id.
     *
     * @return the user found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());
    }

    /**
     * The method to create a User, updating it with all its fields.
     *
     * @param request a user create model, which contains all information about the user
     * @return a user
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDetailModel request) {
        try {
            userService.createUser(request, new NetId(authManager.getNetId()));
            return ResponseEntity.ok("Congratulations " + authManager.getNetId() + ", you have created your user");
        } catch (Exception e) {
            return ResponseEntity.ok("Something went wrong in creating the user");
        }
    }

    /**
     * The method to extract details about the user.
     *
     * @return the user's information
     */
    @GetMapping("/getdetails")
    public ResponseEntity<UserDetailModel> getDetailsOfUser() {
        try {
            NetId netId = new NetId(authManager.getNetId());
            User target = userService.findUser(netId);
            Gender gender = target.getGender();
            String organization = target.getOrganization();
            boolean amateur = target.isAmateur();
            Certificate certificate = target.getCertificate();
            UserDetailModel user = new UserDetailModel(gender, organization, amateur, certificate);
            ResponseEntity.BodyBuilder bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body(user);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the request from a participant to the activity owner to join an activity, to the message database.
     *
     * @param userJoinRequest the request body of the join request
     * @return a String that informs that the message is successfully saved in the message database
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/join")
    public ResponseEntity<String> sendApplicationOfRequesterToOwner(@RequestBody UserJoinRequestModel
                                                                            userJoinRequest) {
        try {
            String content = authManager.getNetId() + " wants to join this competition/training session. "
                    + "They want to join for position " + userJoinRequest.getPosition().toString();
            userService.saveMessage(userJoinRequest.getOwner(),
                    new NetId(authManager.getNetId()),
                    userJoinRequest.getActivityId(),
                    content,
                    userJoinRequest.getPosition());
            return ResponseEntity.ok("The message is successfully saved");
        } catch (Exception e) {
            return ResponseEntity.ok("Something went wrong in sending application to activity owner");
        }
    }

    /**
     * Gets the list of messages (notifications) for the user.
     *
     * @return a list of messages that the user received
     * @throws Exception an already used NetId exception
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<Message>> getNotifications() {
        try {
            List<Message> notifications = userService.getNotifications(new NetId(authManager.getNetId()));
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the decision made by the activity owner to the message database.
     *
     * @param userAcceptanceUpdateModel the request body of the decision made
     * @return a String that informs that the message is successfully saved
     * @throws Exception already used NetId exception
     */
    @PostMapping("/update")
    public ResponseEntity<String> sendDecisionOfOwnerToRequester(@RequestBody UserAcceptanceUpdateModel
                                                                         userAcceptanceUpdateModel) {
        try {
            String content = "";
            if (userAcceptanceUpdateModel.isAccepted()) {
                content += authManager.getNetId() + " accepted your request. You have been selected for position "
                        + userAcceptanceUpdateModel.getPosition().toString();
            } else {
                content += authManager.getNetId() + " did not accept your request. Consider joining another activity!";
            }

            userService.saveMessage(userAcceptanceUpdateModel.getEventRequester(),
                    new NetId(authManager.getNetId()),
                    0,
                    content,
                    userAcceptanceUpdateModel.getPosition());
            return ResponseEntity.ok("The message is successfully saved");
        } catch (Exception e) {
            return ResponseEntity.ok("Something went wrong in sending decision of activity owner to participant");
        }
    }
}
