package BE.entities.user;

import BE.entities.UserProject;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "username")
    private String username;

    private String password;

    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinTable(name = "has_privilege",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "privilege_name", referencedColumnName = "name"))
    private List<Privilege> privileges;

    @OneToMany(mappedBy = "user")
    private List<UserProject> userProjects;

    protected User() {}

    public User(String username, String password, String email, List<Privilege> privileges, List<UserProject> userProjects) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.privileges = privileges;
        this.userProjects = userProjects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }
}
