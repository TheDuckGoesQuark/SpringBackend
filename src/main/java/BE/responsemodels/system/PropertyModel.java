package BE.responsemodels.system;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyModel {
    private String id;
    // private Display display
    private boolean readonly;
    private String type;
    private String value;

    protected PropertyModel() {
    }

    public PropertyModel(String id, boolean readonly, String type, String value) {
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
