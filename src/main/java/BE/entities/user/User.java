package BE.entities.user;

import BE.entities.MetaData;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="public_user_metadata", referencedColumnName = "metadataID")
    private MetaData public_user_metadata = new MetaData(MetaData.PUBLIC_USER);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="private_user_metadata", referencedColumnName = "metadataID")
    private MetaData private_user_metadata = new MetaData(MetaData.PRIVATE_USER);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="public_admin_metadata", referencedColumnName = "metadataID")
    private MetaData public_admin_metadata = new MetaData(MetaData.PUBLIC_ADMIN);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="private_admin_metadata", referencedColumnName = "metadataID")
    private MetaData private_admin_metadata = new MetaData(MetaData.PRIVATE_ADMIN);

    protected User() {}

    public User(String username, String password, String email, List<Privilege> privileges, List<UserProject> userProjects, MetaData public_user_metadata, MetaData private_user_metadata, MetaData public_admin_metadata, MetaData private_admin_metadata) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.privileges = privileges;
        this.userProjects = userProjects;
        this.public_user_metadata = public_user_metadata;
        this.private_user_metadata = private_user_metadata;
        this.public_admin_metadata = public_admin_metadata;
        this.private_admin_metadata = private_admin_metadata;
    }

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

    public MetaData getPublic_user_metadata() {
        return public_user_metadata;
    }

    public void setPublic_user_metadata(MetaData public_user_metadata) {
        this.public_user_metadata = public_user_metadata;
    }

    public MetaData getPrivate_user_metadata() {
        return private_user_metadata;
    }

    public void setPrivate_user_metadata(MetaData private_user_metadata) {
        this.private_user_metadata = private_user_metadata;
    }

    public MetaData getPublic_admin_metadata() {
        return public_admin_metadata;
    }

    public void setPublic_admin_metadata(MetaData public_admin_metadata) {
        this.public_admin_metadata = public_admin_metadata;
    }

    public MetaData getPrivate_admin_metadata() {
        return private_admin_metadata;
    }

    public void setPrivate_admin_metadata(MetaData private_admin_metadata) {
        this.private_admin_metadata = private_admin_metadata;
    }
}
