package BE.controllers;

// JavaIO
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

// Models
import BE.responsemodels.file.FileModel;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;

// Services
import BE.services.FileService;
import BE.services.ProjectService;

// Exceptions
import BE.exceptions.NotImplementedException;
import java.io.IOException;

// Apache
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

// Spring
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

//Other
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public void upload(HttpServletRequest request) {
        try {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            // Inform user about invalid request
            System.out.println("Invalid request");
            return;
        }

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        // Parse the request
            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                if (!item.isFormField()) {
                    String filename = item.getName();
                    // Process the input stream
                    System.out.println("File detected");
                    OutputStream out = new FileOutputStream(filename);
                    IOUtils.copy(stream, out);
                    stream.close();
                    out.close();
                }
            }
        }catch (FileUploadException | IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/uploader", method = RequestMethod.GET)
    public ModelAndView uploaderPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("uploader");
        return model;
    }

}
