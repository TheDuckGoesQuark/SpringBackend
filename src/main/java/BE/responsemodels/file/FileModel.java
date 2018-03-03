package BE.responsemodels.file;

import BE.entities.project.SupportedView;

import java.util.List;
//TODO this may actually have to be named FileModel containing two constructors one for file meta protocol and one for file data protocol.
public class FileModel {

    private String path;

    private String file_name;

    private int file_id;

    private List<SupportedView> views;

    private String metadata;

    private String type;

    private String status;

//    private List<Dir_contains> contents;

    //file meta model
    public FileModel(String path, String file_name, int file_id, List<SupportedView> views, String metadata, String type, String status) {
        this.path = path;
        this.file_name = file_name;
        this.file_id = file_id;
        this.views = views;
        this.metadata = metadata;
        this.type = type;
        this.status = status;
//        this.contents = contents;
    }

    //dir meta model
//    public FileModel(List<Dir_contains> contents) {
//        this.contents = contents;
//    }

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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

//    public List<Dir_contains> getContents() {
//        return contents;
//    }
//
//    public void setContents(List<Dir_contains> contents) {
//        this.contents = contents;
//    }
}
