package BE.models;

import org.json.JSONObject;

public class MetaDataModel {
    private int version;
    private JSONObject namespaces;

    public MetaDataModel(int version, JSONObject namespaces) {
        this.version = version;
        this.namespaces = namespaces;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public JSONObject getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(JSONObject namespaces) {
        this.namespaces = namespaces;
    }
}
