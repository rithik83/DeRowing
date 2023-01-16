package nl.tudelft.sem.template.activity.domain.exceptions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.CompetitionServiceServerSide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


class NetIdAlreadyInUseExceptionTest {

    @Mock
    private CompetitionServiceServerSide competitionServiceServerSide;

    @Test
    void testNetIdAlreadyInUseException() throws Exception {
        competitionServiceServerSide = mock(CompetitionServiceServerSide.class);
        when(competitionServiceServerSide.createCompetition(any(), any()))
                .thenThrow(new NetIdAlreadyInUseException(new NetId("123")));
        Assertions.assertThrows(NetIdAlreadyInUseException.class, () -> {
            competitionServiceServerSide.createCompetition(null, new NetId("123"));
        });
    }

}