package nl.tudelft.sem.template.activity.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.UserJoinEvent;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager",
        "mockBoatRestService", "mockUserRestService", "mockApplicationEventPublisher"})
@AutoConfigureMockMvc
public class TrainingIntegrationTest {

    // Rest services are mocked to avoid having to run the other microservices
    @Autowired
    private BoatRestService mockBoatRestService;
    @Autowired
    private ApplicationEventPublisher mockApplicationEventPublisher;
    @Autowired
    private UserRestService mockUserRestService;
    @Autowired
    private AuthManager mockAuthenticationManager;
    @Autowired
    private JwtTokenVerifier mockJwtTokenVerifier;
    @Autowired
    private CurrentTimeProvider currentTimeProvider;
    @Autowired
    private MockMvc mockMvc;
    // This is used to assert db state
    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * Reset the mocks before each test.
     */
    @BeforeEach
    public void setup() {
        // Authorization mocking
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        // Reset mock counters
        reset(mockApplicationEventPublisher);
        reset(mockBoatRestService);
        reset(mockBoatRestService);
        reset(mockUserRestService);
        // Reset database state
        trainingRepository.deleteAll();
        trainingRepository.resetSequence();
    }

    /**
     * Creates a training.
     *
     * @param owner the owner of the training
     * @param boatId the id of the boat
     * @return a training create model
     */
    public Training fabricateTraining(String owner, long boatId) {
        long curr = currentTimeProvider.getCurrentTime().toEpochMilli();
        curr += 2 * (24 * 60 * 60 * 1000);
        return new Training(new NetId("barrack"), "name", 1L, curr, Type.C4);
    }

    /**
     * Performs a post request using mockMVC.
     *
     * @param body the body of the request
     * @param path the path of the request
     * @return the result of the request
     * @throws Exception if the request fails
     */
    public ResultActions performPost(Object body, String path) throws Exception {
        return mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));
    }

    /**
     * Serializes an object.
     *
     * @param obj An object
     * @return JSON of the input object
     */
    public String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("serialization error");
        }
        return null;
    }

    @Test
    public void createTest() throws Exception {
        TrainingCreateModel body = new TrainingCreateModel("testname", 100, Type.C4);

        // Case where boatMicroservice is online
        when(mockBoatRestService.getBoatId(body.getType())).thenReturn(1L);

        ResultActions res = performPost(body, "/training/create");
        String response = res.andReturn().getResponse().getContentAsString();

        assertEquals("Training successfully created", response);

        Training t = trainingRepository.findById(1L);
        assertEquals("testname", t.getActivityName());

        // Case where boat microservice is offline
        when(mockBoatRestService.getBoatId(body.getType())).thenReturn(-1L);

        res = performPost(body, "/training/create");
        response = res.andReturn().getResponse().getContentAsString();

        assertEquals("Could not contact boat service", response);

    }

    @Test
    public void informTest() throws Exception {
        // set db state
        Training t = new Training(new NetId("barrack"), "name", 1L, 1000000000000L, Type.C4);
        trainingRepository.save(t);

        AcceptRequestModel body = new AcceptRequestModel();
        body.setAccepted(true);
        body.setActivityId(1L);
        body.setRequestee(new NetId("Maarten"));
        body.setPosition(Position.COX);

        ResultActions res = performPost(body, "/training/inform");
        String response = res.andReturn().getResponse().getContentAsString();

        assertEquals("The user is informed of your decision", response);

        t = trainingRepository.findById(1L);
        assertEquals(1, t.getAttendees().size());
    }

    @Test
    public void joinTest() throws Exception {
        // set db state
        Training t = fabricateTraining("barrack", 1L);
        t.setStartTime(1);
        trainingRepository.save(t);

        JoinRequestModel body = new JoinRequestModel();
        body.setActivityId(1L);
        body.setPosition(Position.COX);

        // Starts too early
        ResultActions res = performPost(body, "/training/join");
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Sorry you can't join this training since it will start in one day.", response);

        // Activity doesnt exist
        body.setActivityId(0L);
        res = performPost(body, "/training/join");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("this competition ID does not exist", response);

        // User data is offline
        body.setActivityId(1L);
        t.setStartTime(currentTimeProvider.getCurrentTime().toEpochMilli() + 2 * (24 * 60 * 60 * 1000));
        trainingRepository.save(t);
        res = performPost(body, "/training/join");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("We could not get your user information from the user service", response);

        // Successful request
        when(mockUserRestService.getUserData()).thenAnswer(x ->
                new UserDataRequestModel(Gender.MALE, "TUDELFT", false, Certificate.C4));
        res = performPost(body, "/training/join");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Done! Your request has been processed", response);
        verify(mockApplicationEventPublisher, times(1)).publishEvent(any(UserJoinEvent.class));

        // Wrong certificate
        t.setType(Type.PLUS8);
        trainingRepository.save(t);
        res = performPost(body, "/training/join");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not have the required certificate to be cox", response);

        verify(mockUserRestService, times(3)).getUserData();
    }

    @Test
    public void editTest() throws Exception {
        // set db state
        Training t = fabricateTraining("barrack", 1L);
        trainingRepository.save(t);

        TrainingEditModel body = new TrainingEditModel();
        body.setTrainingName("testname");
        body.setId(1L);
        body.setStartTime(currentTimeProvider.getCurrentTime().toEpochMilli() + 2 * (24 * 60 * 60 * 1000));

        // pre assertion
        t = trainingRepository.findById(1L);
        assertEquals("name", t.getActivityName());

        // Successful request
        ResultActions res = performPost(body, "/training/edit");
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Successfully edited training", response);

        // post assertion
        t = trainingRepository.findById(1L);
        assertEquals("testname", t.getActivityName());
    }

    @Test
    public void editNonExistentTest() throws Exception {
        TrainingEditModel body = new TrainingEditModel();
        body.setTrainingName("testname");
        body.setId(1L);
        body.setStartTime(currentTimeProvider.getCurrentTime().toEpochMilli() + 2 * (24 * 60 * 60 * 1000));

        // Successful request
        ResultActions res = performPost(body, "/training/edit");
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Internal error when editing training.", response);

    }

    @Test
    public void cancelTest() throws Exception {
        // set db state
        Training t = fabricateTraining("barrack", 1L);
        trainingRepository.save(t);

        // Wrong id
        ActivityCancelModel body = new ActivityCancelModel(2L);

        ResultActions res = performPost(body, "/training/cancel");
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Internal error when canceling training.", response);

        // With boat restservice offline
        body.setId(1L);
        t = trainingRepository.findById(1L);
        assertNotNull(t);
        res = performPost(body, "/training/cancel");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Boat deletion fail.", response);

        t = trainingRepository.findById(1L);
        assertNull(t);

        trainingRepository.deleteAll();
        trainingRepository.resetSequence();
        t = fabricateTraining("barrack", 1L);
        trainingRepository.save(t);

        when(mockBoatRestService.deleteBoat(any(BoatDeleteModel.class))).thenReturn(true);
        res = performPost(body, "/training/cancel");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Successfully deleted the training.", response);

        verify(mockBoatRestService, times(2)).deleteBoat(any(BoatDeleteModel.class));
    }
}

