package BE.services;

import BE.exceptions.FileAlreadyExistsException;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.FileOperationException;
import BE.exceptions.FileRetrievalException;
import BE.models.file.FileRequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;

@Service
public class StorageServiceImpl implements StorageService {

    private final String root_directory;

    public StorageServiceImpl(@Value("${storage_root_directory:storage}") String root_directory) {
        this.root_directory = root_directory.replaceAll("^.|.$", "")+"/";
    }

    /**
     * Gets a specific file input stream
     * @param file_id the id of the file to read
     * @return input stream
     */
    @Override
    public InputStream getFileStream(int file_id) throws FileRetrievalException{
        File file = new File(root_directory + Integer.toString(file_id));

        if (!file.exists()) throw new FileNotFoundException();
        else try {
            return new FileInputStream(file);
        } catch (java.io.FileNotFoundException e) {
            throw new FileRetrievalException();
        }
    }

    /**
     * Uploads a new file
     * @param file_id the id of the file to be uploaded
     * @param options
     * @param bytes contents of the file to be uploaded
     * @return
     */
    @Override
    public boolean uploadFile(int file_id, FileRequestOptions options, byte[] bytes) {
        File file = new File(root_directory + Integer.toString(file_id));
        if (file.exists()) throw new FileAlreadyExistsException();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (Exception e) {
            throw new FileOperationException(e);
        }
        return true;
    }

    /**
     * Deletes a specific file
     * @param file_id the id of the file to be deleted
     * @return
     */
    @Override
    public boolean deleteFile(int file_id) throws FileNotFoundException {
        File file = new File(root_directory + Integer.toString(file_id));
        if (file.exists()) return file.delete();
        else throw new FileNotFoundException();
    }

    /**
     * Copies a specific file
     * @param src_id the id of the file to be copied
     * @param dest_id the id of of the destination file
     * @return true if successful, exception otherwise
     */
    @Override
    public boolean copyFile(int src_id, int dest_id) throws FileNotFoundException {
        File src = new File(root_directory + Integer.toString(src_id));
        File dest = new File(root_directory + Integer.toString(dest_id));

        if (!src.exists()) throw new FileNotFoundException();
        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
        return true;
    }
}
