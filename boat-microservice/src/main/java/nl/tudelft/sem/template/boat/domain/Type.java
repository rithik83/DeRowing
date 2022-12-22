package nl.tudelft.sem.template.boat.domain;

import lombok.Getter;

@Getter
public enum Type {
    C4(1),
    PLUS4(2),
    PLUS8(3);

    public final int value;

    Type(int value) {
        this.value = value;
    }
}
