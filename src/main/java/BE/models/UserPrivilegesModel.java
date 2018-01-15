package BE.models;

public class UserPrivilegesModel {
    private String privilege;
    private String description;
    private boolean internal;

    public UserPrivilegesModel(String privilege, String description, boolean internal) {
        this.privilege = privilege;
        this.description = description;
        this.internal = internal;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }
}
