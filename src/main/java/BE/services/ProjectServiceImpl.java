package BE.services;

import BE.controllers.AccessRight;
import BE.entities.UserProject;
import BE.entities.project.*;
import BE.entities.user.User;
import BE.exceptions.*;
import BE.repositories.ProjectRepository;
import BE.models.project.ProjectModel;
import BE.models.project.ProjectRoleModel;
import BE.models.project.UserListModel;
import BE.repositories.UserRepository;
import BE.repositories.UserProjectRepository;
import BE.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    RoleRepository roleRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, UserProjectRepository userProjectRepository, RoleRepository roleRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userProjectRepository = userProjectRepository;
        this.roleRepository = roleRepository;
    }

    // Conversion Functions

    /**
     * Converts a specific project to a project model
     * @param project the project to convert
     * @return project model
     */
    private static ProjectModel projectToProjectModel(Project project) {
        return new ProjectModel(project.getName(),
                project.getUserProjects()
                        .stream()
                        .map(ProjectServiceImpl::userProjectToUserListModel)
                        .collect(Collectors.toList()));
    }

    /**
     * Converts a specific userProject to a user list model
     * @param userProject the userProject to convert
     * @return user list model
     */
    private static UserListModel userProjectToUserListModel(UserProject userProject) {
        User user = userProject.getUser();
        return new UserListModel(
                user.getUsername(),
                userProject.getAccess_level());
    }

    /**
     * Converts a specific role to a role model
     * @param role the userProject to convert
     * @return user list model
     */
    private static ProjectRoleModel roleToProjectRoleMode(Role role) {
        return new ProjectRoleModel(
                role.getRole(),
                role.getDescription(),
                role.isInternal());
        }


    /**
     * Gets all projects
     * @return a list of all projects
     */
    @Override
    public List<ProjectModel> getAllProjects() {
        return ((List<Project>) projectRepository.findAll())
                .stream()
                .map(ProjectServiceImpl::projectToProjectModel)
                .collect(Collectors.toList());
    }

    /**
     * Gets a specific project by its name
     * @param project_name name of project to get
     * @return project
     */
    @Override
    public ProjectModel getProjectByName(String project_name) {
        Project project = projectRepository.findByName(project_name);
        if (project == null) throw new ProjectNotFoundException();
        else return projectToProjectModel(project);
    }

    /**
     * Creates a new project
     * @param project_name name of project to create
     * @return project
     */
    @Override
    @Transactional
    public ProjectModel createProject(String project_name, String username) {

        if (projectRepository.findByName(project_name) != null) throw new ProjectAlreadyExistsException();
        MetaFile projectRoot = MetaFile.createRoot();

        Project project = new Project(project_name, projectRoot);

        // Make current user an admin of the new project
        User user = userRepository.findByUsername(username);
        UserProject userProject = new UserProject();
        userProject.setAccess_level(AccessRight.ADMIN);
        userProject.setRole(AccessRight.ADMIN);

        userProject.setUser(user);
        userProject.setProject(project);

        userProject = userProjectRepository.save(userProject);

        project.setUserProjects(Collections.singletonList(userProject));

        return projectToProjectModel(project);
    }

    /**
     * Updates a specific existing project
     * @param project the project to update
     * @return project
     */
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
        if(!access_level.equals(AccessRight.REGULAR) && !access_level.equals(AccessRight.ADMIN)) throw new InvalidAccessLevelException();
        UserProject userProject = new UserProject();
        userProject.setAccess_level(access_level);
        userProject.setRole(access_level);

        userProject.setUser(user);
        userProject.setProject(project);

        userProject = userProjectRepository.save(userProject);

        project.setUserProjects(Collections.singletonList(userProject));

        return projectToProjectModel(project);
    }

    /**
     * Deletes a specific existing project
     * @param project_name the name of the project to delete
     * @return project
     */
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
        return ((List<Role>) roleRepository.findAll()).stream().map(
                ProjectServiceImpl::roleToProjectRoleMode
        ).collect(Collectors.toList());
    }
}
