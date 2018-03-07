package BE.services;

import BE.entities.UserProject;
import BE.entities.project.*;
import BE.entities.user.User;
import BE.exceptions.NotImplementedException;
import BE.exceptions.ProjectAlreadyExistsException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.ProjectRepository;
import BE.models.project.ProjectModel;
import BE.models.project.ProjectRoleModel;
import BE.models.project.UserListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final
    ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Conversion Functions
    private static ProjectModel projectToProjectModel(Project project) {
        return new ProjectModel(project.getName(),
//TODO get user projects returns null and that causes the program to throw NullPointerException
/*                project.getUserProjects().stream().map(
                        ProjectServiceImpl::userProjectToUserListModel
                ).collect(Collectors.toList())*/
                null);
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
        MetaFile projectRoot = MetaFile.createRoot();
        Project project = new Project(project_name, projectRoot);
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
    public int getProjectRootDirId(String project_name) {
        Project project = projectRepository.findByName(project_name);
        if (project == null) throw new ProjectNotFoundException();
        else return project.getRoot_dir().getFileId();
    }

    @Override
    public List<ProjectRoleModel> getAllRoles() {
        throw new NotImplementedException();
    }
}
