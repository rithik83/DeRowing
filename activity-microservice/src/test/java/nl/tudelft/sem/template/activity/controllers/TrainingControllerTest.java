package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
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
    private TrainingService trainingService;

    private Training training;

    private TrainingCreateModel trainingCreateModel;

    private TrainingController trainingController;

    private AcceptRequestModel acceptRequestModel;

    private JoinRequestModel joinRequestModel;

    private TrainingEditModel trainingEditModel;

    private ActivityCancelModel activityCancelModel;

    @BeforeEach
    public void setup() {
        authManager = mock(AuthManager.class);
        trainingService = mock(TrainingService.class);
        training = new Training(new NetId("123"), "name", 123L, 123L, Type.C4);
        acceptRequestModel = new AcceptRequestModel();
        joinRequestModel = new JoinRequestModel();
        trainingEditModel = new TrainingEditModel();
        activityCancelModel = new ActivityCancelModel(123L);
        trainingController = new TrainingController(authManager, trainingService);
        trainingCreateModel = new TrainingCreateModel("test", 123L, Type.C4);
    }

    @Test
    void createTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.createTraining(trainingCreateModel, new NetId(authManager.getNetId())))
                .thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingController.createTraining(trainingCreateModel));
    }

    @Test
    void informUserTest() {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.informUser(acceptRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingController.informUser(acceptRequestModel));
    }

    @Test
    void joinTrainingTest() {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.joinTraining(joinRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingController.joinTraining(joinRequestModel));
    }

    @Test
    void editTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.editTraining(trainingEditModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingController.editTraining(trainingEditModel));
    }

    @Test
    void cancelTrainingTest() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.deleteTraining(123L)).thenReturn("success");
        //when(activityCancelModel.getId()).thenReturn(123L);
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                trainingController.cancelTraining(activityCancelModel));
    }
}