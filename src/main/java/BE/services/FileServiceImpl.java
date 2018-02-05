package BE.services;

import BE.entities.project.File;
import BE.exceptions.FileNotFoundException;
import BE.repositories.FileRepository;
import BE.repositories.Supported_ViewRepository;
import BE.responsemodels.file.FileMetaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository FileRepository;

    @Autowired
    Supported_ViewRepository Supported_ViewRepository;

    // Conversion Functions
    private FileMetaModel fileToMetaModel(File file) {
        return new FileMetaModel(
                file.getPath(),
                file.getFile_name(),
                file.getFileId(),
                // get supported views from table
                //TODO get key/value (view/additional_data_of_view) pairs by protocol
                Supported_ViewRepository.findByFileFileId(file.getFileId()),
                file.getMetadata(),
                file.getType(),
                file.getStatus()
        );
    }

    @Override
    public List<FileMetaModel> getAllFiles(String projectName) {
        List<FileMetaModel> files = new ArrayList<>();
        File root_dir = FileRepository.findByProjectName(projectName);
        FileRepository.findAll().forEach( file->{if(file.getPath().startsWith(root_dir.getPath())) files.add(this.fileToMetaModel(file));});
        return files;
    }

    //TODO recursive function to search through closure table(dir_contains) and build up path to find a file
    @Override
    public FileMetaModel getFile(String projectName, String filePath) {
        List<FileMetaModel> files = this.getAllFiles(projectName);
        for(FileMetaModel file : files)
            if(file.getPath().equals(filePath))
                return file;
        throw new FileNotFoundException();
    }

    @Override
    public FileMetaModel getFileByID(String projectName, int file_id) {
        List<FileMetaModel> files = this.getAllFiles(projectName);
        for(FileMetaModel file : files)
            if(file.getFile_id() == file_id)
                return file;
        throw new FileNotFoundException();
    }
    //TODO file metadata must be initial_metadata on creation by protocol ??
    @Override
    @Transactional
    public FileMetaModel createFile(String projectName, String filePath) {
        //TODO check if file with same name already exists in directory
//        if (this.getFile(projectName,filePath).getPath() != null) throw new UserAlreadyExistsException();
        File file;
        String file_name = filePath.substring(filePath.lastIndexOf("/"));
        //TODO may want to change distinguishing between file and dir?
        if(filePath.contains("."))
            file = new File(filePath,file_name, "filetype...", "file status...", "file metadata...");
        else
            file = new File(filePath,file_name, "dir",  "file status...", "file metadata...");
        //TODO create a file outside the DB before saving the metadata
        FileRepository.save(file);
        return this.fileToMetaModel(file);
    }

    @Override
    @Transactional
    public FileMetaModel updateFile(File file) {
        //TODO only root dir has project assigned need to change method
        if (this.getFileByID(file.getProject().getName(), file.getFileId()) == null) throw new FileNotFoundException();
        // .save performs both update and creation
        FileRepository.save(file);
        return this.fileToMetaModel(file);
    }


    @Override
    @Transactional
    public FileMetaModel deleteFile(String projectName, String filePath) {
        FileMetaModel file = this.getFile(projectName, filePath);
        if (file == null) throw new FileNotFoundException();
        FileRepository.delete(file.getFile_id());
        return file;
    }
}
