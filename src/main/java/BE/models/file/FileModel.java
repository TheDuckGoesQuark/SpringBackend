package BE.models.file;

import BE.entities.project.SupportedView;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
public class FileModel {

    public static final String ROOT_FILE_NAME= "";

    private String path;

    private String file_name;

    private int file_id;

    private List<SupportedView> views;

    private FileMetaDataModel metadata;

    private String type;

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FileModel> children;

    // File meta model
    public FileModel(String path, String file_name, int file_id, List<SupportedView> views, FileMetaDataModel metadata, String type, String status) {
        this.path = path;
        this.file_name = file_name;
        this.file_id = file_id;
        this.views = views;
        this.metadata = metadata;
        this.type = type;
        this.status = status;
    }

    // Directory meta model
    public FileModel(String path, String file_name, int file_id, List<SupportedView> views, FileMetaDataModel metadata, String type, String status, List<FileModel> children) {
        this.path = path;
        this.file_name = file_name;
        this.file_id = file_id;
        this.views = views;
        this.metadata = metadata;
        this.type = type;
        this.status = status;
        this.children = children;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
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

    public List<SupportedView> getViews() {
        return views;
    }

    public void setViews(List<SupportedView> views) {
        this.views = views;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FileMetaDataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(FileMetaDataModel metadata) {
        this.metadata = metadata;
    }

    public List<FileModel> getChildren() {
        return children;
    }

    public void setChildren(List<FileModel> children) {
        this.children = children;
    }
}
