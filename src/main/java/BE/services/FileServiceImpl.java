package BE.services;

import BE.entities.project.MetaFile;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.NotImplementedException;
import BE.exceptions.ProjectNotFoundException;
import BE.repositories.Dir_containsRepository;
import BE.repositories.FileRepository;
import BE.repositories.ProjectRepository;
import BE.repositories.SupportedViewRepository;
import BE.responsemodels.file.FileMetaDataModel;
import BE.responsemodels.file.FileModel;

import BE.responsemodels.file.FileRequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final
    ProjectService projectService;

    private final
    StorageService storageService;

    private final
    FileRepository fileRepository;

    private final
    SupportedViewRepository supportedViewRepository;

    private final
    Dir_containsRepository dirContainsRepository;

    @Autowired
    public FileServiceImpl(ProjectService projectService, StorageService storageService, FileRepository fileRepository, SupportedViewRepository supportedViewRepository, Dir_containsRepository dirContainsRepository) {
        this.projectService = projectService;
        this.storageService = storageService;
        this.fileRepository = fileRepository;
        this.supportedViewRepository = supportedViewRepository;
        this.dirContainsRepository = dirContainsRepository;
    }

    // Conversion Functions
    private FileModel metaFileToFileModel(MetaFile metaFile) {
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

    /**
     * Gets all MetaFiles
     * @param projectName
     * @return list of files
     */
    @Override
    public List<FileModel> getAllMetaFiles(String projectName) {
        if (projectService.getProjectByName(projectName) == null)
            throw new ProjectNotFoundException();
        List<FileModel> files = new ArrayList<>();
        MetaFile root_dir = fileRepository.findByProjectName(projectName);
        fileRepository.findAll().forEach(file -> {
            if (file.getPath().startsWith(root_dir.getPath())) files.add(this.metaFileToFileModel(file));
        });
        return files;
    }

    @Override
    public List<FileModel> getChildrenMeta(String projectName, String filePath) {
/*        List<FileModel> children = new ArrayList<>();
        FileModel dir = this.getMetaFile(projectName, filePath);
//        for(Dir_contains file : dir.getContents()){
//            children.add(this.getFileMetaByID(projectName,file.getFile().getFileId()));
//        }
        dirContainsRepository.findByDirId(dir.getFile_id()).forEach(dir_contains -> {
            children.add(this.getFileMetaByID(projectName, dir_contains.getMetaFile().getFileId()));
        });*/
        throw new NotImplementedException();
    }

    @Override
    public FileModel getMetaFile(String projectName, String filePath) {
        throw new NotImplementedException();
    }

    /**
     * Gets specific MetaFile by id
     * @param file_id the id of the file to get
     * @return file
     */
    @Override
    public FileModel getFileMetaByID(int file_id) {
        MetaFile metaFile = fileRepository.findByFileId(file_id);
        if (metaFile != null) return metaFileToFileModel(metaFile);
        else throw new FileNotFoundException();
    }

    @Override
    public InputStream getRawFile(String projectName, String filePath) {
        return null;
    }

    @Override
    public InputStream getRawFileByID(int file_id) {
        return null;
    }

    @Override
    @Transactional
    public FileModel createFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes) {
        return null;
    }

    /**
     * Deletes specific file
     * @param projectName the name of the project to delete
     * @param filePath the path of the file to delete
     * @return file
     */
    @Override
    @Transactional
    public FileModel deleteFile(String projectName, String filePath) {
        FileModel file = this.getMetaFile(projectName, filePath);
        if (file == null) throw new FileNotFoundException();
        fileRepository.delete(file.getFile_id());

        return file;
    }

    @Override
    @Transactional
    public FileModel updateFileMeta(String project_name, String path, String action) {
        throw new NotImplementedException();
    }


    //TODO 12.8 Uploading

//    @Override
//    @Transactional
//    public FileModel uploadFile(String projectName, String filePath) {
//        FileModel file = this.getFile(projectName, filePath);
//        if (file == null) throw new FileNotFoundException();
//        fileRepository.delete(file.getFile_id());
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
