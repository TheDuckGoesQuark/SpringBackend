package BE.models.project;

public class RoleModel {
    private String role;
    private String description;
    private boolean internal;

    public RoleModel(String role, String description, boolean internal) {
        this.role = role;
        this.description = description;
        this.internal = internal;
    }

    public RoleModel(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
