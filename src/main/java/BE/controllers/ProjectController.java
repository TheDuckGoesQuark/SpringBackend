package BE.controllers;

// JavaIO
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
// Exceptions
import BE.exceptions.NotImplementedException;
import java.io.IOException;
// Models
import BE.models.project.ProjectModel;
import BE.models.project.RoleModel;
import BE.models.user.UserModel;
// Apache & Spring
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
