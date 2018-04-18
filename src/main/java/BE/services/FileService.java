package BE.services;

import BE.models.file.FileModel;
import BE.models.file.FileRequestOptions;
import BE.models.file.MoveFileRequestModel;

import java.io.InputStream;

public interface FileService {

    FileModel getMetaFile(String projectName, String filePath);

    FileModel getFileMetaByID(int file_id);

    FileModel getFileMetaWithChildrenById(int file_id);

    InputStream getRawFile(String projectName, String filePath);

    InputStream getRawFileByID(int file_id);

    InputStream getTabularFile(String projectName, String filePath, FileRequestOptions fileRequestOptions);

    InputStream getTabularFileById(int file_id, FileRequestOptions fileRequestOptions);

    boolean supportsView(int file_id, String view);

    boolean supportsView(String project_name, String filePath, String view);

    FileModel createFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes);

    void deleteFile(String projectName, String filePath);

    void deleteFileById(int file_id);

    FileModel updateFileMeta(String project_name, String path);

    FileModel moveFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel);

    FileModel getFileMetaWithChildren(String projectName, String filePath);

    FileModel updateFile(String project_name, String relativeFilePath, FileRequestOptions options);

    FileModel copyFile(String project_name, String relativeFilePath, MoveFileRequestModel moveFileRequestModel);
}


