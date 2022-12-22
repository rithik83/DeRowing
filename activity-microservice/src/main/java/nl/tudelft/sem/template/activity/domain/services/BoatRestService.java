package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionModel;
import nl.tudelft.sem.template.activity.models.FindSuitableCompetitionResponseModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatRestService extends RestService {
    // Here we can create methods, that use the parent class' performRequest()
    // to get information from other microservices
    private final transient Environment environment;
    private transient String boatUrl = "boat.url";
    private transient String boatPort = "boat.port";

    @Autowired
    public BoatRestService(Environment environment) {
        this.environment = environment;
    }


    /**
     * A method to inform the boat service of an extra user.
     *
     * @param model the model to insert a user into a boat
     * @return boolean whether the request was successful
     */
    public boolean informBoatOfJoining(BoatChangeEvent model) {
        String url = environment.getProperty(boatUrl);
        int port = Integer.parseInt(environment.getProperty(boatPort));
        try {
            performRequest(model, url, port, "/boat/insert", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return false;
        }
    }

    /**
     * Tells the boat service to create a new boat.
     *
     * @param type      the type of boat to create
     * @return the boat id of the newly creted boat.
     */
    public long getBoatId(Type type) {
        String url = environment.getProperty(boatUrl);
        int port = Integer.parseInt(environment.getProperty(boatPort));
        CreateBoatModel model = new CreateBoatModel();
        model.setType(type);
        try {
            Object genericResponse = performRequest(model, url, port, "/boat/create", HttpMethod.POST);
            return (genericResponse != null)
                    ? ((CreateBoatResponseModel) deserialize(genericResponse, CreateBoatResponseModel.class)).getBoatId()
                    : -1;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return -1;
        }
    }

    /**
     * Checks if competitions are available for the specific position of the boat.
     *
     * @param competitions The list of all competitions
     * @param position     The position to check for
     * @return a list of competitions, or an empty list if no competitions available
     */
    public List<Competition> checkIfPositionAvailable(List<Competition> competitions, Position position) {
        String url = environment.getProperty(boatUrl);
        int port = Integer.parseInt(environment.getProperty(boatPort));
        FindSuitableCompetitionModel model = new FindSuitableCompetitionModel(competitions, position);
        try {
            FindSuitableCompetitionResponseModel response =
                    (FindSuitableCompetitionResponseModel)
                            performRequest(model, url, port, "/boat/check", HttpMethod.POST);
            return response.getCompetitions();
        } catch (UnsuccessfulRequestException e) {
            System.out.println("There is no such competition that you are suitable for");
            return new ArrayList<>();
        }
    }

    /**
     * The method to delete a boat when delete an activity.
     *
     * @param boatDeleteModel the id of the boat to be deleted
     * @return a boolean value representing the result
     */
    public boolean deleteBoat(BoatDeleteModel boatDeleteModel) {
        String url = environment.getProperty(boatUrl);
        int port = Integer.parseInt(environment.getProperty(boatPort));
        try {
            performRequest(boatDeleteModel, url, port, "/boat/delete", HttpMethod.POST);
            return true;
        } catch (UnsuccessfulRequestException e) {
            System.out.println("Boat microservice seems unavailable");
            return false;
        }
    }
}
