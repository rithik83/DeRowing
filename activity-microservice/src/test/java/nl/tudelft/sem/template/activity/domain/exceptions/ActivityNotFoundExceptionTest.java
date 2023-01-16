package nl.tudelft.sem.template.activity.domain.exceptions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

class ActivityNotFoundExceptionTest {

    private TrainingRepository trainingRepository;

    private CompetitionRepository competitionRepository;

    private Environment environment;

    @Test
    void testActivityNotFoundException() {

    }

}