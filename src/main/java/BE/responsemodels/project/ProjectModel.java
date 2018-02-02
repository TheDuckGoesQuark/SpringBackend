package BE.responsemodels.project;

import java.util.List;

public class ProjectModel {

    private String project_name;
    private List<UserListModel> users;

    protected ProjectModel() {}

    public ProjectModel(String project_name, List<UserListModel> users) {
        this.project_name = project_name;
        this.users = users;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public List<UserListModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserListModel> users) {
        this.users = users;
    }
}
