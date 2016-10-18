package com.henderson.simpleuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.exceptions.AlreadyExistsException;
import com.henderson.simpleuser.exceptions.NotFoundException;
import com.henderson.simpleuser.service.SimpleUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ross on 17/10/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleUserService userService;

    private JacksonTester<SimpleUser> json;

    @Before
    public void setup() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }


    @Test
    public void listUsers() throws Exception {
        long id = 1;
        String firstName = "ross";
        String lastName = "henderson";
        String nickName = "rossiar";
        String password = "ross";
        String email = "rosshenderson@gmail.com";
        String country = "UK";
        List<SimpleUser> mockUsers = new LinkedList<>();
        mockUsers.add(new SimpleUser(id, firstName, lastName, nickName, password, email, country));
        given(userService.allUsers()).willReturn(mockUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].first_name", is(firstName)))
                .andExpect(jsonPath("$[0].last_name", is(lastName)))
                .andExpect(jsonPath("$[0].nickname", is(nickName)))
                .andExpect(jsonPath("$[0].password", is(password)))
                .andExpect(jsonPath("$[0].email", is(email)))
                .andExpect(jsonPath("$[0].country.name", is(country)));
    }

    @Test
    public void createNewUser() throws Exception {
        long id = 14;
        String firstName = "ross";
        String lastName = "henderson";
        String nickName = "rossiar";
        String password = "ross";
        String email = "rosshenderson@gmail.com";
        String country = "UK";
        SimpleUser user = new SimpleUser(firstName, lastName, nickName, password, email, country);

        given(userService.createNewUser(any(SimpleUser.class)))
                .willReturn(new SimpleUser(id, firstName, lastName, nickName, password, email, country));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/users/" + id));

        verify(userService).createNewUser(any(SimpleUser.class));
    }

    @Test
    public void createNewUserAlreadyExists() throws Exception {
        String firstName = "ross";
        String lastName = "henderson";
        String nickName = "rossiar";
        String password = "ross";
        String email = "rosshenderson@gmail.com";
        String country = "UK";
        SimpleUser user = new SimpleUser(firstName, lastName, nickName, password, email, country);

        given(userService.createNewUser(any(SimpleUser.class)))
                .willThrow(new AlreadyExistsException());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson()))
                .andExpect(status().isConflict());
    }

    @Test
    public void createNewUserNotAUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"something\":\"else\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser() throws Exception {
        long id = 1;
        Long updatedId = 1234L;
        String firstName = "ross";
        String lastName = "henderson";
        String nickName = "rossiar";
        String password = "ross";
        String email = "rosshenderson@gmail.com";
        String country = "UK";
        SimpleUser user = new SimpleUser(id, firstName, lastName, nickName, password, email, country);
        given(userService.updateUser(any(SimpleUser.class)))
                .willReturn(new SimpleUser(updatedId, firstName, lastName, nickName, password, email, country));

        mockMvc.perform(put("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id", is(updatedId.intValue())));
    }

    @Test
    public void updateUserNotFound() throws Exception {
        long id = 1;
        String firstName = "ross";
        String lastName = "henderson";
        String nickName = "rossiar";
        String password = "ross";
        String email = "rosshenderson@gmail.com";
        String country = "UK";
        SimpleUser user = new SimpleUser(id, firstName, lastName, nickName, password, email, country);
        given(userService.updateUser(any(SimpleUser.class))).willThrow(new NotFoundException());

        mockMvc.perform(put("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson()))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateUserNotAUser() throws Exception {
        long id = 1;
        mockMvc.perform(put("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"something\":\"else\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void removeUser() throws Exception {
        long id = 1;

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeUserNotFound() throws Exception {
        long id = 1;
        doThrow(new NotFoundException()).when(userService).removeUser(id);

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isNotFound());
    }
}
