package nl.tudelft.sem.template.boat.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.boat.builders.Director;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.Type;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.repositories.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BoatService extends RestService {
    private BoatRepository boatRepository;

    private Director director;

    @Autowired
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
        this.director = new Director();
    }

    /**
     * Method parse a requestBody.

     * @param request the request body
     * @return a new Boat
     */
    public Boat parseRequest(BoatCreateModel request) {
        Type type = request.getType();
        return director.constructBoat(type);
    }


    /**
     * Method to create and persist a new Boat.

     * @param request the request body
     * @return a new Boat
     * @throws Exception the already using this netId exception
     */
    public Boat createBoat(BoatCreateModel request) throws Exception {
        try {
            Boat boat = parseRequest(request);
            boatRepository.save(boat);
            return boat;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Something went wrong in createBoat");
        }
    }
    /**
     * A method to find a boat by ID from the database.

     * @param id the ID of the boat
     * @return the Boat with that id
     * @throws Exception the boat not found exception
     */
    public Boat findBoatById(Integer id) throws Exception {
        try {
            Optional<Boat> boatOptional = boatRepository.findById(id);

            return boatOptional.orElse(null);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findBoatsById");
        }
    }

    /**
     * A method which updates a certain boat, when a rower has been inserted or removed.

     * @param boat the updated boat
     */
    public void updateBoat(Boat boat) {
        boatRepository.save(boat);
    }

    /**
     * A method which deletes a certain boat.

     * @param boat the boat which needs to be deleted
     */
    public void deleteBoat(Boat boat) {
        boatRepository.delete(boat);
    }

    /**
     * A method which finds the boats that have certain positions available.
     *
     * @param requiredPositions the number of positions of each type that are available
     * @return a list of boats that fulfill the requirements
     */
    public List<Boat> findBoatsByEmptyPositions(Map<Position, Integer> requiredPositions) throws Exception {
        try {
            List<Boat> result = new ArrayList<>();

            // iterate through all boats in the DB
            List<Boat> boats = boatRepository.findAll();
            for (Boat boat : boats) {
                // check the number available positions for each of them
                for (Position p : Position.values()) {
                    if (requiredPositions.get(p) <= boat.getRequiredRowers().get(p)) {
                        result.add(boat);
                    }
                }
            }

            return result;
        } catch (Exception e) {
            throw new Exception("Something went wrong in findBoatsByEmptyPositions");
        }
    }
}
