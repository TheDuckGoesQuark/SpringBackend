package BE.services;

import BE.entities.UserProject;
import BE.entities.project.*;
import BE.entities.user.User;
import BE.exceptions.*;
import BE.repositories.ProjectRepository;
import BE.repositories.SupportedViewRepository;
import BE.repositories.UserRepository;
import BE.repositories.UserProjectRepository;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final
    ProjectRepository projectRepository;

    private final
    UserRepository userRepository;

    private final
    UserProjectRepository userProjectRepository;

    private final
    SupportedViewRepository supportedViewRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, UserProjectRepository userProjectRepository, SupportedViewRepository supportedViewRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userProjectRepository = userProjectRepository;
        this.supportedViewRepository = supportedViewRepository;
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


    // NOTE: Root directory has name "" and path "".
    // A file in this directory has name "something" and path "something"
    private MetaFile createProjectRoot() {
        List<SupportedView> supportedViews = new ArrayList<>();
        supportedViews.add(supportedViewRepository.findByView(SupportedView.META_VIEW));
        return new MetaFile(
                "",
                "",
                FileTypes.DIR,
                FileStatus.READY,
                new Timestamp(System.currentTimeMillis()),
                0L,
                supportedViews
                );
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
    public ProjectModel createProject(String project_name, String username) {
        if (projectRepository.findByName(project_name) != null) throw new ProjectAlreadyExistsException();
        MetaFile projectRoot = createProjectRoot();
        Project project = new Project(project_name, projectRoot);
        // Save to database
        projectRepository.save(project);
        // Make current user an admin of the new project
        User user = userRepository.findByUsername(username);
        UserProject userProject = new UserProject();
        userProject.setProject(project);
        userProject.setUser(user);
        userProject.setAccess_level("project_admin");
        userProjectRepository.save(userProject);

        List<UserProject> userProjects = new ArrayList<>();
        userProjects.add(userProject);
        project.setUserProjects(userProjects);
        return projectToProjectModel(project);
    }

    @Override
    @Transactional
    public ProjectModel updateProject(String project_name, ProjectModel project) {
        Project updated = projectRepository.findByName(project_name);
        if (updated == null) throw new ProjectNotFoundException();
        // .save performs both update and creation
        // TODO method updates project meta-data, which we don't track yet :(
        throw new NotImplementedException();
    }

    @Override
    public ProjectModel updateGrant(String project_name, UserListModel grant) {
        Project project = projectRepository.findByName(project_name);
        if (project == null) throw new ProjectNotFoundException();
        User user = userRepository.findByUsername(grant.getUsername());
        if (user == null) throw new UserNotFoundException();
        String access_level = grant.getAccess_level();
        if(!access_level.equals("regular") && !access_level.equals("project_admin")) throw new InvalidAccessLevelException();
        UserProject userProject = new UserProject();
        userProject.setProject(project);
        userProject.setUser(user);
        userProject.setAccess_level(access_level);
        userProjectRepository.save(userProject);

        //do I need to check if the UserProject is already in the list?
        //is it possible to have two UserProjects that are the same besides access_level and role? if so need to avoid this

        List<UserProject> userProjects = new ArrayList<>();
        if (project.getUserProjects() != null) userProjects = project.getUserProjects();
        userProjects.add(userProject);
        project.setUserProjects(userProjects);
        return projectToProjectModel(project);
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
