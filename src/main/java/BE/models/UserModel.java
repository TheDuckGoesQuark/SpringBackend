package BE.models;

public class UserModel {

    private final String username;
    private final String content;

    public UserModel(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
