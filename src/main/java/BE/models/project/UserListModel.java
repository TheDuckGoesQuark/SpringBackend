package BE.models.project;

public class UserListModel {
    private String username;
    private String access_level;

    protected UserListModel() {}

    public UserListModel(String username, String access_level) {
        this.username = username;
        this.access_level = access_level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccess_level() {
        return access_level;
    }

    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }
}
