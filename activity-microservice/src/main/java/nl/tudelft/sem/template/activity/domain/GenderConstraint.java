package nl.tudelft.sem.template.activity.domain;

public enum GenderConstraint {
    ONLY_MALE(1),
    ONLY_FEMALE(2),
    NO_CONSTRAINT(3);
    public final int value;
    GenderConstraint(int value) {
        this.value = value;
    }
}
