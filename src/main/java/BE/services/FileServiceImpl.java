package BE.services;

import BE.controllers.Action;
import BE.entities.project.FileStatus;
import BE.entities.project.FileTypes;
import BE.entities.project.MetaFile;
import BE.entities.project.SupportedView;
import BE.exceptions.FileAlreadyExistsException;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.InvalidParentDirectoryException;
import BE.exceptions.NotImplementedException;
import BE.exceptions.excludedFromBaseException.RootFileDeletionException;
import BE.models.file.MoveFileRequestModel;
import BE.repositories.FileRepository;
import BE.repositories.SupportedViewRepository;
import BE.models.file.FileMetaDataModel;
import BE.models.file.FileModel;

import BE.models.file.FileRequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final
    FileRepository fileRepository;

    private final
    ProjectService projectService;

    private final
    StorageService storageService;

    private final
    SupportedViewRepository supportedViewRepository;

    public static final List<SupportedView> FILE_SUPPORTED_VIEWS = new ArrayList<>();
    public static final List<SupportedView> DIRECTORY_SUPPORTED_VIEWS = new ArrayList<>();

    private void initialiseDefaults() {
        SupportedView meta = supportedViewRepository.findByView(SupportedView.META_VIEW);
        SupportedView raw = supportedViewRepository.findByView(SupportedView.RAW_VIEW);

        DIRECTORY_SUPPORTED_VIEWS.add(meta);
        FILE_SUPPORTED_VIEWS.add(meta);
        FILE_SUPPORTED_VIEWS.add(raw);
    }

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ProjectService projectService, StorageService storageService, SupportedViewRepository supportedViewRepository) {
        this.fileRepository = fileRepository;
        this.projectService = projectService;
        this.storageService = storageService;
        this.supportedViewRepository = supportedViewRepository;
        this.initialiseDefaults();

    }

    // Conversion Functions
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
            if (parent != null && !entry.equals("")) {
                parent = parent.getChildren().stream().filter(child -> child.getFile_name().equals(entry)).findAny().orElse(null);
            } else break;
        }

        if (parent != null) return parent;
        else throw new FileNotFoundException();
    }

    private MetaFile getParentFromPath(String project_name, String path) {
        String parentPath = "";

        if(path.lastIndexOf(MetaFile.FILE_PATH_DELIMITER) > 0)
            parentPath = path.substring(0, path.lastIndexOf(MetaFile.FILE_PATH_DELIMITER));

        return getMetaFileFromPath(project_name, parentPath);
    }

    @Override
    public List<FileModel> getChildrenMeta(String projectName, String filePath) {
        MetaFile root = fileRepository.findByFileId(projectService.getProjectRootDirId(projectName));
        return root.getChildren().stream().map(FileServiceImpl::metaFileToFileModel).collect(Collectors.toList());
    }

    @Override
    public FileModel updateFile(String project_name, String relativeFilePath, FileRequestOptions options) {
        throw new NotImplementedException();
    }

    @Override
    public FileModel getMetaFile(String projectName, String filePath) {
        return metaFileToFileModel(getMetaFileFromPath(projectName, filePath));
    }

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
                    getFilenameFromPath(path),
                    parent);
        } else {
            metaFile = MetaFile.createFile(
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
    public FileModel updateFileMeta(String project_name, String path) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public FileModel moveFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel) {
        // get path, either from id or given path
        String new_path;
        if (moveFileRequestModel.getId() != null) {
            MetaFile dest = fileRepository.findByFileId(moveFileRequestModel.getId());
            new_path = dest.getPath();
        } else {
            new_path = moveFileRequestModel.getPath();
            if (path.equals("")) throw new RootFileDeletionException();
        }

        // delete file at that location if it exists
        try {
            MetaFile dest = getMetaFileFromPath(project_name, new_path);
            if (dest != null) deleteRecursively(dest);
        } catch (FileNotFoundException ignore) {}

        // change parent to new directory
        MetaFile original = getMetaFileFromPath(project_name, path);
        MetaFile destParent = getParentFromPath(project_name, new_path);
        if (!destParent.getType().equals(FileTypes.DIR)) throw new InvalidParentDirectoryException();
        original.setParent(destParent);

        // check for name change
        String new_name = getFilenameFromPath(new_path);
        if (!new_name.equals(original.getFile_name())) original.setFile_name(new_name);

        original = fileRepository.save(original);
        return metaFileToFileModel(original);
    }


    //TODO 12.9 Changing metadata
    //TODO 12.13 Copying
    //TODO 12.14 Extra file types
    //TODO 12.13 Tabular files
    //TODO 13.1 The tabular view
    //TODO 14 Zoommable image files
    //TODO 14.1 The Scalable Image view
}
