package BE.models.user;

public class UserPrivilegesModel {
    private Privilege privilege;
    private String description;
    private boolean internal;

    public UserPrivilegesModel(Privilege privilege, String description, boolean internal) {
        this.privilege = privilege;
        this.description = description;
        this.internal = internal;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
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