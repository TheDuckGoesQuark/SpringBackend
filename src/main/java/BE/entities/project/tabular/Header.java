package BE.entities.project.tabular;

import BE.entities.project.MetaFile;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "header")
@Entity
public class Header implements Serializable {

    public static final String NUMBER = "number";
    public static final String CATEGORY = "category";
    public static final String STRING = "string";

    @EmbeddedId
    private HeaderPK id;

    private String name;
    private String type;

    public Header() {}

    public Header(HeaderPK id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HeaderPK getId() {
        return id;
    }

    public void setId(HeaderPK id) {
        this.id = id;
    }

    @Embeddable
    public static class HeaderPK implements Serializable {

        @ManyToOne(optional = false)
        @JoinColumn(name="file_id")
        MetaFile file;

        @Column(name="\"index\"", nullable = false, updatable = false)
        int index;

        public HeaderPK() {
        }

        public HeaderPK(MetaFile file, int index) {
            this.file = file;
            this.index = index;
        }

        public MetaFile getFile() {
            return file;
        }

        public void setFile(MetaFile file) {
            this.file = file;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

    }

}
