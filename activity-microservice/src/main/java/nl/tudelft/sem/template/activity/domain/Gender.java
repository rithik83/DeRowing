package nl.tudelft.sem.template.activity.domain;

public enum Gender {
    MALE(1),
    FEMALE(2);
    public final int value;
    Gender(int value) {
        this.value = value;
    }
}
