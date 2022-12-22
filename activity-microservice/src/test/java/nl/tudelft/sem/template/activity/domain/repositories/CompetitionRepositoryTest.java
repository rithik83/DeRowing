package nl.tudelft.sem.template.activity.domain.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class CompetitionRepositoryTest {

    @Mock
    private CompetitionRepository competitionRepository;

    private Competition competition;

    @Mock
    private AuthManager authManager;

    private NetId id;

    @BeforeEach
    public void setup() {
        id = new NetId("123");
        authManager = mock(AuthManager.class);
        when(authManager.getNetId()).thenReturn("123");
        competition = new Competition(id, "name", 123L, 123L,
                false, GenderConstraint.ONLY_MALE, false, "TUDELFT", Type.C4);
        competitionRepository = mock(CompetitionRepository.class);
    }

    @Test
    void findByNetId() {
        when(competitionRepository.existsById(any())).thenReturn(true);
        when(competitionRepository.findById(any())).thenReturn(Optional.ofNullable(competition));
        Assertions.assertEquals(null, competitionRepository.findById(123L));
    }

    @Test
    void existsByNetId() {
        when(this.competitionRepository.existsById(any())).thenReturn(false);
        Assertions.assertFalse(competitionRepository.existsById(123L));
    }
}