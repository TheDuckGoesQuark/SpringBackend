package BE.services;

import BE.entities.project.Project;
import BE.entities.user.User;
import BE.exceptions.ProjectAlreadyExistsException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Project getProjectByName(String project_name) {
        Project project = projectRepository.findByName(project_name);
        if (project == null) throw new ProjectNotFoundException();
        else return project;
    }

    @Override
    @Transactional
    public Project createProject(String project_name) {
        if (projectRepository.findByName(project_name) != null) throw new ProjectAlreadyExistsException();
        Project project = new Project(project_name, null);
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project updateProject(Project project) {
        if (projectRepository.findByName(project.getName()) == null) throw new ProjectNotFoundException();
        // .save performs both update and creation
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project deleteProject(String project_name) {
        if (projectRepository.findByName(project_name) == null) throw new ProjectNotFoundException();
        else return projectRepository.deleteByName(project_name);
    }
}
