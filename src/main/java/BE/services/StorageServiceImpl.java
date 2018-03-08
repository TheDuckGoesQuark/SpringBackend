package BE.services;

import BE.exceptions.FileNotFoundException;
import BE.exceptions.FileOperationException;
import BE.exceptions.FileRetrievalException;
import BE.models.file.FileRequestOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class StorageServiceImpl implements StorageService {

    /**
     * Gets a specific file input stream
     * @param file_id the id of the file to read
     * @return input stream
     */
    @Override
    public InputStream getFileStream(int file_id) throws FileRetrievalException{
        File file = new File(Integer.toString(file_id));

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
        File file = new File(Integer.toString(file_id));
        return false;
    }

    /**
     * Deletes a specific file
     * @param file_id the id of the file to be deleted
     * @return
     */
    @Override
    public boolean deleteFile(int file_id) throws FileNotFoundException {
        File file = new File(Integer.toString(file_id));
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
        File src = new File(Integer.toString(src_id));
        File dest = new File(Integer.toString(dest_id));

        if (!src.exists()) throw new FileNotFoundException();
        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
        return true;
    }
}
