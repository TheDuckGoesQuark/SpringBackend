package BE.services;

import BE.models.project.ProjectModel;
import BE.models.project.ProjectRoleModel;
import BE.models.project.UserListModel;

import java.util.List;

public interface ProjectService {

    public List<ProjectModel> getAllProjects();

    public ProjectModel getProjectByName(String project_name);

    public ProjectModel createProject(String project_name);

    public ProjectModel updateProject(ProjectModel project);

    public ProjectModel updateGrant(String project_name, UserListModel grant);

    public ProjectModel deleteProject(String project_name);

    public int getProjectRootDirId(String project_name);

    public List<ProjectRoleModel> getAllRoles();

}
