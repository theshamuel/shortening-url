package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.AuthController;
import com.theshamuel.shrturl.user.dao.UserRepository;
import com.theshamuel.shrturl.user.entity.User;
import com.theshamuel.shrturl.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The Auth controller tests. {@link AuthController}
 *
 * @author Alex Gladkikh
 */
public class AuthControllerTest {
    @Mock
    UserRepository userRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(userRepository)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }

    @Test
    public void testAuth() throws Exception {
        String login = "admin";
        User testUser =  User.builder().id("0001").login(login).password(Utils.pwd2sha256("123","salt")).salt("salt").author("admin").build();
        AuthController.LoginForm loginForm = new AuthController.LoginForm();
        loginForm.login = login;
        loginForm.password = "123";

        when(userRepository.findByLogin(login)).thenReturn(testUser);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(loginForm)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("admin")));

        verify(userRepository,times(1)).findByLogin(login);

    }

    @Test
    public void testAuthNotFoundUser() throws Exception {
        String login = "admin";
        AuthController.LoginForm loginForm = new AuthController.LoginForm();
        loginForm.login = login;
        loginForm.password = "123";

        when(userRepository.findByLogin(login)).thenReturn(null);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(loginForm)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",is("There is no user with this login")));

        verify(userRepository,times(1)).findByLogin(login);
    }


    @Test
    public void testAuthWrongPassword() throws Exception{
        String login = "admin";
        User testUser =  User.builder().id("0001").login(login).password("no").salt("salt").author("admin").build();
        AuthController.LoginForm loginForm = new AuthController.LoginForm();
        loginForm.login = login;
        loginForm.password = "123";

        when(userRepository.findByLogin(login)).thenReturn(testUser);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(loginForm)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",is("Wrong password")));

        verify(userRepository,times(1)).findByLogin(login);
    }


    private Map createRoles(){
        Map result = new HashMap();
        result.put("admin",0);
        result.put("operator",0);
        result.put("doctor",0);
        result.put("manager",0);
        return new HashMap<>();
    }
}
