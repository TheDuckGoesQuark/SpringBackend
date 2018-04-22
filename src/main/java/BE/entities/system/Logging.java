package BE.entities.system;


import javax.persistence.*;

@Entity
@Table(name = "logging")
public class Logging {

    private String component;
    private String level;
    @Id
    private String value;
    private String username;
    private String timestamp;

    protected Logging() {}

    public Logging(String component, String level, String value, String username, String timestamp) {
        this.component = component;
        this.level = level;
        this.value = value;
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
