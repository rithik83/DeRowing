package nl.tudelft.sem.template.activity.authentication;

public interface AuthInterface {
    /**
     * Interfaces with spring security to get the name of the user in the current context.
     *
     * @return The name of the user.
     */
    String getNetId();
}
