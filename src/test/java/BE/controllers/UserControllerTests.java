/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package BE.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import BE.entities.UserProject;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.responsemodels.user.PrivilegeModel;
import BE.responsemodels.user.ProjectListModel;
import BE.responsemodels.user.UserModel;
import BE.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

/**
 * Tests involving calls to UserController.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(new CorsFilter())
                .build();
    }

    //Initialised test helpers

    List<String> testPrivileges= Arrays.asList("username");
    List<String> testPrivileges2 = Arrays.asList("username","admin");
    List<ProjectListModel> testProject=null;
    List<UserModel> usersG = Arrays.asList(
            new UserModel("testUser","testPass","test@email.com",testProject,testPrivileges),
            new UserModel("testUser2","testPass2","test2@email.com",testProject,testPrivileges2));
    PrivilegeModel testPrivilege = new PrivilegeModel("username","standard user access", false);
    PrivilegeModel testPrivilege2 = new PrivilegeModel("admin","admin access", true);
    List<PrivilegeModel> testPrivilegeList = Arrays.asList(testPrivilege, testPrivilege2);
    UserModel testUser = new UserModel("testUserModel","testPazz","testModel@email.com",testProject,testPrivileges);

    @Test
    public void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(usersG);
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[1].username").value("testUser2"))
                .andExpect(jsonPath("$[0].password").value("testPass"))
                .andExpect(jsonPath("$[0].email").value("test@email.com"))
                //.andExpect(jsonPath("$[0].projects").value("null"))
                .andExpect(jsonPath("$[0].privileges[0]").value("username"));

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);

    }

    @Test
    public void getListOfUserPriviledges() throws Exception {
        when(userService.getAllPrivileges()).thenReturn(testPrivilegeList);
        this.mockMvc.perform(get("/user_privileges"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].privilege").value("username"))
                .andExpect(jsonPath("$[1].privilege").value("admin"))
                .andExpect(jsonPath("$[0].description").value("standard user access"))
                .andExpect(jsonPath("$[0].internal").value(false));
        verify(userService, times(1)).getAllPrivileges();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void createUser() throws Exception {
        when(userService.userExists(testUser.getUsername())).thenReturn(false);
        when(userService.createUser(testUser)).thenReturn(testUser);
        this.mockMvc.perform(post("/users/testUser?action=create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andDo(print());
                //.andExpect(status().isCreated())
                //.andExpect(header().string("location", containsString("http://localhost/users/")));
        verify(userService, times(1)).userExists(testUser.getUsername());
        verify(userService, times(1)).createUser(testUser);
        verifyNoMoreInteractions(userService);
    }

}
