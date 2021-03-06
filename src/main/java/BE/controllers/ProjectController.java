package BE.controllers;

// JavaIO
import java.io.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Models
import BE.entities.project.FileTypes;
import BE.entities.project.SupportedView;
import BE.models.MetaDataModel;
import BE.models.file.FileModel;
import BE.models.file.FileRequestOptions;
import BE.models.file.MoveFileRequestModel;
import BE.models.file.SupportsViewModel;
import BE.models.project.ProjectModel;
import BE.models.project.ProjectRoleModel;
import BE.models.project.UserListModel;

// Services
import BE.services.FileService;
import BE.services.ProjectService;

// Exceptions
import BE.exceptions.NotImplementedException;
import BE.exceptions.FileRetrievalException;
import BE.exceptions.UnsupportedFileViewException;

// Apache
import org.apache.log4j.Logger;

// Spring
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Other
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static BE.controllers.Action.COPY;
import static BE.controllers.Action.MOVE;
import static BE.controllers.Action.SET_METADATA;
import static BE.models.file.FileRequestOptions.readOptions;

@RestController
@CrossOrigin
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    private final
    ProjectService projectService;

    private final
    FileService fileService;

    @Autowired
    public ProjectController(ProjectService projectService, FileService fileService) {
        this.projectService = projectService;
        this.fileService = fileService;
    }

    /**
     * Gets the path of a specific file in a request
     *
     * @param request      the request to get the file path from
     * @param project_name name of the project the file is in
     * @return file path
     */
    private static String getRelativeFilePath(HttpServletRequest request, String project_name) {
        String requestURI = request.getRequestURI();
        return requestURI.replaceFirst("/projects/" + project_name + "/files/", "");
    }

    /**
     * Sends a file
     *
     * @param inputStream input stream of the file
     * @param response
     */
    private void sendFile(InputStream inputStream, HttpServletResponse response, String mediaType) {

        response.setContentType(mediaType);

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
     * Gets all projects
     *
     * @return a list of all projects
     **/
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectModel> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * Gets a specific project
     *
     * @param project_name the name of the project to get
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public ProjectModel getProject(@PathVariable(value = "project_name") String project_name) {
        return projectService.getProjectByName(project_name);
    }

    /**
     * Creates a new project
     *
     * @param project_name the name of the project to create
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.CREATE}, method = RequestMethod.POST)
    public ProjectModel createProject(@PathVariable(value = "project_name") String project_name, Principal principal, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return projectService.createProject(project_name, principal.getName());
    }

    /**
     * @param project_name the name of the project to update
     * @param project      the project to update
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE}, method = RequestMethod.POST)
    public ProjectModel updateProject(@PathVariable(value = "project_name") String project_name, @RequestBody ProjectModel project) {
        return projectService.updateProject(project_name, project);
    }

    /**
     * @param project_name the name of the project to delete
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.DELETE}, method = RequestMethod.POST)
    public void deleteProject(@PathVariable(value = "project_name") String project_name) {
        projectService.deleteProject(project_name);
    }

    /**
     * Updates the list of users that can update a specific project
     *
     * @param project_name  the name of the project to update
     * @param userListModel the list of users to update
     * @return project
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE_GRANT}, method = RequestMethod.POST)
    public ProjectModel updateGrant(@PathVariable(value = "project_name") String project_name, @RequestBody UserListModel userListModel) {
        return projectService.updateGrant(project_name, userListModel);
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public List<ProjectRoleModel> getProjectRoles() throws NotImplementedException {
        //TODO this
        return projectService.getAllRoles();
        //throw new NotImplementedException();
    }

    /* -----------------|-----------------------------------|---------------- */
    /* -----------------|------PROJECT FILE OPERATIONS------|---------------- */
    /* -----------------\/----------------------------------\/--------------- */

    /**
     * Gets a specific file in a specific project
     *
     * @param project_name the name of the project that has the file
     * @param view         which type of file type to be returned i.e. 'raw' for a bytestream or 'meta' for just file meta data.
     * @param request      object containing information about the request
     * @param response     object to return the bytestream if chosen.
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.GET)
    public FileModel getFile(@PathVariable(value = "project_name") String project_name,
                             @RequestParam(required = false) Map<String, String> otherOptions,
                             @RequestParam(value = "view", required = false, defaultValue = SupportedView.META_VIEW) String view,
                             @RequestParam(value = "include_children", required = false) String includeChildren,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // Retrieves file path from request
        String relativePath = getRelativeFilePath(request, project_name);
        FileRequestOptions options = readOptions(otherOptions);

        // Return appropriate response
        if (!fileService.supportsView(project_name, relativePath, view).isSupportedView()) throw new UnsupportedFileViewException();
        switch (view) {
            case SupportedView.META_VIEW:
                FileModel fileModel = fileService.getMetaFileWithChildren(project_name, relativePath);
                if (includeChildren != null && fileModel.getType().equals(FileTypes.DIR)) return fileModel;
                else {
                    fileModel.setChildren(null);
                    return fileModel;
                }
            case SupportedView.RAW_VIEW:
                sendFile(fileService.getRawFile(project_name, relativePath), response, MediaType.APPLICATION_JSON_VALUE);
                return null;
            case SupportedView.TABULAR_VIEW:
                sendFile(fileService.getTabularFile(project_name, relativePath, options), response, MediaType.TEXT_CSV_VALUE);
                return null;
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * Gets a specific file by file id in a specific project
     *
     * @param project_name the name of the project that has the file
     * @param file_id      the id of the file to get
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", method = RequestMethod.GET)
    public FileModel getFileByID(@PathVariable(value = "project_name") String project_name,
                                 @PathVariable(value = "file_id") int file_id,
                                 @RequestParam(required = false) Map<String, String> otherOptions,
                                 @RequestParam(value = "view", required = false, defaultValue = SupportedView.META_VIEW) String view,
                                 @RequestParam(value = "include_children", required = false) String includeChildren,
                                 HttpServletResponse response) {

        FileRequestOptions options = readOptions(otherOptions);

        // Return appropriate response
        if (!fileService.supportsView(project_name, file_id, view).isSupportedView()) throw new UnsupportedFileViewException();
        switch (view) {
            case SupportedView.META_VIEW:
                FileModel fileModel = fileService.getMetaFileWithChildren(file_id);
                if (includeChildren != null && fileModel.getType().equals(FileTypes.DIR)) return fileModel;
                else {
                    fileModel.setChildren(null);
                    return fileModel;
                }
            case SupportedView.RAW_VIEW:
                sendFile(fileService.getRawFile(file_id), response, MediaType.APPLICATION_OCTET_STREAM_VALUE);
                return null;
            case SupportedView.TABULAR_VIEW:
                sendFile(fileService.getTabularFile(file_id, options), response, MediaType.TEXT_CSV_VALUE);
                return null;
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"action=" + Action.SUPPORTS_VIEW}, method = RequestMethod.GET)
    public SupportsViewModel supportsView(@PathVariable(value = "project_name") String project_name,
                                          @RequestParam(value = "view") String view,
                                          HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);

        return fileService.supportsView(project_name, relativeFilePath, view);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", params = {"action=" + Action.SUPPORTS_VIEW}, method = RequestMethod.GET)
    public SupportsViewModel supportsView(@PathVariable(value = "project_name") String project_name,
                                          @PathVariable(value = "file_id") int file_id,
                                          @RequestParam(value = "view") String view) {
        return fileService.supportsView(project_name, file_id, view);
    }

    /**
     * Creates or updates a specific file in a specific project
     *
     * @param project_name the name of the project that has the file
     * @param bytes
     * @param otherOptions
     * @param request
     * @param action       create or delete
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.POST)
    public FileModel createOrUpdateFile(@PathVariable(value = "project_name") String project_name,
                                        @RequestParam(required = false) Map<String, String> otherOptions,
                                        @RequestParam("action") String action,
                                        @RequestBody(required = false) byte[] bytes,
                                        HttpServletRequest request) {

        String relativeFilePath = getRelativeFilePath(request, project_name);
        FileRequestOptions options = readOptions(otherOptions);

        return fileService.createOrUpdateFile(project_name, relativeFilePath, action, options, bytes);
    }

    /**
     * Creates or updates a specific file in a specific project
     *
     * @param project_name the name of the project that has the file
     * @param bytes
     * @param otherOptions
     * @param action       create or delete
     * @return file
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", method = RequestMethod.POST)
    public FileModel createOrUpdateFileBy(@PathVariable(value = "project_name") String project_name,
                                          @PathVariable(value = "file_id") int file_id,
                                          @RequestParam(required = false) Map<String, String> otherOptions,
                                          @RequestParam("action") String action,
                                          @RequestBody(required = false) byte[] bytes) {

        FileRequestOptions options = readOptions(otherOptions);

        return fileService.createOrUpdateFile(project_name, file_id, action, options, bytes);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"action=" + Action.DELETE}, method = RequestMethod.POST)
    public void deleteFile(@PathVariable(value = "project_name") String project_name,
                           HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);

        fileService.deleteFile(project_name, relativeFilePath);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", params = {"action=" + Action.DELETE}, method = RequestMethod.POST)
    public void deleteFile(@PathVariable(value = "project_name") String project_name,
                           @PathVariable(value = "file_id") int file_id) {
        fileService.deleteFile(project_name, file_id);
    }

    /**
     * Updates the meta data for a file
     *
     * @param project_name the name of the project that has the file
     * @param request
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"action=" + SET_METADATA}, method = RequestMethod.POST)
    public FileModel updateFileMetaData(@PathVariable(value = "project_name") String project_name,
                                        @RequestBody MetaDataModel metaDataModel,
                                        HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);
        return fileService.updateFileMetaData(project_name, relativeFilePath, metaDataModel);
    }

    /**
     * Updates the meta data for a file
     *
     * @param project_name the name of the project that has the file
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", params = {"action=" + SET_METADATA}, method = RequestMethod.POST)
    public FileModel updateFileMetaData(@PathVariable(value = "project_name") String project_name,
                                        @PathVariable(value = "file_id") int file_id,
                                        @RequestBody MetaDataModel metaDataModel) {
        return fileService.updateFileMetaData(project_name, file_id, metaDataModel);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"action=" + MOVE}, method = RequestMethod.POST)
    public FileModel moveFile(@PathVariable(value = "project_name") String project_name,
                              @RequestBody MoveFileRequestModel moveFileRequestModel,
                              HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);
        return fileService.moveFile(project_name, relativeFilePath, moveFileRequestModel);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", params = {"action=" + MOVE}, method = RequestMethod.POST)
    public FileModel moveFile(@PathVariable(value = "project_name") String project_name,
                              @PathVariable(value = "file_id") int file_id,
                              @RequestBody MoveFileRequestModel moveFileRequestModel) {
        return fileService.moveFile(project_name, file_id, moveFileRequestModel);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"action=" + COPY}, method = RequestMethod.POST)
    public FileModel copyFile(@PathVariable(value = "project_name") String project_name,
                              @RequestBody MoveFileRequestModel moveFileRequestModel,
                              HttpServletRequest request) {
        String relativeFilePath = getRelativeFilePath(request, project_name);
        return fileService.copyFile(project_name, relativeFilePath, moveFileRequestModel);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", params = {"action=" + COPY}, method = RequestMethod.POST)
    public FileModel copyFile(@PathVariable(value = "project_name") String project_name,
                              @PathVariable(value = "file_id") int file_id,
                              @RequestBody MoveFileRequestModel moveFileRequestModel) {
        return fileService.copyFile(project_name, file_id, moveFileRequestModel);
    }
}