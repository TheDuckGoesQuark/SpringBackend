package BE.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.UserListModel;
import BE.services.ProjectService;
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
public class ProjectControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(projectController)
                .addFilters(new CorsFilter())
                .build();
    }

    @Test
    public void getAllProjects() throws Exception {
        List<UserListModel> userList = Arrays.asList(
                new UserListModel("testUserListModel1","testAccess1"),
                new UserListModel("testUserListModel2", "testAccess2"));
        List<ProjectModel> projectList = Arrays.asList(
                new ProjectModel("testProject1", userList),
                new ProjectModel("testProject2", userList)
        );
        when(projectService.getAllProjects()).thenReturn(projectList);
        mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].project_name").value("testProject1"))
                .andExpect(jsonPath("$[1].project_name").value("testProject2"))
                .andExpect(jsonPath("$[0].users[0].username").value("testUserListModel1"))
                .andExpect(jsonPath("$[0].users[1].username").value("testUserListModel2"))
                .andExpect(jsonPath("$[0].users[1].access_level").value("testAccess2"));
        verify(projectService, times(1)).getAllProjects();
        verifyNoMoreInteractions(projectService);
    }

    @Test
    public void getProject() throws Exception {
        List<UserListModel> userList = Arrays.asList(
                new UserListModel("testUserListModel1","testAccess1"),
                new UserListModel("testUserListModel2", "testAccess2"));
        ProjectModel testProject = new ProjectModel("testProjectModel1", userList);

        when(projectService.getProjectByName(testProject.getProject_name())).thenReturn(testProject);
        mockMvc.perform(get("/projects/{project_name}", testProject.getProject_name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project_name").value("testProjectModel1"));
        verify(projectService, times(1)).getProjectByName(testProject.getProject_name());
        verifyNoMoreInteractions(projectService);
    }

    @Test
    public void createProject() throws Exception {
        //TODO
    }

    @Test
    public void updateProject() throws Exception {
        //TODO
    }

    @Test
    public void deleteProject() throws Exception {
        //TODO
    }

    @Test
    public void updateGrant() throws Exception {
        //TODO
    }

    @Test
    public void getProjectRoles() throws Exception {
        //TODO
    }

    @Test
    public void getAllFiles() throws Exception {
        //TODO
    }

    @Test
    public void getFile() throws Exception {
        //TODO
    }

    @Test
    public void getFileById() throws Exception {
        //TODO
    }

    @Test
    public void updateFile() throws Exception {
        //TODO
    }

    @Test
    public void deleteFile() throws Exception {
        //TODO
    }

    @Test
    public void getDirContents() throws Exception {
        //TODO
    }

    @Test
    public void createFile() throws Exception {
        //TODO
    }

    @Test
    public void upload() throws Exception {
        //TODO
    }


}





