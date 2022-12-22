package nl.tudelft.sem.template.activity.profiles;

import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockBoatRestService")
@Configuration
public class MockBoatRestServiceProfile {

    @Bean
    @Primary
    public BoatRestService getMockBoatRestService() {
        return Mockito.mock(BoatRestService.class);
    }
}
