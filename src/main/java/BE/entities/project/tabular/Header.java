package BE.entities.project.tabular;

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

        @Column(name = "file_id", nullable = false, updatable = false)
        protected int fileid;

        @Column(nullable = false, updatable = false)
        protected int index;

        public HeaderPK() {
        }

        public HeaderPK(int file_id, int index) {
            this.fileid = file_id;
            this.index = index;
        }

        public int getFile_id() {
            return fileid;
        }

        public void setFile_id(int file_id) {
            this.fileid = file_id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HeaderPK headerPK = (HeaderPK) o;

            if (fileid != headerPK.fileid) return false;
            return index == headerPK.index;
        }

        @Override
        public int hashCode() {
            int result = fileid;
            result = 31 * result + index;
            return result;
        }
    }

}
