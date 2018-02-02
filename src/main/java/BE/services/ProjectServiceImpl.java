package BE.services;

import BE.entities.project.File;
import BE.entities.project.FileTypes;
import BE.entities.project.Project;
import BE.exceptions.ProjectAlreadyExistsException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.FileRepository;
import BE.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    FileRepository fileRepository;

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
        // TODO add creating user to project during creation logic
        // Create root directory
        File file = new File("/" + project_name, project_name, FileTypes.DIR);
        // Create project
        Project project = new Project(project_name, file);
        // Link project to root file
        file.setProject(project);
        project.setRoot_dir(file);
        // Save to database
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

    @Override
    public List<String> getAllRoles() {
        return null;
    }
}
