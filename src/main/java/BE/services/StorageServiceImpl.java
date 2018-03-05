package BE.services;

import BE.exceptions.FileNotFoundException;
import BE.exceptions.FileRetrievalException;
import BE.responsemodels.file.FileRequestOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class StorageServiceImpl implements StorageService {

    /**
     * Gets a specific file input stream
     * @param file_id the id of the file to read
     * @return input stream
     */
    @Override
    public InputStream getFileStream(int file_id) {
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
    public boolean deleteFile(int file_id) {
        File file = new File(Integer.toString(file_id));
        if (file.exists()) return file.delete();
        else throw new FileNotFoundException();
    }
}
