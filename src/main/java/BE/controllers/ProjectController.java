package BE.controllers;

// Entities
import BE.entities.project.File;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
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

    @Autowired
    FileService fileService;

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
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.POST)
    public ProjectModel createProject(@PathVariable(value="project_name") String project_name) {
        return projectService.createProject(project_name);
    }

    /**
     * @param project_name
     * @param project
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.PATCH)
    public ProjectModel updateProject(@PathVariable(value="project_name") String project_name, @RequestBody ProjectModel project) {
        return projectService.updateProject(project);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.DELETE)
    public ProjectModel deleteProject(@PathVariable(value="project_name") String project_name) {
        return projectService.deleteProject(project_name);
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public List<ProjectRoleModel> getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }


    /**
     * Gets all files of a project
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects/{project_name}/files", method = RequestMethod.GET)
    public List<File> getAllFiles(@PathVariable(value="project_name") String project_name) {
        return fileService.getAllFiles(project_name);
    }

    /**
     * @param project_name
     * @return a particular file
     */
    @RequestMapping(value = "/project/{project_name}/**", method = RequestMethod.GET)
    public File getFile(@PathVariable(value="project_name") String project_name,
                        HttpServletRequest request) {
        String path  = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return fileService.getFile(project_name, path);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/project/{project_name}/**", method = RequestMethod.POST)
    public File createFile(@PathVariable(value="project_name") String file_name,
                           HttpServletRequest request) {
        String path  = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return fileService.createFile(file_name, path);
    }
//
//    /**
//     * @param project_name
//     * @param project
//     * @return
//     */
//    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.PATCH)
//    public Project updateProject(@PathVariable(value="project_name") String project_name, @RequestBody Project project) {
//        return projectService.updateProject(project);
//    }
//
//    /**
//     * @param project_name
//     * @return
//     */
//    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.DELETE)
//    public Project deleteProject(@PathVariable(value="project_name") String project_name) {
//        return projectService.deleteProject(project_name);
//    }
//
//    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
//    public Role getProjectRoles() throws NotImplementedException {
//        //TODO this
//        throw new NotImplementedException();
//    }

}
