package nl.tudelft.sem.template.activity.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Authentication Manager.
 */
@Component
public class AuthManager implements AuthInterface {
    /**
     * Interfaces with spring security to get the name of the user in the current context.
     *
     * @return The name of the user.
     */
    @Override
    public String getNetId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
