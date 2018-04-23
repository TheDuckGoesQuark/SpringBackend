package BE.entities.system;


import javax.persistence.*;

@Entity
@Table(name = "property")
public class Property {

    @Id
    private String id;
    private String type;
    private String value;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean readonly;

    protected Property() {}

    public Property(String id, boolean readonly, String type, String value) {
        this.id = id;
        this.readonly = readonly;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
