package BE.entities;

import java.io.Serializable;

public class UserProjectPK implements Serializable {

    private String user;
    private String project;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
