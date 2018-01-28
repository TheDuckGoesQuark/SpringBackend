package BE.controllers;

// Entities
import BE.services.ProjectService;
// Exceptions
import BE.entities.project.Project;
import BE.entities.project.Role;
import BE.exceptions.NotImplementedException;
// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /**
     * Gets all projects
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * @param project_name
     * @return
     * @throws NotImplementedException
     */
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public Project getProject(@PathVariable(value="project_name") String project_name) {
        return projectService.getProjectByName(project_name);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.POST)
    public Project createProject(@PathVariable(value="project_name") String project_name) {
        return projectService.createProject(project_name);
    }

    /**
     * @param project_name
     * @param project
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.PATCH)
    public Project updateProject(@PathVariable(value="project_name") String project_name, @RequestBody Project project) {
        return projectService.updateProject(project);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.DELETE)
    public Project deleteProject(@PathVariable(value="project_name") String project_name) {
        return projectService.deleteProject(project_name);
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public Role getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

}
