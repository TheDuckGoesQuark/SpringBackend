package BE.services;

import BE.entities.project.MetaFile;
import BE.responsemodels.file.FileModel;

import java.util.List;

public interface FileService {
    public List<FileModel> getAllFiles(String projectName);
    public FileModel getFile(String projectName, String filePath);
    public FileModel createFile(FileModel file, String action);
    public FileModel deleteFile(String projectName, String filePath);
    public FileModel getFileByID(String projectName, int file_id);
    public FileModel updateFile(MetaFile metaFile);
    public List<FileModel> getChildren(String projectName, String filePath);
    //TODO methods for FileDataModel (probably NO just have a FileModel class with meta and data constructors)
}


