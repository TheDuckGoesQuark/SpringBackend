package BE.models;

import java.util.HashMap;
import java.util.Map;

public class MetaDataModel {

    private int version = 0;
    private Map<String, Object> namespaces = new HashMap<>();

    public MetaDataModel(int version, Map<String, Object> namespaces) {
        this.version = version;
        this.namespaces = namespaces;
    }

    public MetaDataModel() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, Object> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Map<String, Object> namespaces) {
        this.namespaces = namespaces;
    }
}
