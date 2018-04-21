package BE.models.user;

import BE.entities.MetaData;
import BE.models.MetaDataModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

public class UserModel {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    private List<ProjectListModel> projects;

    private List<String> privileges;

    private MetaDataModel public_user_metadata = new MetaDataModel();

    private MetaDataModel private_user_metadata = new MetaDataModel();

    private MetaDataModel public_admin_metadata = new MetaDataModel();

    private MetaDataModel private_admin_metadata = new MetaDataModel();

    protected UserModel() {}

    public UserModel(String username, String password, String email, List<ProjectListModel> projects, List<String> privileges, MetaDataModel public_user_metadata, MetaDataModel private_user_metadata, MetaDataModel public_admin_metadata, MetaDataModel private_admin_metadata) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.projects = projects;
        this.privileges = privileges;
        this.public_user_metadata = public_user_metadata;
        this.private_user_metadata = private_user_metadata;
        this.public_admin_metadata = public_admin_metadata;
        this.private_admin_metadata = private_admin_metadata;
    }

    public UserModel(String username, String password, String email, List<ProjectListModel> projects, List<String> privileges) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.projects = projects;
        this.privileges = privileges;
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

    public List<ProjectListModel> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectListModel> projects) {
        this.projects = projects;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public MetaDataModel getPublic_user_metadata() {
        return public_user_metadata;
    }

    public void setPublic_user_metadata(MetaDataModel public_user_metadata) {
        this.public_user_metadata = public_user_metadata;
    }

    public MetaDataModel getPrivate_user_metadata() {
        return private_user_metadata;
    }

    public void setPrivate_user_metadata(MetaDataModel private_user_metadata) {
        this.private_user_metadata = private_user_metadata;
    }

    public MetaDataModel getPublic_admin_metadata() {
        return public_admin_metadata;
    }

    public void setPublic_admin_metadata(MetaDataModel public_admin_metadata) {
        this.public_admin_metadata = public_admin_metadata;
    }

    public MetaDataModel getPrivate_admin_metadata() {
        return private_admin_metadata;
    }

    public void setPrivate_admin_metadata(MetaDataModel private_admin_metadata) {
        this.private_admin_metadata = private_admin_metadata;
    }
}
