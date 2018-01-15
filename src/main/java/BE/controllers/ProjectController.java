package BE.controllers;

import java.util.List;

// Exceptions
import BE.exceptions.NotImplementedException;
// Models
import BE.models.project.ProjectModel;
import BE.models.project.RoleModel;
import BE.models.user.UserModel;
// Spring
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    /**
     * Gets all projects
     * @return a list of all projects
     */
    @RequestMapping("/projects")
    public List<ProjectModel> getAllProjects() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    /**
     * Gets a project based on project name
     * @param project_name name of project to retrieve
     * @return project with requested name
     */
    @RequestMapping("/projects/{project_name}")
    public UserModel getUser(@PathVariable(value="project_name") String project_name) throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping("/project_roles")
    public RoleModel getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

}
