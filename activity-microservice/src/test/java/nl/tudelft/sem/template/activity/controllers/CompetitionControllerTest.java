package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.services.CompetitionServiceServerSide;
import nl.tudelft.sem.template.activity.domain.services.CompetitionServiceUserSide;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
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
    private CompetitionServiceServerSide competitionServiceServerSide;

    private CompetitionController competitionController;

    private CompetitionCreateModel competitionCreateModel;

    private AcceptRequestModel acceptRequestModel;

    private JoinRequestModel joinRequestModel;

    private ActivityCancelModel activityCancelModel;

    private CompetitionEditModel competitionEditModel;

    private PositionEntryModel positionEntryModel;

    private CompetitionServiceUserSide competitionServiceUserSide;

    @BeforeEach
    void setup() {
        authManager = mock(AuthManager.class);
        competitionServiceServerSide = mock(CompetitionServiceServerSide.class);
        competitionServiceUserSide = mock(CompetitionServiceUserSide.class);
        competitionCreateModel = new CompetitionCreateModel("name", GenderConstraint.NO_CONSTRAINT,
                false, false, "TUDELFT", 123L, Type.C4);
        acceptRequestModel = new AcceptRequestModel();
        joinRequestModel = new JoinRequestModel();
        competitionEditModel = new CompetitionEditModel();
        activityCancelModel = new ActivityCancelModel(123L);
        positionEntryModel = new PositionEntryModel(Position.COX);
        competitionController = new CompetitionController(authManager, competitionServiceServerSide,
                competitionServiceUserSide);
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
        when(competitionServiceServerSide.createCompetition(competitionCreateModel, new NetId(authManager.getNetId())))
                .thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.createCompetition(competitionCreateModel));
    }

    @Test
    void informUser() {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionServiceUserSide.informUser(acceptRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.informUser(acceptRequestModel));
    }

    @Test
    void joinCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionServiceUserSide.joinCompetition(joinRequestModel)).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.joinCompetition(joinRequestModel));
    }

    @Test
    void cancelCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionServiceServerSide.deleteCompetition(123L, authManager.getNetId())).thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.cancelCompetition(activityCancelModel));
    }

    @Test
    void editCompetition() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionServiceServerSide.editCompetition(competitionEditModel, authManager.getNetId()))
                .thenReturn("success");
        Assertions.assertEquals(new ResponseEntity<>("success", HttpStatus.valueOf(200)),
                competitionController.editCompetition(competitionEditModel));
    }

    @Test
    void getCompetitions() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        List<Competition> result = new ArrayList<>();
        when(competitionServiceUserSide.getSuitableCompetition(positionEntryModel)).thenReturn(result);
        Assertions.assertEquals(new ResponseEntity<>(result, HttpStatus.valueOf(200)),
                competitionController.getCompetitions(positionEntryModel));
    }
}