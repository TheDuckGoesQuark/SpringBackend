package BE.services;

import BE.entities.project.File;

import java.util.List;

public interface FileService {
    public List<File> getAllFiles(String projectName);
    public File getFile(String projectName, String filePath);
    public File createFile(String projectName, String filePath);
    public File deleteFile(String projectName, String filePath);
    public File getFileByID(String projectName, int file_id);

}


