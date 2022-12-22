package nl.tudelft.sem.template.activity.domain.entities;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;

class TrainingTest {

    private Training test;

    @BeforeEach
    public void setup() {
        test = new Training(new NetId("123"), "name", 123L, 123L,
                Type.C4);
    }
}