package nl.tudelft.sem.template.boat.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.models.BoatDeleteModel;
import nl.tudelft.sem.template.boat.models.BoatEmptyPositionsModel;
import nl.tudelft.sem.template.boat.models.BoatListModel;
import nl.tudelft.sem.template.boat.models.BoatRowerEditModel;
import nl.tudelft.sem.template.boat.services.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RequestMapping("/boat")
@RestController
public class BoatController {

    private final transient AuthManager authManager;

    private final transient BoatService boatService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     * @param boatService the service provider of all boats
     */
    @Autowired
    public BoatController(AuthManager authManager, BoatService boatService) {
        this.authManager = authManager;
        this.boatService = boatService;
    }

    /**
     * the method to create a boat.
     *
     * @param request a boat create model, which contains all information about the boat
     * @return a boat
     * @throws Exception the boat cannot be created
     */
    @PostMapping("/create")
    public ResponseEntity<BoatDeleteModel> createBoat(@RequestBody BoatCreateModel request) throws Exception {
        try {
            ResponseEntity.BodyBuilder bb;
            Boat returnedBoat = boatService.createBoat(request);
            int id = returnedBoat.getId();
            bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body(new BoatDeleteModel(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * the method to insert a certain user in a boat for a specific position.
     *
     * @param request the request body of the insertion
     * @throws Exception the user's netId cannot be inserted
     */
    @PostMapping("/insert")
    public void insertRower(@RequestBody BoatRowerEditModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            NetId netId = request.getNetId();
            target.addRowerToPosition(request.getPosition(), netId);
            boatService.updateBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * the method to remove a certain user in a boat for a specific position.
     *
     * @param request the request body of the removal
     * @throws Exception the user's netId cannot be removed
     */
    @PostMapping("/remove")
    public void removeRower(@RequestBody BoatRowerEditModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            NetId netId = request.getNetId();
            target.removeRower(netId);
            boatService.updateBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * the method to delete a certain boat.
     *
     * @param request the request containing the boat's id
     * @throws Exception the boat could not be deleted
     */
    @PostMapping("/delete")
    public void deleteBoat(@RequestBody BoatDeleteModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            boatService.deleteBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Method for finding the boats that have certain positions still available.
     *
     * @param request the request containing the boat's id
     * @return a string which contains information regarding the deletion of the boat
     * @throws Exception the boat could not be deleted
     */
    @PostMapping("/check")
    public ResponseEntity<BoatListModel> findBoatsByPosition(@RequestBody BoatEmptyPositionsModel request) throws Exception {
        try {
            ResponseEntity.BodyBuilder bb;
            List<Long> boatIds = boatService.findBoatsByPosition(request.getBoatIds(), request.getPosition());
            bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body(new BoatListModel(boatIds));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
