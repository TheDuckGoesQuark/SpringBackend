package BE.services;

import BE.entities.project.File;

import java.util.List;

public interface FileService {
    public List<File> getAllFiles(String projectName);
//    public File getFile(String projectName, String filePath);

}


