package BE.services;

import BE.entities.project.MetaFile;
import BE.responsemodels.file.FileModel;
import BE.responsemodels.file.FileRequestOptions;

import java.util.List;

public interface MetaFileService {
    List<FileModel> getAllMetaFiles(String projectName);

    FileModel getMetaFile(String projectName, String filePath);

    FileModel createMetaFile(String project_name, String path, String action);

    FileModel deleteMetaFile(String projectName, String filePath);

    FileModel getFileMetaByID(String projectName, int file_id);

    FileModel updateFileMeta(String project_name, String path, String action);

    List<FileModel> getChildrenMeta(String projectName, String filePath);
}


