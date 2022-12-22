package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import nl.tudelft.sem.template.activity.models.InformJoinRequestModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TrainingService extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient UserRestService userRestService;
    private final transient TrainingRepository trainingRepository;
    private final transient BoatRestService boatRestService;
    private final transient CurrentTimeProvider currentTimeProvider;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param eventPublisher      the event publisher for user acceptance
     * @param userRestService     the user rest service
     * @param trainingRepository  the repository for trainings
     * @param boatRestService     the boat rest service
     * @param currentTimeProvider the current time provider
     */
    public TrainingService(EventPublisher eventPublisher, UserRestService userRestService,
                           TrainingRepository trainingRepository, BoatRestService boatRestService,
                           CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.userRestService = userRestService;
        this.trainingRepository = trainingRepository;
        this.boatRestService = boatRestService;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Method to parse the TrainingCreateModel.
     *
     * @param request the TrainingCreateModel aka the request body
     * @param netId the netId of the requester
     * @return a new Training
     */
    public Training parseRequest(TrainingCreateModel request, NetId netId, long boatId) {
        return new Training(netId, request.getTrainingName(), boatId,
                request.getStartTime(), request.getType());
    }


    /**
     * Create a new training.
     *
     * @param netId the id of the owner
     * @param request the request body
     * @return a new training
     * @throws Exception the already using this netId exception
     */
    public String createTraining(TrainingCreateModel request, NetId netId) throws Exception {
        long boatId = boatRestService.getBoatId(request.getType());
        if (boatId == -1) {
            return "Could not contact boat service";
        }
        Training training = parseRequest(request, netId, boatId);
        trainingRepository.save(training);
        return "Training successfully created";
    }

    /**
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @return if success
     */
    public String informUser(AcceptRequestModel model) {
        return informUser(model, trainingRepository, eventPublisher);
    }

    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinTraining(JoinRequestModel request) {
        Training training = trainingRepository.findById(request.getActivityId());
        if (training == null) {
            return "this competition ID does not exist";
        }
        long startTime = training.getStartTime();
        boolean isInOneDay = (startTime - currentTimeProvider.getCurrentTime().toEpochMilli()) < 86400000;
        if (isInOneDay) {
            return "Sorry you can't join this training since it will start in one day.";
        }

        UserDataRequestModel userData = userRestService.getUserData();
        if (userData == null) {
            return "We could not get your user information from the user service";
        }
        if (request.getPosition() == Position.COX
                && training.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        }
        eventPublisher.publishJoining(training.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }

    /**
     * The method to find the training with id.
     *
     * @param id The id of the training to be found.
     * @return A training found.
     * @throws Exception An exception to be thrown when facing failures.
     */
    public Training findTraining(long id) throws Exception {
        try {
            return trainingRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findTraining");
        }
    }

    /**
     * The method to delete a training.
     *
     * @param trainingId The id of the training which is to be deleted.
     * @return A string representing the status of the deletion.
     * @throws Exception An exception which is thrown when facing failures.
     */
    public String deleteTraining(long trainingId) throws Exception {
        try {
            Training training = findTraining(trainingId);
            trainingRepository.delete(training);
            long boatId = training.getBoatId();
            BoatDeleteModel boatDeleteModel = new BoatDeleteModel(boatId);
            if (boatRestService.deleteBoat(boatDeleteModel)) {
                return "Successfully deleted the training.";
            } else {
                return "Boat deletion fail.";
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong in delete the specified training.");
        }
    }

    /**
     * The method to update a training.
     *
     * @param training The training to be updated.
     * @param request The edit model which contains all information about the updating.
     * @return A training which is updated.
     */
    public Training update(Training training, TrainingEditModel request) {
        training.setActivityName(request.getTrainingName());
        training.setStartTime(request.getStartTime());
        return training;
    }

    /**
     * The method to edit a training.
     *
     * @param request The request which contains all information about the training to be edited.
     * @return A message showing whether the training is edited successfully.
     * @throws Exception An exception to be thrown when facing difficulties.
     */
    public String editTraining(TrainingEditModel request) throws Exception {
        try {
            Training training = trainingRepository.findById(request.getId());
            training = update(training, request);
            trainingRepository.save(training);
            return "Successfully edited training";
        } catch (Exception e) {
            throw new Exception("Something went wrong in editing training");
        }
    }
}
