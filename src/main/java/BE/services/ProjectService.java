package BE.services;

import BE.entities.project.Project;
import BE.entities.project.Role;
import BE.entities.user.User;

import java.util.List;

public interface ProjectService {

    public List<Project> getAllProjects();

    public Project getProjectByName(String project_name);

    public Project createProject(String project_name);

    public Project updateProject(Project project);

    public Project deleteProject(String project_name);

    public List<String> getAllRoles();

}
