package BE.models;

import java.util.concurrent.atomic.AtomicInteger;

public class MetaDataModel {
    private AtomicInteger version;
    private Object[] namespaces;

    public MetaDataModel(AtomicInteger version, Object[] namespaces) {
        this.version = version;
        this.namespaces = namespaces;
    }

    public AtomicInteger getVersion() {
        return version;
    }

    public void setVersion(AtomicInteger version) {
        this.version = version;
    }

    public Object[] getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Object[] namespaces) {
        this.namespaces = namespaces;
    }
}
