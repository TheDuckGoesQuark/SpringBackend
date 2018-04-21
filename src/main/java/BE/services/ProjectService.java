package BE.services;

import BE.models.project.ProjectModel;
import BE.models.project.ProjectRoleModel;
import BE.models.project.UserListModel;

import java.util.List;

public interface ProjectService {

    List<ProjectModel> getAllProjects();

    ProjectModel getProjectByName(String project_name);

    ProjectModel createProject(String project_name, String username);

    ProjectModel updateProject(String project_name, ProjectModel project);

    ProjectModel updateGrant(String project_name, UserListModel grant);

    ProjectModel deleteProject(String project_name);

    int getProjectRootDirId(String project_name);

    List<ProjectRoleModel> getAllRoles();

}
