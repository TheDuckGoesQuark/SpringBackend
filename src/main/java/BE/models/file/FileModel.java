package BE.models.file;

import BE.models.MetaDataModel;
import BE.models.file.supportedviewinfoobjects.BaseSupportedViewInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public class FileModel {

    public static final String ROOT_FILE_NAME= "";

    private String file_path;

    private String file_name;

    private int id;

    private Map<String, BaseSupportedViewInfo> supported_views;

    private MetaDataModel metadata;

    private String type;

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FileModel> children;

    // File meta model
    public FileModel(String file_path, String file_name, int id, Map<String, BaseSupportedViewInfo> supported_views, MetaDataModel metadata, String type, String status) {
        this.file_path = file_path;
        this.file_name = file_name;
        this.id = id;
        this.supported_views = supported_views;
        this.metadata = metadata;
        this.type = type;
        this.status = status;
    }

    // Directory meta model
    public FileModel(String file_path, String file_name, int id, Map<String, BaseSupportedViewInfo> supported_views, MetaDataModel metadata, String type, String status, List<FileModel> children) {
        this.file_path = file_path;
        this.file_name = file_name;
        this.id = id;
        this.supported_views = supported_views;
        this.metadata = metadata;
        this.type = type;
        this.status = status;
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
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

    public Map<String, BaseSupportedViewInfo> getSupported_views() {
        return supported_views;
    }

    public void setSupported_views(Map<String, BaseSupportedViewInfo> supported_views) {
        this.supported_views = supported_views;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MetaDataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataModel metadata) {
        this.metadata = metadata;
    }

    public List<FileModel> getChildren() {
        return children;
    }

    public void setChildren(List<FileModel> children) {
        this.children = children;
    }
}
