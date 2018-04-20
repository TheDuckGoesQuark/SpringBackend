package BE.entities;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="metadata")
public class MetaData {

    public static final String PUBLIC_USER = "public_user";
    public static final String PRIVATE_USER = "private_user";
    public static final String PUBLIC_ADMIN = "public_admin";
    public static final String PRIVATE_ADMIN = "private_admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metadataID;

    @Column(columnDefinition = "LONGTEXT")
    private String namespaces;

    private String type;

    private int version;

    public MetaData() {
        this.version = 0;
    }

    public MetaData(int metadataID, String namespace, String type, int version) {
        this.metadataID = metadataID;
        this.namespaces = namespace;
        this.type = type;
        this.version = version;
    }

    public int getMetadataID() {
        return metadataID;
    }

    public void setMetadataID(int metadataID) {
        this.metadataID = metadataID;
    }

    public String getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(String namespace) {
        this.namespaces = namespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
