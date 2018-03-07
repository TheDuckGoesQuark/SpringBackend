package BE.services;

import BE.models.file.FileRequestOptions;

import java.io.InputStream;

public interface StorageService {
    InputStream getFileStream(int file_id);

    boolean uploadFile(int file_id, FileRequestOptions options, byte[] bytes);

    boolean deleteFile(int file_id);
}
