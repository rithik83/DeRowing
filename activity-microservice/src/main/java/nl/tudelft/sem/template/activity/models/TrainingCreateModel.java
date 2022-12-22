package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Type;

@Data
public class TrainingCreateModel {
    private String trainingName;
    private long startTime;
    private Type type;

    /**
     * The constructor for trainingCreateModel.
     *
     * @param trainingName The name of the training
     * @param startTime The startTime of the training
     * @param type the Type of boat
     */
    public TrainingCreateModel(String trainingName, long startTime, Type type) {
        this.trainingName = trainingName;
        this.startTime = startTime;
        this.type = type;
    }
}
