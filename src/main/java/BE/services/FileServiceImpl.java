package BE.services;

import BE.entities.project.File;
import BE.exceptions.FileAlreadyExistsException;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.InvalidParentDirectoryException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.Dir_containsRepository;
import BE.repositories.FileRepository;
import BE.repositories.ProjectRepository;
import BE.repositories.Supported_ViewRepository;
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
    Supported_ViewRepository Supported_ViewRepository;

    @Autowired
    Dir_containsRepository Dir_containsRepository;

    // Conversion Functions
    private FileModel fileToMetaModel(File file) {
        return new FileModel(
                file.getPath(),
                file.getFile_name(),
                file.getFileId(),
                // get supported views from table
                //TODO get key/value (view/additional_data_of_view) pairs by protocol
                Supported_ViewRepository.findByFileFileId(file.getFileId()),
                file.getMetadata(),
                file.getType(),
                file.getStatus()
//                file.getContents()
        );
    }

    @Override
    public List<FileModel> getAllFiles(String projectName) {
        if(ProjectRepository.findByName(projectName) == null)
            throw new ProjectNotFoundException();
        List<FileModel> files = new ArrayList<>();
        File root_dir = FileRepository.findByProjectName(projectName);
        FileRepository.findAll().forEach( file->{if(file.getPath().startsWith(root_dir.getPath())) files.add(this.fileToMetaModel(file));});
        return files;
    }

    @Override
    public List<FileModel> getChildren(String projectName, String filePath) {
        List<FileModel> children = new ArrayList<>();
        FileModel dir = this.getFile(projectName, filePath);
//        for(Dir_contains file : dir.getContents()){
//            children.add(this.getFileByID(projectName,file.getFile().getFileId()));
//        }
        Dir_containsRepository.findByDirId(dir.getFile_id()).forEach(dir_contains->{children.add(this.getFileByID(projectName,dir_contains.getFile().getFileId()));});
        return children;
    }

    //TODO recursive function to search through closure table(dir_contains) and build up path to find a file
    @Override
    public FileModel getFile(String projectName, String filePath) {
        List<FileModel> files = this.getAllFiles(projectName);
        for(FileModel file : files)
            if(file.getPath().equals(filePath))
                return file;
        throw new FileNotFoundException();
    }

    @Override
    //TODO write function for this in FileRepository to be a single operation
    public FileModel getFileByID(String projectName, int file_id) {
        List<FileModel> files = this.getAllFiles(projectName);
        for(FileModel file : files)
            if(file.getFile_id() == file_id)
                return file;
        throw new FileNotFoundException();
    }
    //TODO file metadata must be initial_metadata on creation by protocol ??
    //TODO populate dir_contains with parent/child key pair. (this better be an after insert trigger in file table in the DB ???)
    //TODO overwrite, offset, truncate, final according to 12.8 protocol
    @Override
    @Transactional
    public FileModel createFile(FileModel fileModel, String action) {
        File file;
        int i = fileModel.getFile_name().lastIndexOf("/");
        String dir_name = fileModel.getFile_name().substring(0, i);
        java.io.File parent_dir = new java.io.File(dir_name);
        java.io.File same_file_name = new java.io.File(fileModel.getPath());
        if(!parent_dir.exists())
            throw new InvalidParentDirectoryException();
        if(!same_file_name.exists())
            throw new FileAlreadyExistsException();
        //TODO may want to change distinguishing between file and dir?
        if(action.equalsIgnoreCase("upload")){
            file = new File(fileModel.getPath(),fileModel.getFile_name(), fileModel.getType(), fileModel.getStatus(), fileModel.getMetadata());
            //TODO how to return only id:string , created:boolean object
            //{
            //   "id": string,
            //   "created": boolean
            //}
            //TODO create a file outside the DB before saving the metadata
            FileRepository.save(file);
            return this.fileToMetaModel(file);
        }

        else if(action.equalsIgnoreCase("mkdir")){
            file = new File(fileModel.getPath(),fileModel.getFile_name(), fileModel.getType(), fileModel.getStatus(), fileModel.getMetadata());
            //TODO Figure out how to return only id:string JSON object
            //{
            //    "id": string
            //}
            //TODO create a file outside the DB before saving the metadata
            FileRepository.save(file);
            return this.fileToMetaModel(file);
        }
    }

    @Override
    @Transactional
    public FileModel updateFile(File file) {
        //TODO only root dir has project assigned need to change method
        if (this.getFileByID(file.getProject().getName(), file.getFileId()) == null) throw new FileNotFoundException();
        // .save performs both update and creation
        FileRepository.save(file);
        return this.fileToMetaModel(file);
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
//        FileModel file = this.getFile(projectName, filePath);
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
