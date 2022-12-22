package nl.tudelft.sem.template.boat.domain;

import lombok.Getter;

@Getter
public enum Position {
    COX(1),
    COACH(2),
    PORT(3),
    STARBOARD(4),
    SCULLING(5);

    public final int value;

    Position(int value) {
        this.value = value;
    }
}
