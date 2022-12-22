package nl.tudelft.sem.template.activity.domain.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.time.Instant;

class TrainingServiceTest {

    private TrainingService trainingService;

    private TrainingCreateModel trainingCreateModel;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private BoatRestService boatRestService;

    private Training training;

    private NetId id;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserRestService userRestService;

    @Mock
    private CurrentTimeProvider currentTimeProvider;

    @Mock
    private AuthManager authManager;

    private JoinRequestModel joinRequestModel;

    private AcceptRequestModel acceptRequestModel;

    @BeforeEach
    public void setup() {
        trainingCreateModel = new TrainingCreateModel("test", 123L, Type.C4);
        eventPublisher = mock(EventPublisher.class);
        trainingRepository = mock(TrainingRepository.class);
        userRestService = mock(UserRestService.class);
        boatRestService = mock(BoatRestService.class);
        currentTimeProvider = mock(CurrentTimeProvider.class);
        authManager = mock(AuthManager.class);
        joinRequestModel = new JoinRequestModel();
        joinRequestModel.setActivityId(123L);
        joinRequestModel.setPosition(Position.COACH);
        acceptRequestModel = new AcceptRequestModel();
        trainingService = new TrainingService(eventPublisher, userRestService,
                trainingRepository, boatRestService, currentTimeProvider);
        id = new NetId("123");
        training = new Training(id, trainingCreateModel.getTrainingName(), 123L, 123L,
                Type.C4);

    }

    @Test
    void parseRequest() {
        Assertions.assertEquals(training, trainingService
                .parseRequest(trainingCreateModel, id, 123L));
    }

    @Test
    void createTraining() throws Exception {
        //when(authManager.getNetId()).thenReturn("123");
        when(boatRestService.getBoatId(Type.C4)).thenReturn(123L);
        when(trainingRepository.save(training)).thenReturn(training);
        Assertions.assertEquals("Training successfully created",
                trainingService.createTraining(trainingCreateModel, new NetId("123")));
    }

    @Test
    void joinTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        when(currentTimeProvider.getCurrentTime()).thenReturn(Instant.ofEpochSecond(123L));
        Assertions.assertEquals("Sorry you can't join this training "
                + "since it will start in one day.", trainingService.joinTraining(joinRequestModel));
    }

    @Test
    void testFindTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        Assertions.assertEquals(training, trainingService.findTraining(123L));
    }

    @Test
    void deleteTraining() throws Exception {
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(123L);
        when(trainingService.findTraining(123L)).thenReturn(training);
        when(boatRestService.deleteBoat(boatDeleteModel)).thenReturn(true);
        Assertions.assertEquals("Successfully deleted the training.",
                trainingService.deleteTraining(123L));
    }

    @Test
    void update() {
        Training temp = new Training(id, "newName", 123L, 0,
                Type.C4);
        TrainingEditModel trainingEditModel = new TrainingEditModel();
        trainingEditModel.setTrainingName("newName");
        training = trainingService.update(training, trainingEditModel);
        Assertions.assertEquals(temp, training);
    }

    @Test
    void editTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        Training temp = new Training(id, "newName", 123L, 0,
                Type.C4);
        TrainingEditModel trainingEditModel = new TrainingEditModel();
        trainingEditModel.setTrainingName("newName");
        Assertions.assertThrows(Exception.class, () -> {
            trainingService.editTraining(trainingEditModel);
        });
    }
}