package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceServerSide;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceUserSide;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TrainingControllerTest {

    @Mock
    private AuthManager authManager;

    @Mock
    private TrainingServiceUserSide trainingServiceUserSide;

    @Mock
    private TrainingServiceServerSide trainingServiceServerSide;

    private Training training;

    private TrainingCreateModel trainingCreateModel;

    private TrainingControllerUserSide trainingControllerUserSide;

    private TrainingControllerServerSide trainingControllerServerSide;

    private AcceptRequestModel acceptRequestModel;

    private JoinRequestModel joinRequestModel;

    private TrainingEditModel trainingEditModel;

    private ActivityCancelModel activityCancelModel;

    @BeforeEach
    public void setup() {
        authManager = mock(AuthManager.class);
        trainingServiceUserSide = mock(TrainingServiceUserSide.class);
        trainingServiceServerSide = mock(TrainingServiceServerSide.class);
        training = new Training(new NetId("123"), "name", 123L, 123L, Type.C4);
        acceptRequestModel = new AcceptRequestModel();
        joinRequestModel = new JoinRequestModel();
        trainingEditModel = new TrainingEditModel();
        activityCancelModel = new ActivityCancelModel(123L);
        trainingControllerUserSide = new TrainingControllerUserSide(trainingServiceUserSide);
        trainingControllerServerSide = new TrainingControllerServerSide(authManager, trainingServiceServerSide);
        trainingCreateModel = new TrainingCreateModel("test", 123L, Type.C4);
    }

    @Test
    void createTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingServiceServerSide.createTraining(trainingCreateModel, new NetId(authManager.getNetId())))
                .thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingControllerServerSide.createTraining(trainingCreateModel));
    }

    @Test
    void informUserTest() {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingServiceUserSide.informUser(acceptRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingControllerUserSide.informUser(acceptRequestModel));
    }

    @Test
    void joinTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingServiceUserSide.joinTraining(joinRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingControllerUserSide.joinTraining(joinRequestModel));
    }

    @Test
    void editTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingServiceServerSide.editTraining(trainingEditModel, authManager.getNetId())).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingControllerServerSide.editTraining(trainingEditModel));
    }

    @Test
    void cancelTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingServiceServerSide.deleteTraining(123L, authManager.getNetId())).thenReturn("success");
        //when(activityCancelModel.getId()).thenReturn(123L);
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingControllerServerSide.cancelTraining(activityCancelModel));
    }
}