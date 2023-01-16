package nl.tudelft.sem.template.activity.domain.services;


import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceUserSide extends ActivityService {

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
    public CompetitionServiceUserSide(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                                      RestServiceFacade restServiceFacade,
                                      CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.competitionRepository = competitionRepository;
        this.restServiceFacade = restServiceFacade;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @return if success
     */
    public String informUser(AcceptRequestModel model) {
        return informUser(model, competitionRepository, eventPublisher);
    }


    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinCompetition(JoinRequestModel request) throws Exception {
        Competition competition = competitionRepository.findById(request.getActivityId());
        if (competition == null) {
            return "this competition ID does not exist";
        }
        long startTime = competition.getStartTime();
        boolean isInOneDay = (startTime - currentTimeProvider.getCurrentTime().toEpochMilli()) < 86400000;
        if (isInOneDay) {
            return "Sorry you can't join this competition since it will start in one day.";
        }
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        if (userData == null) {
            return "We could not get your user information from the user service";
        }
        if (!competition.isAllowAmateurs() && userData.isAmateur()
                || !checkGender(userData.getGender(), competition.getGenderConstraint())
                || (competition.isSingleOrganization()
                && !userData.getOrganization().equals(competition.getOrganization()))) {
            return "you do not meet the constraints of this competition";
        }
        if (request.getPosition() == Position.COX
                && competition.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        }
        eventPublisher.publishJoining(competition.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }

    /**
     * Check the gender to make it satisfy the requirements.
     *
     * @param gender the gender of the attendee
     * @param constraint the constraint of the competition
     * @return a boolean value which shows that whether the attendee could be admitted
     */
    private boolean checkGender(Gender gender, GenderConstraint constraint) {
        if (constraint == GenderConstraint.NO_CONSTRAINT) {
            return true;
        }
        if (constraint == GenderConstraint.ONLY_MALE && gender == Gender.MALE) {
            return true;
        }
        return constraint == GenderConstraint.ONLY_FEMALE && gender == Gender.FEMALE;
    }

    /**
     * Gets suitable conpetitions for the specified position.
     *
     * @param position The position to filter from
     * @return a list of competitions
     */
    public List<Competition> getSuitableCompetition(PositionEntryModel position) throws Exception {
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        if (userData == null) {
            throw new Exception("We could not get your user information from the user service");
        }
        List<Long> boatIds = competitionRepository.findAll().stream()
                .filter(competition -> competition.getStartTime()
                        > currentTimeProvider.getCurrentTime().toEpochMilli() + 86400000)
                .filter(competition -> checkGender(userData.getGender(), competition.getGenderConstraint()))
                .filter(competition -> checkAmateur(competition, userData))
                .filter(competition -> checkOriganization(competition, userData))
                .map(Activity::getBoatId)
                .collect(Collectors.toList());

        FindSuitableActivityModel model = new FindSuitableActivityModel(boatIds, position.getPosition());
        FindSuitableActivityResponseModel suitableCompetitions =
                (FindSuitableActivityResponseModel) restServiceFacade.performBoatModel(model,
                        "/boat/check", FindSuitableActivityResponseModel.class);
        return competitionRepository.findAllByBoatIdIn(suitableCompetitions.getBoatId());
    }

    /**
     * The method to check amateur.
     *
     * @param competition The competition.
     * @param userData The User details.
     * @return A boolean value representing the result.
     */
    public boolean checkAmateur(Competition competition, UserDataRequestModel userData) {
        return competition.isAllowAmateurs() || !userData.isAmateur();
    }

    /**
     * The method to check organization.
     *
     * @param competition The competition.
     * @param userData The User details.
     * @return A boolean value representing the result.
     */
    public boolean checkOriganization(Competition competition, UserDataRequestModel userData) {
        return !competition.isSingleOrganization()
                || userData.getOrganization().equals(competition.getOrganization());
    }
}
