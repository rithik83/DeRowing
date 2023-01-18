package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceServerSide extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient RestServiceFacade restServiceFacade;
    private final transient TrainingRepository trainingRepository;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param eventPublisher      the event publisher for user acceptance
     * @param restServiceFacade   the rest service facade
     * @param trainingRepository  the repository for trainings
     */
    public TrainingServiceServerSide(EventPublisher eventPublisher, RestServiceFacade restServiceFacade,
                                     TrainingRepository trainingRepository) {
        this.eventPublisher = eventPublisher;
        this.restServiceFacade = restServiceFacade;
        this.trainingRepository = trainingRepository;
    }

    /**
     * Method to parse the TrainingCreateModel.
     *
     * @param request the TrainingCreateModel aka the request body
     * @param netId   the netId of the requester
     * @return a new Training
     */
    public Training parseRequest(TrainingCreateModel request, NetId netId, long boatId) {
        return new Training(netId, request.getTrainingName(), boatId,
                request.getStartTime(), request.getType());
    }

    /**
     * Create a new training.
     *
     * @param netId   the id of the owner
     * @param request the request body
     * @return a new training
     * @throws Exception the already using this netId exception
     */
    public String createTraining(TrainingCreateModel request, NetId netId) throws Exception {
        CreateBoatModel createBoatModel = new CreateBoatModel();
        createBoatModel.setType(request.getType());
        CreateBoatResponseModel response = (CreateBoatResponseModel) restServiceFacade.performBoatModel(createBoatModel,
                "/boat/create", CreateBoatResponseModel.class);
        long boatId = response.getBoatId();
        if (boatId == -1) {
            return "Could not contact boat service";
        }
        Training training = parseRequest(request, netId, boatId);
        trainingRepository.save(training);
        return "Successfully created training";
    }

    /**
     * The method to delete a training.
     *
     * @param trainingId The id of the training which is to be deleted.
     * @param netId     The netId of the user who wants to delete the training.
     * @return A string representing the status of the deletion.
     * @throws Exception An exception which is thrown when facing failures.
     */
    public String deleteTraining(long trainingId, String netId) throws Exception {
        Training training = trainingRepository.findById(trainingId);

        if (training == null) {
            return "training not found";
        }

        if (!training.getOwner().toString().equals(netId)) {
            return "You are not the owner of this competition";
        }

        long boatId = training.getBoatId();
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(boatId);
        restServiceFacade.performBoatModel(boatDeleteModel, "/boat/delete", null);
        trainingRepository.delete(training);
        return "Successfully deleted training";
    }

    /**
     * The method to update a training.
     *
     * @param training The training to be updated.
     * @param request  The edit model which contains all information about the updating.
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
     * @param netId  The netId of the user who is editing the training.
     * @return A message showing whether the training is edited successfully.
     * @throws Exception An exception to be thrown when facing difficulties.
     */
    public String editTraining(TrainingEditModel request, String netId) throws Exception {
        try {
            Training training = trainingRepository.findById(request.getId());
            if (!training.getOwner().toString().equals(netId)) {
                return "You are not the owner of this competition";
            }
            training = update(training, request);
            trainingRepository.save(training);
            return "Successfully edited training";
        } catch (Exception e) {
            throw new Exception("Something went wrong in editing training");
        }
    }
}