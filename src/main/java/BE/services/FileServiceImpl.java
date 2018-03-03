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
                // get supported views from table
                //TODO get key/value (view/additional_data_of_view) pairs by protocol
                Supported_ViewRepository.findByMetaFile_FileId(metaFile.getFileId()),
                metaFile.getMetadata(),
                metaFile.getType(),
                metaFile.getStatus()
//                metaFile.getContents()
        );
    }

    @Override
    public List<FileModel> getAllFiles(String projectName) {
        if(ProjectRepository.findByName(projectName) == null)
            throw new ProjectNotFoundException();
        List<FileModel> files = new ArrayList<>();
        MetaFile root_dir = FileRepository.findByProjectName(projectName);
        FileRepository.findAll().forEach( file->{if(file.getPath().startsWith(root_dir.getPath())) files.add(this.fileToMetaModel(file));});
        return files;
    }

    @Override
    public List<FileModel> getChildren(String projectName, String filePath) {
        List<FileModel> children = new ArrayList<>();
        FileModel dir = this.getFile(projectName, filePath);
//        for(Dir_contains file : dir.getContents()){
//            children.add(this.getFileByID(projectName,file.getMetaFile().getFileId()));
//        }
        Dir_containsRepository.findByDirId(dir.getFile_id()).forEach(dir_contains->{children.add(this.getFileByID(projectName,dir_contains.getMetaFile().getFileId()));});
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
        MetaFile metaFile;
        int i = fileModel.getFile_name().lastIndexOf("/");
        String dir_name = fileModel.getFile_name().substring(0, i);
        java.io.File parent_dir = new java.io.File(dir_name);
        java.io.File same_file_name = new java.io.File(fileModel.getPath());
        if(!parent_dir.exists())
            throw new InvalidParentDirectoryException();
        if(!same_file_name.exists())
            throw new FileAlreadyExistsException();
        //TODO may want to change distinguishing between metaFile and dir?
        if(action.equalsIgnoreCase("upload")){
            metaFile = new MetaFile(fileModel.getPath(),fileModel.getFile_name(), fileModel.getType(), fileModel.getStatus(), fileModel.getMetadata(), last_modified, length, supported_views);
            //TODO how to return only id:string , created:boolean object
            //{
            //   "id": string,
            //   "created": boolean
            //}
            //TODO create a metaFile outside the DB before saving the metadata
            FileRepository.save(metaFile);
//            java.io.MetaFile realFile = new java.io.MetaFile(fileModel.getPath());
            return this.fileToMetaModel(metaFile);
        }

        else//(action.equalsIgnoreCase("mkdir")){
        { metaFile = new MetaFile(fileModel.getPath(),fileModel.getFile_name(), fileModel.getType(), fileModel.getStatus(), fileModel.getMetadata(), last_modified, length, supported_views);
            //TODO Figure out how to return only id:string JSON object
            //{
            //    "id": string
            //}
            //TODO create a metaFile outside the DB before saving the metadata
            FileRepository.save(metaFile);
            return this.fileToMetaModel(metaFile);
        }

    }

    @Override
    @Transactional
    public FileModel updateFile(MetaFile metaFile) {
        //TODO only root dir has project assigned need to change method
        if (this.getFileByID(metaFile.getProject().getName(), metaFile.getFileId()) == null) throw new FileNotFoundException();
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
