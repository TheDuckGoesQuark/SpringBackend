package BE.controllers;

// Entities
import BE.entities.project.File;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;
import BE.services.FileService;
import BE.services.ProjectService;
// Exceptions
import BE.entities.project.Project;
import BE.entities.project.Role;
import BE.exceptions.NotImplementedException;
// Spring
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    /**
     * Gets all projects
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectModel> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * @param project_name
     * @return
     * @throws NotImplementedException
     */
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public ProjectModel getProject(@PathVariable(value="project_name") String project_name) {
        return projectService.getProjectByName(project_name);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", params = {"action=create"}, method = RequestMethod.POST)
    public ProjectModel createProject(@PathVariable(value="project_name") String project_name) {
        return projectService.createProject(project_name);
    }

    /**
     * @param project_name
     * @param project
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", params = {"action=update"}, method = RequestMethod.POST)
    public ProjectModel updateProject(@PathVariable(value="project_name") String project_name, @RequestBody ProjectModel project) {
        return projectService.updateProject(project);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", params = {"action=delete"}, method = RequestMethod.POST)
    public void deleteProject(@PathVariable(value="project_name") String project_name) {
        projectService.deleteProject(project_name);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", params = {"action=update_grant"}, method = RequestMethod.POST)
    public void updateGrant(@PathVariable(value="project_name") String project_name, @RequestBody UserListModel userListModel) {
        projectService.updateGrant(project_name, userListModel);
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public List<ProjectRoleModel> getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

}
