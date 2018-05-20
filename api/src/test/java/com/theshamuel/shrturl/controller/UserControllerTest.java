package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.UserController;
import com.theshamuel.shrturl.user.dao.UserRepository;
import com.theshamuel.shrturl.user.entity.User;
import com.theshamuel.shrturl.user.entity.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The User controller tests. {@link UserController}
 *
 * @author Alex Gladkikh
 */
public class UserControllerTest {

    /**
     * The User repository.
     */
    @Mock
    UserRepository userRepository;

    /**
     * The Mock mvc.
     */
    MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userRepository)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }


    /**
     * Test find user.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testFindUser() throws Exception {
        String id = "0001";
        User testUser =  new UserBuilder().id(id).login("admin").password("123").salt("salt").author("admin").build();

        testUser.setId(id);
        when(userRepository.findOne(id)).thenReturn(testUser);

        mockMvc.perform(get("/api/v1/users/"+id).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(7)));

        verify(userRepository,times(1)).findOne(id);
    }

    /**
     * Test find user not found entity exceptions.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testFindUserNotFoundEntityException() throws Exception {
        User testUser = null;
        String id = "00001";
        when(userRepository.findOne(id)).thenReturn(testUser);

        mockMvc.perform(get("/api/v1/users/00001").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath("$.message", is("User with id ["+id+"] has not found")));


        verify(userRepository,times(1)).findOne(id);
    }

    /**
     * Test save user.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testSaveUser() throws Exception {
        final User testUser = new UserBuilder().login("admin").password("123").salt("salt").author("admin").build();

        User resultUser = new UserBuilder().id("111").login("admin").password("123").salt("salt").author("admin").build();

        when(userRepository.save(testUser)).thenReturn(resultUser);

        mockMvc.perform(post("/api/v1/users/").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(testUser)))
                .andExpect(status().isCreated());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository,times(1)).save(userCaptor.capture());
    }

    /**
     * Test save user duplicate entity exceptions.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testSaveUserDuplicateEntityException() throws Exception {
        User testUser = new UserBuilder().id("111").login("admin").password("123").salt("salt").author("admin").build();

        String login = "admin";
        when(userRepository.findByLogin(login)).thenReturn(testUser);

        mockMvc.perform(post("/api/v1/users/").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(testUser)))
                .andExpect(status().isConflict());


        verify(userRepository,times(1)).findByLogin(login);
    }


    /**
     * Test delete user.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testDeleteUser() throws Exception {
        final User testUser = new UserBuilder().id("111").login("admin").password("123").salt("salt").author("admin").build();

        when(userRepository.findOne(testUser.getId())).thenReturn(testUser);

        mockMvc.perform(delete("/api/v1/users/"+testUser.getId()).accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository,times(1)).findOne(testUser.getId());

    }

    /**
     * Test delete user not found.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testDeleteUserNotFound() throws Exception {

        when(userRepository.findOne("111")).thenReturn(null);

        mockMvc.perform(delete("/api/v1/users/111").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isNotFound());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository,never()).delete(userCaptor.capture());
        verify(userRepository,times(1)).findOne("111");

    }

    /**
     * Test get users order by login asc.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testGetUsersOrderByLoginASC() throws Exception {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new UserBuilder().login("admin").password("123").salt("salt").author("admin").build());
        expectedList.add(new UserBuilder().login("evgit").password("123").salt("salt").author("admin").build());
        when(userRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"login")))).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/users/").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath( "$[0].login",is("admin")))
                .andExpect(jsonPath( "$[1].login",is("evgit")));
        verify(userRepository,times(1)).findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"login")));

    }


    /**
     * Test get users with invalid claims.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testGetUsersWithInvalidClaims() throws Exception {
        mockMvc.perform(get("/api/v1/users/").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getUserClaims()))
                .andExpect(status().isForbidden());
    }

    /**
     * Test delete user with invalid claims.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testDeleteUserWithInvalidClaims() throws Exception {
        mockMvc.perform(delete("/api/v1/users/123").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getUserClaims()))
                .andExpect(status().isForbidden());
    }

    /**
     * Test get users order by login desc.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testGetUsersOrderByLoginDESC() throws Exception {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new UserBuilder().login("evgit").password("123").salt("salt").author("admin").build());
        expectedList.add(new UserBuilder().login("admin").password("123").salt("salt").author("admin").build());
        when(userRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"login")))).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/users/").accept(MediaType.APPLICATION_JSON_UTF8).param("sort","DESC").requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath( "$[0].login",is("evgit")))
                .andExpect(jsonPath( "$[1].login",is("admin")));
        verify(userRepository,times(1)).findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"login")));

    }
}
