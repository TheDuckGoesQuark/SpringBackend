package BE.services;

import BE.entities.project.MetaFile;
import BE.exceptions.FileAlreadyExistsException;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.InvalidParentDirectoryException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.Dir_containsRepository;
import BE.repositories.FileRepository;
import BE.repositories.ProjectRepository;
import BE.repositories.SupportedViewRepository;
import BE.responsemodels.file.FileMetaDataModel;
import BE.responsemodels.file.FileModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    ProjectRepository ProjectRepository;

    @Autowired
    FileRepository FileRepository;

    @Autowired
    SupportedViewRepository Supported_ViewRepository;

    @Autowired
    Dir_containsRepository Dir_containsRepository;

    // Conversion Functions
    private FileModel fileToMetaModel(MetaFile metaFile) {
        return new FileModel(
                metaFile.getPath(),
                metaFile.getFile_name(),
                metaFile.getFileId(),
                metaFile.getSupported_views(),
                new FileMetaDataModel(metaFile.getLast_modified(), metaFile.getLength()),
                metaFile.getType(),
                metaFile.getStatus()
        );
    }

    @Override
    public List<FileModel> getAllFiles(String projectName) {
        if (ProjectRepository.findByName(projectName) == null)
            throw new ProjectNotFoundException();
        List<FileModel> files = new ArrayList<>();
        MetaFile root_dir = FileRepository.findByProjectName(projectName);
        FileRepository.findAll().forEach(file -> {
            if (file.getPath().startsWith(root_dir.getPath())) files.add(this.fileToMetaModel(file));
        });
        return files;
    }

    @Override
    public List<FileModel> getChildren(String projectName, String filePath) {
        List<FileModel> children = new ArrayList<>();
        FileModel dir = this.getFile(projectName, filePath);
//        for(Dir_contains file : dir.getContents()){
//            children.add(this.getFileByID(projectName,file.getMetaFile().getFileId()));
//        }
        Dir_containsRepository.findByDirId(dir.getFile_id()).forEach(dir_contains -> {
            children.add(this.getFileByID(projectName, dir_contains.getMetaFile().getFileId()));
        });
        return children;
    }

    //TODO recursive function to search through closure table(dir_contains) and build up path to find a file
    @Override
    public FileModel getFile(String projectName, String filePath) {
        List<FileModel> files = this.getAllFiles(projectName);
        for (FileModel file : files)
            if (file.getPath().equals(filePath))
                return file;
        throw new FileNotFoundException();
    }

    @Override
    public FileModel createFile(FileModel file, String action) {
        return null;
    }

    @Override
    //TODO write function for this in FileRepository to be a single operation
    public FileModel getFileByID(String projectName, int file_id) {
        List<FileModel> files = this.getAllFiles(projectName);
        for (FileModel file : files)
            if (file.getFile_id() == file_id)
                return file;
        throw new FileNotFoundException();
    }



    @Override
    @Transactional
    public FileModel updateFile(MetaFile metaFile) {
        //TODO only root dir has project assigned need to change method
        if (this.getFileByID(metaFile.getProject().getName(), metaFile.getFileId()) == null)
            throw new FileNotFoundException();
        // .save performs both update and creation
        FileRepository.save(metaFile);
        return this.fileToMetaModel(metaFile);
    }


    @Override
    @Transactional
    public FileModel deleteFile(String projectName, String filePath) {
        FileModel file = this.getFile(projectName, filePath);
        if (file == null) throw new FileNotFoundException();
        FileRepository.delete(file.getFile_id());
        return file;
    }

    //TODO 12.8 Uploading

//    @Override
//    @Transactional
//    public FileModel uploadFile(String projectName, String filePath) {
//        FileModel file = this.getMetaFile(projectName, filePath);
//        if (file == null) throw new FileNotFoundException();
//        FileRepository.delete(file.getFile_id());
//        return file;
//    }

    //TODO 12.9 Changing metadata
    //TODO 12.10 Creating directories
    //TODO 12.11 Deleting
    //TODO 12.12 Moving
    //TODO 12.13 Copying
    //TODO 12.14 Extra file types
    //TODO 12.13 Tabular files
    //TODO 13.1 The tabular view
    //TODO 14 Zoommable image files
    //TODO 14.1 The Scalable Image view
}
