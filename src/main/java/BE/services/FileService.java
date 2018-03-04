package BE.services;

import BE.responsemodels.file.FileModel;
import BE.responsemodels.file.FileRequestOptions;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    List<FileModel> getAllMetaFiles(String projectName);

    FileModel getMetaFile(String projectName, String filePath);

    FileModel getFileMetaByID(int file_id);

    InputStream getRawFile(String projectName, String filePath);

    InputStream getRawFileByID(int file_id);

    FileModel createFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes);

    FileModel deleteFile(String projectName, String filePath);

    FileModel updateFileMeta(String project_name, String path, String action);

    List<FileModel> getChildrenMeta(String projectName, String filePath);
}


