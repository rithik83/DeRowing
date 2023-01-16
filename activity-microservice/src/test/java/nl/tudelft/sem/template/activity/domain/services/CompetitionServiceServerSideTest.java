package nl.tudelft.sem.template.activity.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Instant;

class CompetitionServiceServerSideTest {

    private transient EventPublisher eventPublisher;
    private transient CompetitionRepository competitionRepository;
    private transient RestServiceFacade restServiceFacade;
    private transient CompetitionServiceServerSide sut;
    private transient CompetitionServiceUserSide sut2;
    private transient CurrentTimeProvider currentTimeProvider;

    @BeforeEach
    public void setUp() {
        //Mock dependencies
        this.eventPublisher = Mockito.mock(EventPublisher.class);
        this.competitionRepository = Mockito.mock(CompetitionRepository.class);
        this.restServiceFacade = Mockito.mock(RestServiceFacade.class);
        this.currentTimeProvider = Mockito.mock(CurrentTimeProvider.class);
        this.sut = new CompetitionServiceServerSide(eventPublisher, competitionRepository,
                restServiceFacade, currentTimeProvider);
        this.sut2 = new CompetitionServiceUserSide(eventPublisher,
                competitionRepository, restServiceFacade, currentTimeProvider);
    }

    public Competition fabricateCompetition(long id, long boatId, Type type) {
        return new Competition(new NetId("maarten"), "test", boatId, 1000, true,
                GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", type);
    }

    @Test
    public void createCompetitionIdeal() throws Exception {
        CompetitionCreateModel model = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT, true,
                true, "TUDELFT", 1000, Type.C4);

        when(restServiceFacade.performBoatModel(any(), any(), any())).thenReturn(new CreateBoatResponseModel(1L));
        String result = sut.createCompetition(model, new NetId("maarten"));
        assertEquals("Successfully created competition", result);

    }

    @Test
    public void createCompetitionNoBoat() throws Exception {
        CompetitionCreateModel model = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT, true,
                true, "TUDELFT", 1000, Type.C4);

        when(restServiceFacade.performBoatModel(any(), any(), any())).thenReturn(null);
        assertThrows(Exception.class, () -> sut.createCompetition(model, new NetId("maarten")));

    }

    @Test
    public void createCompetitionAlreadyExistsTest() throws Exception {
        CompetitionCreateModel model = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT, true,
                true, "TUDELFT", 1000, Type.C4);

        when(restServiceFacade.performBoatModel(any(), any(), any())).thenReturn(new CreateBoatResponseModel(1L));
        when(competitionRepository.save(Mockito.any(Competition.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> sut.createCompetition(model, new NetId("maarten")));
    }

    @Test
    public void joinCompetitionTest() throws Exception {
        when(currentTimeProvider.getCurrentTime()).thenReturn(Instant.ofEpochMilli(-1000000000L));
        //Input
        JoinRequestModel request = new JoinRequestModel();
        request.setActivityId(1L);
        request.setPosition(Position.STARBOARD);
        sut2.joinCompetition(request);

        // Competition doesn't exist
        String result = sut2.joinCompetition(request);
        assertEquals("this competition ID does not exist", result);

        // Competition exists, but no Userdata
        Competition competition = fabricateCompetition(1L, 1L, Type.C4);

        when(competitionRepository.findById(1L)).thenReturn(competition);
        result = sut2.joinCompetition(request);
        assertEquals("We could not get your user information from the user service", result);

        // Complies with all constraints
        UserDataRequestModel userData = new UserDataRequestModel();
        userData.setAmateur(true);
        userData.setOrganization("UVA");
        userData.setGender(Gender.MALE);
        userData.setCertificate(Certificate.C4);

        when(restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(userData);

        result = sut2.joinCompetition(request);
        assertEquals("Done! Your request has been processed", result);

        // Does not meet amateur constraint
        competition.setAllowAmateurs(false);
        result = sut2.joinCompetition(request);
        assertEquals("you do not meet the constraints of this competition", result);

        // Does not meet the organization constraint
        competition.setAllowAmateurs(true);
        competition.setSingleOrganization(true);
        result = sut2.joinCompetition(request);
        assertEquals("you do not meet the constraints of this competition", result);

        // Does not meet the Gender constraint
        competition.setSingleOrganization(false);
        competition.setGenderConstraint(GenderConstraint.ONLY_FEMALE);
        result = sut2.joinCompetition(request);
        assertEquals("you do not meet the constraints of this competition", result);

        // Does not meet the certificate constraint
        competition.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        competition.setType(Type.PLUS4);
        request.setPosition(Position.COX);
        result = sut2.joinCompetition(request);
        assertEquals("you do not have the required certificate to be cox", result);


    }
}