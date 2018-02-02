package BE.services;

import BE.entities.project.Project;
import BE.entities.project.Role;
import BE.entities.user.User;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.ProjectRoleModel;
import BE.responsemodels.project.UserListModel;

import java.util.List;

public interface ProjectService {

    public List<ProjectModel> getAllProjects();

    public ProjectModel getProjectByName(String project_name);

    public ProjectModel createProject(String project_name);

    public ProjectModel updateProject(ProjectModel project);

    public ProjectModel updateGrant(String project_name, UserListModel grant);

    public ProjectModel deleteProject(String project_name);

    public List<ProjectRoleModel> getAllRoles();

}
