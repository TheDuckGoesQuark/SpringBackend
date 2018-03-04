package BE.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.UserListModel;
import BE.services.FileService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

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

    @Mock
    private FileService fileService;

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
        List<UserListModel> userList = Arrays.asList(
                new UserListModel("testUserListModel1","testAccess1"),
                new UserListModel("testUserListModel2", "testAccess2"));
        ProjectModel testProject = new ProjectModel("testProjectModel1", userList);

        when(projectService.createProject(testProject.getProject_name())).thenReturn(testProject);
        mockMvc.perform(post("/project/{project_name}?action=create", testProject.getProject_name())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProject)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(projectService, times(1)).createProject(testProject.getProject_name());
        verifyNoMoreInteractions(projectService);
    }

    @Test
    public void updateProject() throws Exception {
        List<UserListModel> userList = Arrays.asList(
                new UserListModel("testUserListModel1","testAccess1"),
                new UserListModel("testUserListModel2", "testAccess2"));
        ProjectModel testProject = new ProjectModel("testProjectModel1", userList);

        when(projectService.updateProject(testProject)).thenReturn(testProject);
        mockMvc.perform(post("/project/{project_name}?action=update", testProject.getProject_name())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProject)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(projectService, times(1)).updateProject(testProject);
        verifyNoMoreInteractions(projectService);
        //TODO extra testing on comparisons of update
    }

    @Test
    public void deleteProject() throws Exception {
        when(projectService.deleteProject("testProject")).thenReturn(null);
        mockMvc.perform(post("/project/testProject?action=delete"))
                .andExpect(status().isOk());
        verify(projectService, times(1)).deleteProject("testProject");
        verifyNoMoreInteractions(projectService);
        //TODO testing if once a project is created that it is deleted
    }

    @Test
    public void updateGrant() throws Exception {
        List<UserListModel> userList = Arrays.asList(
                new UserListModel("testUserListModel1","testAccess1"),
                new UserListModel("testUserListModel2", "testAccess2"));
        ProjectModel testProject = new ProjectModel("testProjectModel1", userList);
        UserListModel userListModel = new UserListModel("testUserListModel3", "testAccess3");

        when(projectService.updateGrant(testProject.getProject_name(), userListModel)).thenReturn(null);
        //TODO after implemented
    }

    @Test
    public void getProjectRoles() throws Exception {
        // TODO when(projectService.getProjectRoles())
    }

    /******************************************FILE TESTS***************************************************/
/*


    @Test
    public void getAllMetaFiles() throws Exception {
        MetaFile testMetaFile = new MetaFile("/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
        List<FileModel> listFileModel = Arrays.asList(fileModel);
        when (fileService.getAllMetaFiles("project")).thenReturn(listFileModel);
        mockMvc.perform(get("/projects/project/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].path").value("/project/"))
                .andExpect(jsonPath("$[0].file_name").value("testFileModel"))
                .andExpect(jsonPath("$[0].file_id").value(99))
                .andExpect(jsonPath("$[0].views[0].view").value("testView"))
                .andExpect(jsonPath("$[0].metadata").value("testMetaDataModel"))
                .andExpect(jsonPath("$[0].type").value("testTypeModel"))
                .andExpect(jsonPath("$[0].status").value("testStatusModel"));
        verify(fileService, times(1)).getAllMetaFiles("project");
        verifyNoMoreInteractions(fileService);
    }

    @Test
    public void getFile() throws Exception {
        MetaFile testMetaFile = new MetaFile("/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
        when(fileService.getFile("project", "/project/"));
        mockMvc.perform(get("/projects/project/files/**"));
        //TODO currently confused about URL form, and method still needing to be further implemented
    }

    @Test
    public void getFileById() throws Exception {
        MetaFile testMetaFile = new MetaFile("/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
        when(fileService.getFileMetaByID("project", 99)).thenReturn(fileModel);
        mockMvc.perform(get("/projects/project/files_by_id/99"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/project/"))
                .andExpect(jsonPath("$.file_name").value("testFileModel"))
                .andExpect(jsonPath("$.file_id").value(99))
                .andExpect(jsonPath("$.views[0].view").value("testView"))
                .andExpect(jsonPath("$.metadata").value("testMetaDataModel"))
                .andExpect(jsonPath("$.type").value("testTypeModel"))
                .andExpect(jsonPath("$.status").value("testStatusModel"));
        verify(fileService, times(1)).getFileMetaByID("project",99);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    public void updateFileMeta() throws Exception {
        MetaFile testMetaFile = new MetaFile("/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");

        when(fileService.updateFileMeta(testMetaFile)).thenReturn(fileModel);
        mockMvc.perform(patch("/project/project/testMetaFile"))
                //TODO the url is wrong, need fix
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/project/"));
        verify(fileService, times(1)).updateFileMeta(testMetaFile);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    public void deleteMetaFile() throws Exception {
        MetaFile testMetaFile = new MetaFile("/projects/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/projects/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
        when(fileService.deleteMetaFile("project", "/project/")).thenReturn(fileModel);
        mockMvc.perform(delete("/projects/project/"))
                //TODO where am I selecting the file?
                .andExpect(status().isOk());
        verify(fileService, times(1)).deleteMetaFile("project", "/projects/project/");
        verifyNoMoreInteractions(fileService);
    }

    @Test
    public void getDirContents() throws Exception {
        MetaFile testMetaFile = new MetaFile("/projects/project/","testMetaFile","testFileType","testStatus","testMetaData", last_modified, length, supported_views);
        List<SupportedView> listSupportedViews = Arrays.asList(
                new SupportedView(testMetaFile, "testView")
        );
        FileModel fileModel = new FileModel("/projects/project/", "testFileModel", 99
                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
        List<FileModel> fileModelList = Arrays.asList(fileModel);
        when(fileService.getFile("project", "/projects/project/")).thenReturn(fileModel);
        when(fileService.getChildrenMeta("project", "/projects/project/")).thenReturn(fileModelList);
        mockMvc.perform(get("/projects/project/?view?include_children"))
                .andDo(print())
                .andExpect(status().isOk());
        //verify(fileService, times(1)).getFile("project", "/projects/project/");
        //verify(fileService, times(1)).getChildrenMeta("project", "/projects/project/");
        //TODO doesnt seem to be calling appropriate methods, or testing incorrectly
        verifyNoMoreInteractions(fileService);
    }

//    @Test
//    public void createMetaFile() throws Exception {
//        MetaFile testFile = new MetaFile("/projects/project/","testFile","testFileType","testStatus","testMetaData");
//        List<SupportedView> listSupportedViews = Arrays.asList(
//                new SupportedView(testFile, "testView")
//        );
//        FileModel fileModel = new FileModel("/projects/project/", "testFileModel", 99
//                , listSupportedViews, "testMetaDataModel", "testTypeModel", "testStatusModel");
//        when(fileService.createMetaFile("project", "/projects/project/")).thenReturn(fileModel);
//        mockMvc.perform(post("/projects/project/"))
//                .andDo(print())
//                .andExpect(status().isCreated());
//        verify(fileService, times(1)).createMetaFile("project", "/projects/project/");
//        verifyNoMoreInteractions(fileService);
//    }

    @Test
    public void upload() throws Exception {
        //TODO
    }

*/
}
