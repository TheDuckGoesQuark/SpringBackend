package BE.controllers;

import BE.entities.project.File;
import BE.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * Gets all files of a project
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects/{project_name}/files", method = RequestMethod.GET)
    public List<File> getAllFiles(@PathVariable(value="project_name") String project_name) {
        return fileService.getAllFiles(project_name);
    }
//
//    /**
//     * @param project_name
//     * @return
//     */
//    @RequestMapping(value = "/projects/{project_name}/{file_path}", method = RequestMethod.GET)
//    public File getFile(@PathVariable(value="project_name") String project_name,
//                        @PathVariable(value="file_path") String file_path) {
//        return fileService.getFile(project_name, file_path);
//    }
//
//    /**
//     * @param project_name
//     * @return
//     */
//    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.POST)
//    public Project createProject(@PathVariable(value="project_name") String project_name) {
//        return projectService.createProject(project_name);
//    }
//
//    /**
//     * @param project_name
//     * @param project
//     * @return
//     */
//    @RequestMapping(value = "/project/{project_name}", method = RequestMethod.PATCH)
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
