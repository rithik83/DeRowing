package nl.tudelft.sem.template.activity.domain.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;

class TrainingRepositoryTest {

    @Mock
    private TrainingRepository trainingRepository;

    private Training training;

    @BeforeEach
    public void setup() {
        training = new Training(new NetId("123"), "testname", 123L, 123L, Type.C4);
        trainingRepository = mock(TrainingRepository.class);
    }

    @Test
    void findByNetId() {
        when(trainingRepository.findById(any())).thenReturn(Optional.ofNullable(training));
        Assertions.assertEquals(null, trainingRepository.findById(123L));
    }

    @Test
    void existsByNetId() {
        when(trainingRepository.existsById(any())).thenReturn(false);
        Assertions.assertFalse(trainingRepository.existsById(123L));
    }
}