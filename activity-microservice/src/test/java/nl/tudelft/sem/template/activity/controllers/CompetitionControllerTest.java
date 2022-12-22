package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

class CompetitionControllerTest {

    @Mock
    private AuthManager authManager;

    @Mock
    private CompetitionService competitionService;

    private CompetitionController competitionController;

    private CompetitionCreateModel competitionCreateModel;

    private AcceptRequestModel acceptRequestModel;

    private JoinRequestModel joinRequestModel;

    private ActivityCancelModel activityCancelModel;

    private CompetitionEditModel competitionEditModel;

    private Position position;

    @BeforeEach
    void setup() {
        authManager = mock(AuthManager.class);
        competitionService = mock(CompetitionService.class);
        competitionCreateModel = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                false, false, "TUDELFT", 123L, Type.C4);
        acceptRequestModel = new AcceptRequestModel();
        joinRequestModel = new JoinRequestModel();
        competitionEditModel = new CompetitionEditModel();
        activityCancelModel = new ActivityCancelModel(123L);
        position = Position.COX;
        competitionController = new CompetitionController(authManager, competitionService);
    }

    @Test
    void helloWorld() {
        when(authManager.getNetId()).thenReturn("123");
        Assertions.assertEquals(new ResponseEntity<>("Hello 123", HttpStatus.valueOf(200)),
                competitionController.helloWorld());
    }

    @Test
    void createCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.createCompetition(competitionCreateModel, new NetId(authManager.getNetId())))
                .thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.createCompetition(competitionCreateModel));
    }

    @Test
    void informUser() {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.informUser(acceptRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.informUser(acceptRequestModel));
    }

    @Test
    void joinCompetition() {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.joinCompetition(joinRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.joinCompetition(joinRequestModel));
    }

    @Test
    void cancelCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.deleteCompetition(123L)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.cancelCompetition(activityCancelModel));
    }

    @Test
    void editCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.editCompetition(competitionEditModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.editCompetition(competitionEditModel));
    }

    @Test
    void getCompetitions() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        List<Competition> result = new ArrayList<>();
        when(competitionService.getSuitableCompetition(position)).thenReturn(result);
        Assertions.assertEquals(new ResponseEntity<>(result, HttpStatus.valueOf(200)),
                competitionController.getCompetitions(position));
    }
}