package BE.services;

import BE.controllers.Action;
import BE.entities.project.FileStatus;
import BE.entities.project.FileTypes;
import BE.entities.project.MetaFile;
import BE.exceptions.FileAlreadyExistsException;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.NotImplementedException;
import BE.repositories.FileRepository;
import BE.responsemodels.file.FileMetaDataModel;
import BE.responsemodels.file.FileModel;

import BE.responsemodels.file.FileRequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static BE.entities.project.SupportedView.FILE_SUPPORTED_VIEWS;

@Service
public class FileServiceImpl implements FileService {

    private final
    FileRepository fileRepository;

    private final
    ProjectService projectService;

    private final
    StorageService storageService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ProjectService projectService, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.projectService = projectService;
        this.storageService = storageService;
    }

    // Conversion Functions

    /**
     * Converts a specific meta file to a file model
     * @param metaFile the meta file to be converted
     * @return file model
     */
    private static FileModel metaFileToFileModel(MetaFile metaFile) {
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
     * Gets all meta files
     * @param projectName
     * @return a list of all meta files
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
    private static String getFilenameFromPath(String path) {
        File file = new File(path);
        return file.getName();
    }

    private static String getTypeFromFilename(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        } else {
            return "none";
        }
        return extension;
    }

    private MetaFile getMetaFileFromPath(String projectName, String filePath) {
        MetaFile root = fileRepository.findByFileId(projectService.getProjectRootDirId(projectName));
        String[] entries = filePath.split(MetaFile.FILE_PATH_DELIMITER);

        MetaFile parent = root;
        for (String entry : entries) {
            if (parent != null) {
                parent = parent.getChildren().stream().filter(child -> child.getFile_name().equals(entry)).findAny().orElse(null);
            } else break;
        }

        if (parent != null) return parent;
        else throw new FileNotFoundException();
    }

    private MetaFile getParentFromPath(String project_name, String path) {
        String parentPath = path.substring(0, path.lastIndexOf(MetaFile.FILE_PATH_DELIMITER));
        return getMetaFileFromPath(project_name, parentPath);
    }

    @Override
    public List<FileModel> getChildrenMeta(String projectName, String filePath) {
        MetaFile root = fileRepository.findByFileId(projectService.getProjectRootDirId(projectName));
        return root.getChildren().stream().map(FileServiceImpl::metaFileToFileModel).collect(Collectors.toList());
    }

    @Override
    public FileModel getMetaFile(String projectName, String filePath) {
        return metaFileToFileModel(getMetaFileFromPath(projectName, filePath));
    }

    /**
     * Gets a specific meta file by id
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
        int id = getMetaFile(projectName, filePath).getFile_id();
        return storageService.getFileStream(id);
    }

    @Override
    public InputStream getRawFileByID(int file_id) {
        return null;
    }

    @Override
    @Transactional
    public FileModel createFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes) {
        if (path.equals("")) throw new FileAlreadyExistsException(); // root will already exist

        MetaFile parent = getParentFromPath(project_name, path);
        MetaFile metaFile;

        if (action.equals(Action.MAKE_DIRECTORY)) {
            metaFile = MetaFile.createDirectory(
                    path,
                    getFilenameFromPath(path),
                    parent);
        } else {
            metaFile = MetaFile.createFile(
                    path,
                    getFilenameFromPath(path),
                    getTypeFromFilename(path),
                    options.isFinal() ? FileStatus.READY : FileStatus.UPLOADING,
                    bytes.length,
                    FILE_SUPPORTED_VIEWS,
                    parent);
        }

        MetaFile created = fileRepository.save(metaFile);

        // Add physical file to storage
        if (!action.equals(Action.MAKE_DIRECTORY)) {
            storageService.uploadFile(created.getFileId(), options, bytes);
        }

        return metaFileToFileModel(created);
    }

    private void deleteRecursively(MetaFile parent) {
        // Delete children
        if (parent.getType().equals(FileTypes.DIR)) {
            parent.getChildren().forEach(this::deleteRecursively);
        }

        // Delete this
        fileRepository.delete(parent.getFileId());
        if (!parent.getType().equals(FileTypes.DIR)) storageService.deleteFile(parent.getFileId());
    }

    /**
     * Deletes a specific file
     * @param projectName the name of the project to delete
     * @param filePath the path of the file to delete
     * @return file
     */
    @Override
    @Transactional
    public void deleteFile(String projectName, String filePath) {
        MetaFile deletionRoot = this.getMetaFileFromPath(projectName, filePath);

        deleteRecursively(deletionRoot);
    }

    @Override
    @Transactional
    public void deleteFileById(int file_id) {
        MetaFile metaFile = fileRepository.findByFileId(file_id);

        deleteRecursively(metaFile);
    }

    @Override
    @Transactional
    public FileModel updateFileMeta(String project_name, String path, String action) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public FileModel moveFile(String project_name, String path, String newPath) {
        throw new NotImplementedException();
    }


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
