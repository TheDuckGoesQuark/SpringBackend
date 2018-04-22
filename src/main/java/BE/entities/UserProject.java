package BE.entities;

import BE.entities.project.Project;
import BE.entities.user.User;

import javax.persistence.*;

/**
 *  Since involved_in involves attribute,
 *  A separate entity class had to be created to handle
 *  this relationship.
 */

@Entity
@Table(name = "involved_in")
@IdClass(UserProjectPK.class)
public class UserProject {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_name", referencedColumnName = "name")
    private Project project;

    @Column(name = "role")
    private String role;

    @Column(name = "access_level")
    private String access_level;

    public UserProject(User user, Project project, String role, String access_level) {
        this.user = user;
        this.project = project;
        this.role = role;
        this.access_level = access_level;
    }

    public UserProject() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccess_level() {
        return access_level;
    }

    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }
}
