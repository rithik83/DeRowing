package nl.tudelft.sem.template.activity.profiles;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockApplicationEventPublisher")
@Configuration
public class MockApplicationEventPublisherProfile {

    /**
     * Mocks the ApplicationEventPublisher.
     *
     * @return A mocked ApplicationEventPublisher.
     */
    @Bean
    @Primary  // marks this bean as the first bean to use when trying to inject an AuthenticationManager
    public ApplicationEventPublisher getMockApplicationEventPublisher() {
        return Mockito.mock(ApplicationEventPublisher.class);
    }
}
