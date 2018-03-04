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

    @Override
    public boolean uploadFile(int file_id, FileRequestOptions options, byte[] bytes) {
        File file = new File(Integer.toString(file_id));
        return false;
    }

    @Override
    public boolean deleteFile(int file_id) {
        File file = new File(Integer.toString(file_id));
        if (file.exists()) return file.delete();
        else throw new FileNotFoundException();
    }
}
