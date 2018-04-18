package BE.entities.project.tabular;

import BE.entities.project.MetaFile;

import javax.persistence.*;

@Table(name = "header")
public class Header {

    public static final String NUMBER = "number";
    public static final String CATEGORY = "category";
    public static final String STRING = "string";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @Id
    private MetaFile file;
    @Id
    private int index;

    private String name;
    private String type;


    protected Header() {}

    public Header(MetaFile file, String name, String type, int index) {
        this.file = file;
        this.name = name;
        this.type = type;
        this.index = index;
    }

    public MetaFile getFile() {
        return file;
    }

    public void setFile(MetaFile file) {
        this.file = file;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
