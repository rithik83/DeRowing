package nl.tudelft.sem.template.boat.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.boat.domain.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer> {

    /**
     * Method which resets the database id to 1. This is used for testing.
     */
    @Modifying
    @Query(value = "ALTER SEQUENCE HIBERNATE_SEQUENCE restart with 1", nativeQuery = true)
    @Transactional
    void resetSequence();

    /**
     * Method that returns all elements stored in the repository.
     *
     * @return all elements in the repository
     */
    List<Boat> findAll();

    /**
     * Find a Boat given an ID.
     *
     * @param id the ID of the Boat
     * @return the boat that has this ID
     */
    Optional<Boat> findById(Integer id);
}
