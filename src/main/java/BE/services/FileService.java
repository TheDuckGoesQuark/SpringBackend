package BE.services;

import BE.models.file.FileModel;
import BE.models.file.FileRequestOptions;
import BE.models.file.MoveFileRequestModel;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    FileModel getMetaFile(String projectName, String filePath);

    FileModel getFileMetaByID(int file_id);

    InputStream getRawFile(String projectName, String filePath);

    InputStream getRawFileByID(int file_id);

    FileModel createFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes);

    void deleteFile(String projectName, String filePath);

    void deleteFileById(int file_id);

    FileModel updateFileMeta(String project_name, String path);

    FileModel moveFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel);

    List<FileModel> getChildrenMeta(String projectName, String filePath);

    FileModel updateFile(String project_name, String relativeFilePath, FileRequestOptions options);
}


