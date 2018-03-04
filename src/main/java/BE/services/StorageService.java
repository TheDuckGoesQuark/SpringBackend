package BE.services;

import BE.responsemodels.file.FileRequestOptions;

import java.io.InputStream;

public interface StorageService {
    InputStream getFileStream(int file_id);

    void uploadFile(int file_id, FileRequestOptions options, byte[] bytes);
}
