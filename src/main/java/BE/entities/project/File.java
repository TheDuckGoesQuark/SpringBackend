package BE.entities.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    private String path;

    private String file_name;

    private String type;

    private String status;

    @Column(name = "meta-data")
    private String meta_data;




    @OneToOne(mappedBy = "root_dir")
    @JsonIgnore
    private Project project;

    protected File() {
    }

    public File(String path, String file_name, String type, String status, String meta_data) {
        this.path = path;
        this.file_name = file_name;
        this.type = type;
        this.status = status;
        this.meta_data = meta_data;
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

    public String getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(String meta_data) {
        this.meta_data = meta_data;
    }
}
