package BE.entities.user;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "privilege")
public class Privilege {
    @Id
    private String name;

    private String description;
    @Column(columnDefinition = "TINYINT(1)")
    private boolean internal;

    public Privilege() {
    }

    public Privilege(String name, String description, boolean internal) {
        this.name = name;
        this.description = description;
        this.internal = internal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
