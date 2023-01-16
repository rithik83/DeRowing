package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceServerSide extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient CompetitionRepository competitionRepository;
    private final transient RestServiceFacade restServiceFacade;
    private final transient CurrentTimeProvider currentTimeProvider;

    /**
     * Constructor for CompetitionService bean.
     *
     * @param eventPublisher        the event publisher
     * @param competitionRepository the competition repository
     * @param restServiceFacade     the rest service facade
     * @param currentTimeProvider   the current time provider
     */
    public CompetitionServiceServerSide(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                                        RestServiceFacade restServiceFacade,
                                        CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.competitionRepository = competitionRepository;
        this.restServiceFacade = restServiceFacade;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Method parse a requestBody.
     *
     * @param request the request body
     * @param netId   the netId of the requester
     * @return a new Competition
     */
    public Competition parseRequest(CompetitionCreateModel request, NetId netId, long boatId) {
        String competitionName = request.getCompetitionName();
        long startTime = request.getStartTime();
        boolean allowAmateurs = request.isAllowAmateurs();
        boolean singleOrganization = request.isSingleOrganization();
        GenderConstraint genderConstraint = request.getGenderConstraint();
        Type boatType = request.getType();
        String organization = request.getOrganization();
        Competition competition = new Competition(netId, competitionName, boatId, startTime,
                allowAmateurs, genderConstraint, singleOrganization, organization, boatType);
        return competition;
    }

    /**
     * Method to create and persist a new Competition.
     *
     * @param request the request body
     * @param netId   the netId of the requester
     * @return a new Competition
     * @throws Exception the already using this netId exception
     */
    public String createCompetition(CompetitionCreateModel request, NetId netId) throws Exception {
        CreateBoatModel createBoatModel = new CreateBoatModel();
        createBoatModel.setType(request.getType());
        CreateBoatResponseModel response = (CreateBoatResponseModel) restServiceFacade.performBoatModel(createBoatModel,
                "/boat/create", CreateBoatResponseModel.class);
        if (response == null) {
            throw new Exception("Could not create boat");
        }
        long boatId = response.getBoatId();
        Competition competition = parseRequest(request, netId, boatId);
        competitionRepository.save(competition);
        return "Successfully created competition";
    }

    /**
     * The method to delete a specified competition.
     *
     * @param competitionId The id of the specified competition
     * @param netId The netId of the requester
     * @return A String which shows whether the competition is deleted successfully.
     * @throws Exception An exception to show that there's something wrong during the deleting process.
     */
    public String deleteCompetition(long competitionId, String netId) throws Exception {
        Competition competition = competitionRepository.findById(competitionId);
        if (competition == null) {
            return "Competition not found";
        }
        if (!competition.getOwner().toString().equals(netId)) {
            return "You are not the owner of this competition";
        }
        long boatId = competition.getBoatId();
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(boatId);
        restServiceFacade.performBoatModel(boatDeleteModel, "/boat/delete", null);
        competitionRepository.delete(competition);
        return "Successfully deleted competition";
    }


    /**
     * The method to edit a competition.
     *
     * @param request The competition editing model
     * @param netId  The netId of the requester
     * @return A string shows the status after the edition
     * @throws Exception An exception to be shown when facing failures.
     */
    public String editCompetition(CompetitionEditModel request, String netId) throws Exception {
        try {
            Competition competition = competitionRepository.findById(request.getId());
            if (!netId.equals(competition.getOwner().toString())) {
                return "You are not the owner of this competition";
            }
            competition = update(competition, request);
            competitionRepository.save(competition);
            return "Successfully edited competition";
        } catch (Exception e) {
            throw new Exception("Something went wrong in editing competition");
        }
    }

    /**
     * The method to update a competition.
     *
     * @param competition The competition to be updated.
     * @param request The edit model which contains all information about the updating.
     * @return A competition which is updated.
     */
    public Competition update(Competition competition, CompetitionEditModel request) {
        competition.setActivityName(request.getCompetitionName());
        competition.setGenderConstraint(request.getGenderConstraint());
        competition.setAllowAmateurs(request.isAllowAmateurs());
        competition.setSingleOrganization(request.isSingleOrganization());
        competition.setOrganization(request.getOrganization());
        competition.setStartTime(request.getStartTime());
        return competition;
    }
}
