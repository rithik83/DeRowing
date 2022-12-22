package nl.tudelft.sem.template.activity.domain.repositories;

import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    Competition findCompetitionByAttendeesContains(NetId netId);

    List<Competition> getAllCompetition();

    List<Competition> findAllCompetitionByBoatIds(List<Long> boatIds);
}
