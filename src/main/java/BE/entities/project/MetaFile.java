package BE.entities.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "file")
public class MetaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    private String path;

    private String file_name;

    private String type;

    private String status;

    private Timestamp last_modified;

    private long length;

    @OneToMany(mappedBy = "metaFile")
    private List<SupportedView> supported_views;


    @OneToOne(mappedBy = "root_dir")
    @JsonIgnore
    private Project project;

//    @OneToMany
//    @JoinColumn(name = "dir_id")
//    private List<Dir_contains> contents = new ArrayList<Dir_contains>();

    protected MetaFile() {
    }

    public MetaFile(String path, String file_name, String type, String status, Timestamp last_modified, long length, List<SupportedView> supported_views) {
        this.path = path;
        this.file_name = file_name;
        this.type = type;
        this.status = status;
        this.last_modified = last_modified;
        this.length = length;
        this.supported_views = supported_views;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Timestamp last_modified) {
        this.last_modified = last_modified;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public List<SupportedView> getSupported_views() {
        return supported_views;
    }

    public void setSupported_views(List<SupportedView> supported_views) {
        this.supported_views = supported_views;
    }

    //    public List<Dir_contains> getContents() {
//        return contents;
//    }
//
//    public void setContents(List<Dir_contains> contents) {
//        this.contents = contents;
//    }
}
