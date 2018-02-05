package BE.services;

import BE.entities.project.File;
import BE.responsemodels.file.FileModel;

import java.util.List;

public interface FileService {
    public List<FileModel> getAllFiles(String projectName);
    public FileModel getFile(String projectName, String filePath);
    public FileModel createFile(String projectName, String filePath);
    public FileModel deleteFile(String projectName, String filePath);
    public FileModel getFileByID(String projectName, int file_id);
    public FileModel updateFile(File file);
    public List<FileModel> getChildren(String projectName, String filePath);
    //TODO methods for FileDataModel (probably NO just have a FileModel class with meta and data constructors)
}


