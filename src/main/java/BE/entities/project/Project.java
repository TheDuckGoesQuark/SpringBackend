package BE.entities.project;


import BE.entities.UserProject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    private String name;

    @JoinColumn(name = "root_dir_id")
    @OneToOne(cascade = CascadeType.ALL)
    private MetaFile root_dir;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<UserProject> userProjects = new ArrayList<>();

    protected Project() {
    }

    public Project(String name, MetaFile root_dir) {
        this.name = name;
        this.root_dir = root_dir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaFile getRoot_dir() {
        return root_dir;
    }

    public void setRoot_dir(MetaFile root_dir) {
        this.root_dir = root_dir;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }
}
