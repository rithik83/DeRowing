package nl.tudelft.sem.template.activity.domain.repositories;

import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, NetId> {
    /**
     * Find competition by using NetId of the competition owner.
     *
     * @param id the netId of the activity owner
     * @return an activity which is held by its owner
     */
    Competition findById(long id);

    NetId findOwnerById(long id);

    /**
     * Check the usage of the provided netId.
     *
     * @param id the netId to be searched
     * @return a boolean value which shows whether the netId is used or not
     */
    boolean existsById(long id);


    List<Competition> findAllByBoatIdIn(List<Long> ids);

    @Transactional
    void deleteById(long id);

    @Modifying
    @Query(value = "ALTER SEQUENCE HIBERNATE_SEQUENCE restart with 1", nativeQuery = true)
    @Transactional
    void resetSequence();

    @Query(value = "SELECT * FROM COMPETITIONS c WHERE c.allowAmateurs = ?3 "
            + "AND c.genderConstraint = ?1 AND c.organization = ?2", nativeQuery = true)
    List<Competition> findSuitableCompetitions(Gender gender, String organization, boolean isAmateur);
}
