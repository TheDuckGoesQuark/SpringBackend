package BE.models.file;

import java.util.Map;

public class FileMetaDataModel {
    private int version;
    private Map<String, Object> namespaces;

    public FileMetaDataModel(int version, Map<String, Object> namespaces) {
        this.version = version;
        this.namespaces = namespaces;
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
