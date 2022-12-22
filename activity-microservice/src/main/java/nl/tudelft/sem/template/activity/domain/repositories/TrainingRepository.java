package nl.tudelft.sem.template.activity.domain.repositories;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, NetId> {
    /**
     * Find training by using NetId of the training owner.
     *
     * @param id the netId of the activity owner
     * @return a training which is held by its owner
     */
    Training findById(long id);

    /**
     * Check the usage of the provided netId.
     *
     * @param id the netId to be searched
     * @return a boolean value which shows whether the netId is used or not
     */
    boolean existsById(long id);


    Training findTrainingByAttendeesContains(NetId netId);

    @Transactional
    void deleteById(long id);

    @Modifying
    @Query(value = "ALTER SEQUENCE HIBERNATE_SEQUENCE restart with 1", nativeQuery = true)
    @Transactional
    void resetSequence();
}
