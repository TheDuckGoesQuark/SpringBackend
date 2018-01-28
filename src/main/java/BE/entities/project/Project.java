package BE.entities.project;


import BE.entities.UserProject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    private String name;

    @JoinColumn(name = "root_dir_id")
    @OneToOne(cascade = CascadeType.ALL)
    private File root_dir;

    @OneToMany(mappedBy = "project")
    private List<UserProject> userProjects;

    protected Project() {
    }

    public Project(String name, File root_dir, List<UserProject> userProjects) {
        this.name = name;
        this.root_dir = root_dir;
        this.userProjects = userProjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getRoot_dir() {
        return root_dir;
    }

    public void setRoot_dir(File root_dir) {
        this.root_dir = root_dir;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }
}
