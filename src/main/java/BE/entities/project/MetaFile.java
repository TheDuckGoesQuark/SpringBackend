package BE.entities.project;

import BE.entities.project.tabular.Header;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static BE.models.file.FileModel.ROOT_FILE_NAME;
import static BE.services.FileServiceImpl.DIRECTORY_SUPPORTED_VIEWS;

@Entity
@Table(name = "file")
public class MetaFile {

    public static String FILE_PATH_DELIMITER = "/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    private String file_name;

    private String type;

    private String status;

    private Timestamp last_modified;

    private long length;

    @ManyToOne
    private MetaFile parent;

    @OneToMany(mappedBy = "parent")
    private List<MetaFile> children = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "supports_view",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "file_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "view", referencedColumnName = "view", insertable = false, updatable = false))
    private List<SupportedView> supported_views;

    @OneToOne(mappedBy = "root_dir")
    @JsonIgnore
    private Project project;

    protected MetaFile() {
    }

    private MetaFile(String file_name, String type, String status, Timestamp last_modified, long length, List<SupportedView> supported_views, MetaFile parent) {
        this.file_name = file_name;
        this.type = type;
        this.status = status;
        this.last_modified = last_modified;
        this.length = length;
        this.supported_views = supported_views;
        this.parent = parent;
    }

    public static MetaFile createRoot() {
        return new MetaFile(
                ROOT_FILE_NAME,
                FileTypes.DIR,
                FileStatus.READY,
                new Timestamp(System.currentTimeMillis()),
                0,
                DIRECTORY_SUPPORTED_VIEWS,
                null);
    }

    public static MetaFile createFile(String file_name, String type, String status, long length, List<SupportedView> supportedViews, MetaFile parent) {
        MetaFile metaFile = new MetaFile(
                file_name,
                type,
                status,
                new Timestamp(System.currentTimeMillis()),
                length,
                supportedViews,
                parent);
        parent.children.add(metaFile);
        return metaFile;
    }

    public static MetaFile createDirectory(String file_name, MetaFile parent) {
        return createFile(file_name, FileTypes.DIR, FileStatus.READY, 0, DIRECTORY_SUPPORTED_VIEWS, parent);
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getPath() {
        if (parent == null) return this.file_name;
        String parentPath = this.getParent().getPath();
        return parentPath.equals(ROOT_FILE_NAME) ? parentPath + this.file_name : parentPath + "/" + this.file_name;
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

    public MetaFile getParent() {
        return parent;
    }

    public void setParent(MetaFile parent) {
        this.parent = parent;
    }

    public List<MetaFile> getChildren() {
        return children;
    }

    public void setChildren(List<MetaFile> children) {
        this.children = children;
    }
}
