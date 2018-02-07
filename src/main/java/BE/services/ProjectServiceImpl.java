package BE.services;

import BE.entities.UserProject;
import BE.entities.project.File;
import BE.entities.project.FileTypes;
import BE.entities.project.Project;
import BE.entities.user.User;
import BE.exceptions.NotImplementedException;
import BE.exceptions.ProjectAlreadyExistsException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.FileRepository;
import BE.repositories.ProjectRepository;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    FileRepository fileRepository;

    // Conversion Functions
    private static ProjectModel projectToProjectModel(Project project) {
        return new ProjectModel(project.getName(),
                project.getUserProjects().stream().map(
                        ProjectServiceImpl::userProjectToUserListModel
                ).collect(Collectors.toList()));
    }

    private static UserListModel userProjectToUserListModel(UserProject userProject) {
        User user = userProject.getUser();
        return new UserListModel(
                user.getUsername(),
                userProject.getAccess_level());
    }

    @Override
    public List<ProjectModel> getAllProjects() {
        return ((List<Project>) projectRepository.findAll())
                .stream()
                .map(ProjectServiceImpl::projectToProjectModel)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectModel getProjectByName(String project_name) {
        Project project = projectRepository.findByName(project_name);
        if (project == null) throw new ProjectNotFoundException();
        else return projectToProjectModel(project);
    }

    @Override
    @Transactional
    public ProjectModel createProject(String project_name) {
        if (projectRepository.findByName(project_name) != null) throw new ProjectAlreadyExistsException();
        // TODO add creating user to project during creation logic
        // Create root directory
        File file = new File("/" + project_name, project_name, FileTypes.DIR, "status", "file metadata");
        // Create project
        Project project = new Project(project_name, file);
        // Link project to root file
        file.setProject(project);
        project.setRoot_dir(file);
        // Save to database
        projectRepository.save(project);
        return projectToProjectModel(project);
    }

    @Override
    @Transactional
    public ProjectModel updateProject(ProjectModel project) {
        Project updated = projectRepository.findByName(project.getProject_name());
        if (updated == null) throw new ProjectNotFoundException();
        // .save performs both update and creation
        // TODO method updates project meta-data, which we don't track yet :(
        throw new NotImplementedException();
    }

    @Override
    public ProjectModel updateGrant(String project_name, UserListModel grant) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public ProjectModel deleteProject(String project_name) {
        if (projectRepository.findByName(project_name) == null) throw new ProjectNotFoundException();
        else {
             projectRepository.deleteByName(project_name);
             return null;
        }
    }

    @Override
    public List<ProjectRoleModel> getAllRoles() {
        throw new NotImplementedException();
    }
}
