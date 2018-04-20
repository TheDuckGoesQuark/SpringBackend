package BE.services;

import BE.models.file.FileModel;
import BE.models.file.FileRequestOptions;
import BE.models.file.MoveFileRequestModel;
import BE.models.file.SupportsViewModel;

import java.io.InputStream;

/**
 * File service provides methods for retrieving project files. All files can be accessed either be path or by id.
 */
public interface FileService {

    FileModel getMetaFile(String projectName, String filePath);

    FileModel getMetaFile(int file_id);

    FileModel getMetaFileWithChildren(String projectName, String filePath);

    FileModel getMetaFileWithChildren(int file_id);

    InputStream getRawFile(String projectName, String filePath);

    InputStream getRawFile(int file_id);

    InputStream getTabularFile(String projectName, String filePath, FileRequestOptions fileRequestOptions);

    InputStream getTabularFile(int file_id, FileRequestOptions fileRequestOptions);

    SupportsViewModel supportsView(String project_name, String filePath, String view);

    SupportsViewModel supportsView(String project_name, int file_id, String view);

    FileModel createOrUpdateFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes);

    FileModel createOrUpdateFile(String project_name, int file_id, String action, FileRequestOptions options, byte[] bytes);

    void deleteFile(String projectName, String filePath);

    void deleteFile(String project_name, int file_id);

    FileModel updateFileMetaData(String project_name, String path);

    FileModel updateFileMetaData(String project_name, int file_id);

    FileModel moveFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel);

    FileModel moveFile(String project_name, int file_id, MoveFileRequestModel moveFileRequestModel);

    FileModel copyFile(String project_name, String relativeFilePath, MoveFileRequestModel moveFileRequestModel);

    FileModel copyFile(String project_name, int file_id, MoveFileRequestModel moveFileRequestModel);

}


