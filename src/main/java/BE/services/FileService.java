package BE.services;

import BE.entities.project.File;
import BE.responsemodels.file.FileMetaModel;

import java.util.List;

public interface FileService {
    public List<FileMetaModel> getAllFiles(String projectName);
    public FileMetaModel getFile(String projectName, String filePath);
    public FileMetaModel createFile(String projectName, String filePath);
    public FileMetaModel deleteFile(String projectName, String filePath);
    public FileMetaModel getFileByID(String projectName, int file_id);
    public FileMetaModel updateFile(File file);
    //TODO methods for FileDataModel (probably NO just have a FileModel class with meta and data constructors)
}


