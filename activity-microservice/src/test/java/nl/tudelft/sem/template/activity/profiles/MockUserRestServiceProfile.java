package nl.tudelft.sem.template.activity.profiles;

import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockUserRestService")
@Configuration
public class MockUserRestServiceProfile {

    @Bean
    @Primary
    public UserRestService getMockUserRestService() {
        return Mockito.mock(UserRestService.class);
    }
}
