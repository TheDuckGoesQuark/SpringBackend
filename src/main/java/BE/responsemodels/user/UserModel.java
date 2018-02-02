package BE.responsemodels.user;

import java.util.List;

public class UserModel {

    private String username;

    private String password;

    private String email;

    private List<ProjectListModel> projects;

    protected UserModel() {}

    public UserModel(String username, String password, String email, List<ProjectListModel> projects) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.projects = projects;
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
}
