package BE.controllers;


// Exceptions
import BE.exceptions.NotImplementedException;
// Models
import BE.entities.user.User;
// Spring
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

/*    *//**
     * Gets all projects
     * @return a list of all projects
     *//*
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectModel> getAllProjects() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    *//**
     * Gets a Project based on Project name
     * @param project_name name of Project to retrieve
     * @return Project with requested name
     *//*
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public User getProject(@PathVariable(value="project_name") String project_name) throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public RoleModel getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }*/

}
