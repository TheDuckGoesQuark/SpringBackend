package BE.models.project;

import BE.models.MetaDataModel;
import BE.models.project.UserAccessLevelModel;

public class ProjectModel {
    private String project_name;
    private UserAccessLevelModel[] users;
    private MetaDataModel public_metadata;
    private MetaDataModel private_metadata;
    private MetaDataModel admin_metadata;

    public ProjectModel(String project_name, UserAccessLevelModel[] users, MetaDataModel public_metadata, MetaDataModel private_metadata, MetaDataModel admin_metadata) {
        this.project_name = project_name;
        this.users = users;
        this.public_metadata = public_metadata;
        this.private_metadata = private_metadata;
        this.admin_metadata = admin_metadata;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public UserAccessLevelModel[] getUsers() {
        return users;
    }

    public void setUsers(UserAccessLevelModel[] users) {
        this.users = users;
    }

    public MetaDataModel getPublic_metadata() {
        return public_metadata;
    }

    public void setPublic_metadata(MetaDataModel public_metadata) {
        this.public_metadata = public_metadata;
    }

    public MetaDataModel getPrivate_metadata() {
        return private_metadata;
    }

    public void setPrivate_metadata(MetaDataModel private_metadata) {
        this.private_metadata = private_metadata;
    }

    public MetaDataModel getAdmin_metadata() {
        return admin_metadata;
    }

    public void setAdmin_metadata(MetaDataModel admin_metadata) {
        this.admin_metadata = admin_metadata;
    }
}
