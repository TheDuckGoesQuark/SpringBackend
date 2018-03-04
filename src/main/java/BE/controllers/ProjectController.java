package BE.controllers;

// JavaIO

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Models
import BE.entities.project.MetaFile;
import BE.entities.project.SupportedView;
import BE.exceptions.FileRetrievalException;
import BE.exceptions.UnsupportedFileViewException;
import BE.responsemodels.file.FileModel;
import BE.responsemodels.file.FileRequestOptions;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;

// Services
import BE.services.MetaFileService;
import BE.services.ProjectService;

// Exceptions
import BE.exceptions.NotImplementedException;
//import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;

// Apache
import BE.services.StorageService;
import com.sun.org.apache.regexp.internal.RE;
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

    @Autowired
    ProjectService projectService;

    @Autowired
    MetaFileService metaFileService;

    @Autowired
    StorageService storageService;

    private static String getRelativeFilePath(HttpServletRequest request, String project_name) {
        String requestURI = request.getRequestURI();
        return requestURI.replaceFirst("/projects/" + project_name + "/files/", "");
    }

    private void sendFile(HttpServletResponse response, int file_id) {
        InputStream inputStream = storageService.getFileStream(file_id);

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

    private static FileRequestOptions readOptions(Map<String, String> mapOptions) {
        FileRequestOptions options  = new FileRequestOptions();
        options.setFinal(mapOptions.containsKey(FileRequestOptions.FINAL));
        options.setOffset(Integer.parseInt(mapOptions.get(FileRequestOptions.OFFSET)));
        options.setOverwrite(mapOptions.containsKey(FileRequestOptions.OVERWRITE));
        options.setTruncate(mapOptions.containsKey(FileRequestOptions.TRUNCATE));
        return options;
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
     * @param project_name
     * @return
     * @throws NotImplementedException
     */
    @RequestMapping(value = "/projects/{project_name}", method = RequestMethod.GET)
    public ProjectModel getProject(@PathVariable(value = "project_name") String project_name) {
        return projectService.getProjectByName(project_name);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.CREATE}, method = RequestMethod.POST)
    public ProjectModel createProject(@PathVariable(value = "project_name") String project_name) {
        return projectService.createProject(project_name);
    }

    /**
     * @param project_name
     * @param project
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE}, method = RequestMethod.POST)
    public ProjectModel updateProject(@PathVariable(value = "project_name") String project_name, @RequestBody ProjectModel project) {
        return projectService.updateProject(project);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.DELETE}, method = RequestMethod.POST)
    public void deleteProject(@PathVariable(value = "project_name") String project_name) {
        projectService.deleteProject(project_name);
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}", params = {"action=" + Action.UPDATE_GRANT}, method = RequestMethod.POST)
    public void updateGrant(@PathVariable(value = "project_name") String project_name, @RequestBody UserListModel userListModel) {
        projectService.updateGrant(project_name, userListModel);
    }

    @RequestMapping(value = "/project_roles", method = RequestMethod.GET)
    public List<ProjectRoleModel> getProjectRoles() throws NotImplementedException {
        //TODO this
        throw new NotImplementedException();
    }

    /**
     * @param project_name
     * @return a particular file
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", params = {"view=" + SupportedView.RAW_VIEW}, method = RequestMethod.GET)
    public void getRawFile(@PathVariable(value = "project_name") String project_name, HttpServletRequest request, HttpServletResponse response) {
        // Retrieves file path from request
        String relativePath = getRelativeFilePath(request, project_name);

        FileModel fileModel = metaFileService.getMetaFile(project_name, relativePath);
        sendFile(response, fileModel.getFile_id());
    }

    /**
     * @param project_name
     * @return a particular file
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
                return metaFileService.getMetaFile(project_name, relativePath);
            case SupportedView.RAW_VIEW:
                getRawFile(project_name, request, response);
                return null;
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * @param project_name
     * @param file_id
     * @return a particular file
     */
    @RequestMapping(value = "/projects/{project_name}/files_by_id/{file_id}", method = RequestMethod.GET)
    public FileModel getFileByID(@PathVariable(value = "project_name") String project_name,
                                 @PathVariable(value = "file_id") int file_id,
                                 @RequestParam(value = "view", required = false, defaultValue = SupportedView.META_VIEW) String view,
                                 HttpServletResponse response) {
        // Return appropriate response
        switch (view) {
            case SupportedView.META_VIEW:
                return metaFileService.getFileMetaByID(project_name, file_id);
            case SupportedView.RAW_VIEW:
                sendFile(response, file_id);
            default:
                throw new UnsupportedFileViewException();
        }
    }

    /**
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/files/**", method = RequestMethod.POST)
    public FileModel createOrUpdateFile(@PathVariable(value = "project_name") String project_name,
                                        @RequestParam(value = "content") byte[] bytes,
                                        @RequestParam Map<String, String> otherOptions,
                                        HttpServletRequest request,
                                        @RequestParam("action") String action)
            throws IOException, ServletException {

        String relativeFilePath = getRelativeFilePath(request, project_name);
        FileModel fileModel = metaFileService.createMetaFile(project_name, relativeFilePath, action);

        storageService.uploadFile(fileModel.getFile_id(), readOptions(otherOptions), bytes);

        return fileModel;
    }

    /**
     * @param project_name
     * @return
     */
    @RequestMapping(value = "/projects/{project_name}/**", method = RequestMethod.DELETE)
    public FileModel deleteFile(@PathVariable(value = "project_name") String project_name,
                                HttpServletRequest request) {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //TODO REPLACE.this is error prone because /files can be contained somewhere in filepath.
        // Can work with string methods to adjust path to replace just first /files, which is needed by protocols.
        path = path.replace("/files", "");
        return metaFileService.deleteMetaFile(project_name, path);
    }

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
        FileModel dir = metaFileService.getMetaFile(project_name, path);
        list.add(dir);
        //TODO transfer logic to file service
        if (dir.getType().equals("dir") && view.equals("meta")) {
            return metaFileService.getChildrenMeta(project_name, path);
        }
        return list;
    }
}