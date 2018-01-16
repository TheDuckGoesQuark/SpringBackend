package BE.models.user;

import BE.models.MetaDataModel;

public class UserModel {
    private String username;
    private UserPrivilegesModel[] privileges;
    private ProjectAccessLevelModel[] projects;
    private String password;
    private MetaDataModel public_user_metadata;
    private MetaDataModel private_user_metadata;
    private MetaDataModel public_admin_metadata;
    private MetaDataModel private_admin_metadata;

    public UserModel(String username, UserPrivilegesModel[] privileges, ProjectAccessLevelModel[] projects, String password, MetaDataModel public_user_metadata, MetaDataModel private_user_metadata, MetaDataModel public_admin_metadata, MetaDataModel private_admin_metadata) {
        this.username = username;
        this.privileges = privileges;
        this.projects = projects;
        this.password = password;
        this.public_user_metadata = public_user_metadata;
        this.private_user_metadata = private_user_metadata;
        this.public_admin_metadata = public_admin_metadata;
        this.private_admin_metadata = private_admin_metadata;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserPrivilegesModel[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(UserPrivilegesModel[] privileges) {
        this.privileges = privileges;
    }

    public ProjectAccessLevelModel[] getProjects() {
        return projects;
    }

    public void setProjects(ProjectAccessLevelModel[] projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
