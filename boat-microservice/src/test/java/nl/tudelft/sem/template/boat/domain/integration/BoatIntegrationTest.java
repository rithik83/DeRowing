package nl.tudelft.sem.template.boat.domain.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.Type;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.models.BoatDeleteModel;
import nl.tudelft.sem.template.boat.models.BoatEmptyPositionsModel;
import nl.tudelft.sem.template.boat.models.BoatListModel;
import nl.tudelft.sem.template.boat.models.BoatRowerEditModel;
import nl.tudelft.sem.template.boat.repositories.BoatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
@AutoConfigureMockMvc
public class BoatIntegrationTest {
    @Autowired
    private transient AuthManager mockAuthenticationManager;
    @Autowired
    private transient JwtTokenVerifier mockTokenVerifier;
    @Autowired
    private transient MockMvc mvc;
    @Autowired
    private transient BoatRepository boatRepository;
    
    private final transient String authorization = "Authorization";
    
    private final transient String bearerMockedToken = "Bearer MockedToken";
    
    private final transient String createUrl = "/boat/create";

    /**
     * The setup for each test, its main purpose being to reset the database.
     */
    @BeforeEach
    public void setup() {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        boatRepository.deleteAll();
        boatRepository.resetSequence();
    }

    /**
     * Method which serializez an object.
     *
     * @param obj the desired object
     * @return the serialization of the object
     */
    public String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("Serialization error");
        }
        return null;
    }

    @Test
    public void createBoatTest() throws Exception {
        BoatCreateModel body = new BoatCreateModel(Type.C4);
        ResultActions res = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header(authorization, bearerMockedToken));
        String response = res.andReturn().getResponse().getContentAsString();
        res.andExpect(status().isOk());
        BoatDeleteModel sol = new ObjectMapper().readValue(response, BoatDeleteModel.class);
        assertEquals(1, sol.getBoatId());
        Optional<Boat> storedBoat = boatRepository.findById(1);
        assertEquals(Type.C4, storedBoat.get().getType());
    }

    @Test
    public void deleteBoatTest() throws Exception {
        BoatCreateModel body1 = new BoatCreateModel(Type.C4);
        ResultActions res1 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body1))
                .header(authorization, bearerMockedToken));
        res1.andExpect(status().isOk());
        BoatDeleteModel body2 = new BoatDeleteModel(1);
        assertEquals(1, boatRepository.findAll().size());
        ResultActions res2 = mvc.perform(post("/boat/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body2))
                .header(authorization, bearerMockedToken));
        res2.andReturn();
        res2.andExpect(status().isOk());
        assertEquals(0, boatRepository.findAll().size());
    }

    @Test
    public void insertRowerTest() throws Exception {
        BoatCreateModel body1 = new BoatCreateModel(Type.PLUS4);
        ResultActions res1 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body1))
                .header(authorization, bearerMockedToken));
        res1.andExpect(status().isOk());
        Optional<Boat> storedBoat = boatRepository.findById(1);
        assertEquals(1, storedBoat.get().getRequiredRowers().get(Position.COX));
        NetId netId = new NetId("a");
        BoatRowerEditModel body2 = new BoatRowerEditModel(1, Position.COX, netId);
        assertEquals(1, boatRepository.findAll().size());
        ResultActions res2 = mvc.perform(post("/boat/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body2))
                .header(authorization, bearerMockedToken));
        res2.andReturn();
        res2.andExpect(status().isOk());
        storedBoat = boatRepository.findById(1);
        assertEquals(0, storedBoat.get().getRequiredRowers().get(Position.COX));
        assertEquals(1, storedBoat.get().getRowers().get(Position.COX).size());
        assertEquals(netId, storedBoat.get().getRowers().get(Position.COX).get(0));
    }

    @Test
    public void removeRowerTest() throws Exception {
        BoatCreateModel body1 = new BoatCreateModel(Type.PLUS4);
        ResultActions res1 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body1))
                .header(authorization, bearerMockedToken));
        res1.andExpect(status().isOk());
        Optional<Boat> storedBoat = boatRepository.findById(1);
        assertEquals(1, storedBoat.get().getRequiredRowers().get(Position.COX));
        NetId netId = new NetId("a");
        BoatRowerEditModel body2 = new BoatRowerEditModel(1, Position.COX, netId);
        assertEquals(1, boatRepository.findAll().size());
        ResultActions res2 = mvc.perform(post("/boat/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body2))
                .header(authorization, bearerMockedToken));
        res2.andReturn();
        res2.andExpect(status().isOk());
        storedBoat = boatRepository.findById(1);
        assertEquals(0, storedBoat.get().getRequiredRowers().get(Position.COX));
        assertEquals(1, storedBoat.get().getRowers().get(Position.COX).size());
        assertEquals(netId, storedBoat.get().getRowers().get(Position.COX).get(0));
        ResultActions res3 = mvc.perform(post("/boat/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body2))
                .header(authorization, bearerMockedToken));
        res3.andReturn();
        res3.andExpect(status().isOk());
        storedBoat = boatRepository.findById(1);
        assertEquals(1, storedBoat.get().getRequiredRowers().get(Position.COX));
    }

    @Test
    public void findBoatsByPosition() throws Exception {
        BoatCreateModel body1 = new BoatCreateModel(Type.C4);
        ResultActions res1 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body1))
                .header(authorization, bearerMockedToken));
        res1.andExpect(status().isOk());

        BoatCreateModel body2 = new BoatCreateModel(Type.PLUS4);
        ResultActions res2 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body2))
                .header(authorization, bearerMockedToken));
        res2.andExpect(status().isOk());

        BoatCreateModel body3 = new BoatCreateModel(Type.PLUS8);
        ResultActions res3 = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body3))
                .header(authorization, bearerMockedToken));
        res3.andExpect(status().isOk());

        List<Long> desiredList = new ArrayList<>();
        desiredList.add((long) 1);
        desiredList.add((long) 2);
        desiredList.add((long) 3);
        BoatEmptyPositionsModel body4 = new BoatEmptyPositionsModel(desiredList, Position.COX);
        ResultActions res4 = mvc.perform(post("/boat/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body4))
                .header(authorization, bearerMockedToken));
        String response4 = res4.andReturn().getResponse().getContentAsString();
        res4.andExpect(status().isOk());
        BoatListModel sol = new ObjectMapper().readValue(response4, BoatListModel.class);
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 2);
        expectedList.add((long) 3);
        assertEquals(expectedList, sol.getBoatId());
    }

    @Test
    public void createNullBoatTest() throws Exception {
        ResultActions res = mvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(null))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteNullBoatTest() throws Exception {
        ResultActions res = mvc.perform(post("/boat/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(null))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }

    @Test
    public void insertNullRowerTest() throws Exception {
        ResultActions res = mvc.perform(post("/boat/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(null))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }

    @Test
    public void removeNullRowerTest() throws Exception {
        ResultActions res = mvc.perform(post("/boat/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(null))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }

    @Test
    public void findNullBoatsByPosition() throws Exception {
        ResultActions res = mvc.perform(post("/boat/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(null))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }

    @Test
    public void findErrorBoatsByPosition() throws Exception {
        List<Long> desiredList = new ArrayList<>();
        desiredList.add((long) 1);
        desiredList.add((long) 2);
        desiredList.add((long) 3);
        BoatEmptyPositionsModel body = new BoatEmptyPositionsModel(desiredList, Position.COX);
        ResultActions res = mvc.perform(post("/boat/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header(authorization, bearerMockedToken));
        res.andReturn();
        res.andExpect(status().is4xxClientError());
    }
}
