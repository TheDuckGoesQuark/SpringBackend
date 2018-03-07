package BE.controllers;

// JavaIO
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Models
import BE.entities.project.SupportedView;
import BE.exceptions.FileRetrievalException;
import BE.exceptions.UnsupportedFileViewException;
import BE.responsemodels.file.FileModel;
import BE.responsemodels.file.FileRequestOptions;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;

// Services
import BE.services.FileService;
import BE.services.ProjectService;

// Exceptions
import BE.exceptions.NotImplementedException;
//import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;

// Apache
import BE.services.StorageService;
import org.apache.log4j.Logger;

// Spring
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

//Other
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    final
    ProjectService projectService;

    final
    FileService fileService;

    @Autowired
    public ProjectController(ProjectService projectService, FileService fileService) {
        this.projectService = projectService;
        this.fileService = fileService;
    }

    /**
     * Gets the path of a specific file in a request
     * @param request the request to get the file path from
     * @param project_name name of the project the file is in
     * @return file path
     */
    private static String getRelativeFilePath(HttpServletRequest request, String project_name) {
        String requestURI = request.getRequestURI();
        return requestURI.replaceFirst("/projects/" + project_name + "/files/", "");
    }

    /**
     * Sends a file
     * @param inputStream input stream of the file
     * @param response
     */
    private void sendFile(InputStream inputStream, HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        try {
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            throw new FileRetrievalException();
        } finally {
            try {
                response.flushBuffer();
            } catch (IOException e) {
                throw new FileRetrievalException();
            }
        }
    }

    /**
     * Reads options of a request
     * @param mapOptions the options to read
     * @return options
     */
    private static FileRequestOptions readOptions(Map<String, String> mapOptions) {
        FileRequestOptions options = new FileRequestOptions();
        options.setFinal(mapOptions.containsKey(FileRequestOptions.FINAL));
        if (mapOptions.containsKey(FileRequestOptions.OFFSET))
            options.setOffset(Integer.parseInt(mapOptions.get(FileRequestOptions.OFFSET)));
        else options.setOffset(0);
        options.setOverwrite(mapOptions.containsKey(FileRequestOptions.OVERWRITE));
        options.setTruncate(mapOptions.containsKey(FileRequestOptions.TRUNCATE));
        return options;
    }

    /**
     * Gets all projects
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectModel> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * Gets a specific project
     * @param project_name the name of the project to get
     * @return project
     * @throws NotImplementedException
     */
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public ProjectModel getProject(@PathVariable(value = "project_name") String project_name) {
        return projectService.getProjectByName(project_name);
    }

    /**
     * Creates a new project
     * @param project_name the name of the project to create
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.CREATE}, method = RequestMethod.POST)
    public ProjectModel createProject(@PathVariable(value = "project_name") String project_name, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return projectService.createProject(project_name);
    }

    /**
     * Updates a specific existing project
     * @param project_name the name of the project to update
     * @param project the project to update
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE}, method = RequestMethod.POST)
    public ProjectModel updateProject(@PathVariable(value = "project_name") String project_name, @RequestBody ProjectModel project) {
        return projectService.updateProject(project);
    }

    /**
     * Deletes a specific existing project
     * @param project_name the name of the project to delete
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.DELETE}, method = RequestMethod.POST)
    public void deleteProject(@PathVariable(value = "project_name") String project_name) {
        projectService.deleteProject(project_name);
    }

    /**
     * Updates the list of users that can update a specific project
     * @param project_name the name of the project to update
     * @param userListModel the list of users to update
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE_GRANT}, method = RequestMethod.POST)
    public void updateGrant(@PathVariable(value = "project_name") String project_name, @RequestBody UserListModel userListModel) {
        projectService.updateGrant(project_name, userListModel);
    }

    /**
     *
     */
    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public List<ProjectRoleModel> getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    /**
     * Gets a specific raw file in a specific project
     * @param project_name the name of the project that has the file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"view=" + SupportedView.RAW_VIEW}, method = RequestMethod.GET)
    public void getRawFile(@PathVariable(value = "project_name") String project_name, HttpServletRequest request, HttpServletResponse response) {
        // Retrieves file path from request
        String relativePath = getRelativeFilePath(request, project_name);

        InputStream inputStream = fileService.getRawFile(project_name, relativePath);
        sendFile(inputStream, response);
    }

    /**
     * Gets a specific file in a specific project
     * @param project_name the name of the project that has the file
     * @param view
     * @param request
     * @param response
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.GET)
    public FileModel getFile(@PathVariable(value = "project_name") String project_name,
                             @RequestParam(value = "view", required = false, defaultValue = SupportedView.META_VIEW) String view,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // Retrieves file path from request
        String relativePath = getRelativeFilePath(request, project_name);

        // Return appropriate response
        switch (view) {
            case SupportedView.META_VIEW:
                return fileService.getMetaFile(project_name, relativePath);
            case SupportedView.RAW_VIEW:
                getRawFile(project_name, request, response);
                return null;
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * Gets a specific file by file id in a specific project
     * @param project_name the name of the project that has the file
     * @param file_id the id of the file to get
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", method = RequestMethod.GET)
    public FileModel getFileByID(@PathVariable(value = "project_name") String project_name,
                                 @PathVariable(value = "file_id") int file_id,
                                 @RequestParam(value = "view", required = false, defaultValue = SupportedView.META_VIEW) String view,
                                 HttpServletResponse response) {
        // Return appropriate response
        switch (view) {
            case SupportedView.META_VIEW:
                return fileService.getFileMetaByID(file_id);
            case SupportedView.RAW_VIEW:
                InputStream inputStream = fileService.getRawFileByID(file_id);
                sendFile(inputStream, response);
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * Creates or updates a specific file in a specific project
     * @param project_name the name of the project that has the file
     * @param bytes
     * @param otherOptions
     * @param request
     * @param action create or delete
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.POST)
    public FileModel createOrUpdateFile(@PathVariable(value = "project_name") String project_name,
                                        @RequestParam Map<String, String> otherOptions,
                                        @RequestParam("action") String action,
                                        @RequestBody(required = false) byte[] bytes,
                                        HttpServletRequest request) {

        String relativeFilePath = getRelativeFilePath(request, project_name);
        FileRequestOptions options = readOptions(otherOptions);

        return fileService.createFile(project_name, relativeFilePath, action, options, bytes);
    }

    /**
     * Deletes a specific file in a specific project
     * @param project_name the name of the project that has the file
     * @param request
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.DELETE)
    public void deleteFile(@PathVariable(value = "project_name") String project_name,
                                HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);

        fileService.deleteFile(project_name, relativeFilePath);
    }

    /**
     * Gets all files in a specific directory in a specific project
     * @param project_name the name of the project that has the directory
     * @param request
     * @param view
     * @return a list of files
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"view", "include_children"}, method = RequestMethod.GET)
    public List<FileModel> getDirContents(@PathVariable(value = "project_name") String project_name,
                                          HttpServletRequest request, @RequestParam("view") String view) {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //TODO REPLACE this is error prone because /files can be contained somewhere in filepath.
        // Can work with string methods to adjust path to replace just first /files, which is needed by protocols.
        path = path.replace("/files", "");
        List<FileModel> list = new ArrayList<>();
        //check if dir exists
        FileModel dir = fileService.getMetaFile(project_name, path);
        list.add(dir);
        //TODO transfer logic to file service
        if (dir.getType().equals("dir") && view.equals("meta")) {
            return fileService.getChildrenMeta(project_name, path);
        }
        return list;
    }
}