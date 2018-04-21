package BE.services;

import BE.controllers.Action;
import BE.entities.MetaData;
import BE.entities.project.FileStatus;
import BE.entities.project.FileTypes;
import BE.entities.project.MetaFile;
import BE.entities.project.SupportedView;
import BE.entities.project.tabular.Header;
import BE.entities.project.tabular.RowCount;
import BE.exceptions.*;
import BE.exceptions.FileNotFoundException;
import BE.exceptions.RootFileDeletionException;
import BE.models.MetaDataModel;
import BE.models.file.*;
import BE.repositories.FileRepository;
import BE.repositories.SupportedViewRepository;

import BE.util.TabularParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static BE.models.file.FileModel.ROOT_FILE_NAME;
import static BE.util.MetaFileUtil.*;
import static BE.util.TabularParser.applyTabularSettings;

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
    public static final List<SupportedView> TABULAR_SUPPORTED_VIEWS = new ArrayList<>();

    private void initialiseDefaults() {
        // Init database with supported view types
        SupportedView meta = supportedViewRepository.findByView(SupportedView.META_VIEW);
        if (meta == null) meta = supportedViewRepository.save(new SupportedView(SupportedView.META_VIEW));
        SupportedView raw = supportedViewRepository.findByView(SupportedView.RAW_VIEW);
        if (raw == null) raw = supportedViewRepository.save(new SupportedView(SupportedView.RAW_VIEW));
        // Init local constants for easy file creation
        SupportedView tabular = supportedViewRepository.findByView(SupportedView.TABULAR_VIEW);
        if (tabular == null) tabular = supportedViewRepository.save(new SupportedView(SupportedView.TABULAR_VIEW));
        DIRECTORY_SUPPORTED_VIEWS.add(meta);
        FILE_SUPPORTED_VIEWS.addAll(DIRECTORY_SUPPORTED_VIEWS);
        FILE_SUPPORTED_VIEWS.add(raw);
        TABULAR_SUPPORTED_VIEWS.addAll(FILE_SUPPORTED_VIEWS);
        TABULAR_SUPPORTED_VIEWS.add(tabular);
    }

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ProjectService projectService, StorageService storageService, SupportedViewRepository supportedViewRepository) {
        this.fileRepository = fileRepository;
        this.projectService = projectService;
        this.storageService = storageService;
        this.supportedViewRepository = supportedViewRepository;
        this.initialiseDefaults();
    }

    private MetaFile getMetaFileFromPath(String projectName, String filePath) {
        MetaFile root = fileRepository.findByFileId(projectService.getProjectRootDirId(projectName));

        String[] entries = filePath.split(MetaFile.FILE_PATH_DELIMITER);

        MetaFile parent = root;
        for (String entry : entries) {
            if (parent != null && !entry.equals(ROOT_FILE_NAME)) {
                parent = parent.getChildren().stream().filter(child -> child.getFile_name().equals(entry)).findAny().orElse(null);
            } else break;
        }

        if (parent != null) return parent;
        else throw new FileNotFoundException();
    }

    private MetaFile getParentFromPath(String project_name, String path) {
        String parentPath = ROOT_FILE_NAME;

        if (path.lastIndexOf(MetaFile.FILE_PATH_DELIMITER) > 0)
            parentPath = path.substring(0, path.lastIndexOf(MetaFile.FILE_PATH_DELIMITER));

        return getMetaFileFromPath(project_name, parentPath);
    }

    private String getPathFromMoveFileRequest(MoveFileRequestModel moveFileRequestModel) {
        // get path, either from id or given path
        if (moveFileRequestModel.getId() != null) {
            MetaFile dest = fileRepository.findByFileId(moveFileRequestModel.getId());
            return dest.getPath();
        } else {
            return moveFileRequestModel.getPath();
        }
    }

    @Override
    public FileModel getMetaFile(String projectName, String filePath) {
        return metaFileToFileModel(getMetaFileFromPath(projectName, filePath));
    }

    /**
     * Gets a specific meta file by id
     *
     * @param file_id the id of the file to get
     * @return file
     */
    @Override
    public FileModel getMetaFile(int file_id) {
        MetaFile metaFile = fileRepository.findByFileId(file_id);
        if (metaFile != null) return metaFileToFileModel(metaFile);
        else throw new FileNotFoundException();
    }

    @Override
    public FileModel getMetaFileWithChildren(String projectName, String filePath) {
        MetaFile root = fileRepository.findByFileId(projectService.getProjectRootDirId(projectName));
        return getFileModelWithChildren(root);
    }

    @Override
    public FileModel getMetaFileWithChildren(int file_id) {
        MetaFile root = fileRepository.findByFileId(file_id);
        return getFileModelWithChildren(root);
    }

    @Override
    public InputStream getRawFile(String projectName, String filePath) {
        int id = getMetaFile(projectName, filePath).getFile_id();
        return storageService.getFileStream(id);
    }

    @Override
    public InputStream getRawFile(int file_id) {
        return storageService.getFileStream(file_id);
    }

    @Override
    public InputStream getTabularFile(String projectName, String filePath, FileRequestOptions fileRequestOptions) {
        return applyTabularSettings(getRawFile(projectName, filePath), fileRequestOptions);
    }

    @Override
    public InputStream getTabularFile(int file_id, FileRequestOptions fileRequestOptions) {
        return applyTabularSettings(getRawFile(file_id), fileRequestOptions);
    }

    @Override
    public SupportsViewModel supportsView(String project_name, int file_id, String view) {
        return new SupportsViewModel(fileRepository.findByFileId(file_id)
                .getSupported_views()
                .stream()
                .anyMatch(supportedView -> supportedView.getView().equals(view)));
    }

    @Override
    public SupportsViewModel supportsView(String project_name, String filePath, String view) {
        return new SupportsViewModel(getMetaFileFromPath(project_name, filePath)
                .getSupported_views()
                .stream()
                .anyMatch(supportedView -> supportedView.getView().equals(view)));
    }

    private MetaFile addTabularInformation(MetaFile metaFile) {
        metaFile.setHeaders(TabularParser.parseHeaders(metaFile, storageService.getFileStream(metaFile.getFileId())));
        RowCount rowCount = new RowCount(metaFile, TabularParser.getNumberOfRows(storageService.getFileStream(metaFile.getFileId())));
        metaFile.setRowCount(rowCount);
        rowCount.setFile(metaFile);
        rowCount.setFile_id(metaFile.getFileId());
        return fileRepository.save(metaFile);
    }

    @Override
    @Transactional
    public FileModel createOrUpdateFile(String project_name, String path, String action, FileRequestOptions options, byte[] bytes) {

        if (path.equals(ROOT_FILE_NAME)) throw new FileAlreadyExistsException(); // root will already exist and cannot be updated

        MetaFile parent = getParentFromPath(project_name, path);
        MetaFile metaFile = null;
        final String fileName = getFilenameFromPath(path);

        if (fileName.equals(ROOT_FILE_NAME)) throw new InvalidFileNameException();

        // Check file doesn't already exist in directory
        Optional<MetaFile> possibleExistingFile = parent.getChildren().stream().filter(child->child.getFile_name().equals(fileName)).findFirst();
        if (possibleExistingFile.isPresent()) {

            if (!options.isOverwrite()) throw new FileAlreadyExistsException();

            MetaFile existingFile = possibleExistingFile.get();

            if (existingFile.getType().equals(FileTypes.DIR)) throw new NotAFileException();
            metaFile = existingFile;

            // Update metadata to reflect imminent truncation
            if (bytes.length == 0 && options.isTruncate()) metaFile.setLength(options.getOffset());
            // Extend file if overwrite is greater than current size
            else if (options.getOffset()+bytes.length > metaFile.getLength()) metaFile.setLength(options.getOffset()+bytes.length);

            if (options.isFinal()) metaFile.setStatus(FileStatus.READY);
        }

        // Create file
        if (action.equals(Action.MAKE_DIRECTORY) && !options.isOverwrite()) {
            metaFile = MetaFile.createDirectory(
                    fileName,
                    parent);
        } else if (metaFile == null) {

            String type = getTypeFromFilename(path);
            List<SupportedView> supportedViews;

            // Add tabular view if necessary
            if (FileTypes.isTabular(type)) supportedViews = TABULAR_SUPPORTED_VIEWS;
            else supportedViews = FILE_SUPPORTED_VIEWS;

            metaFile = MetaFile.createFile(
                    fileName,
                    type,
                    options.isFinal() ? FileStatus.READY : FileStatus.UPLOADING,
                    bytes.length,
                    supportedViews,
                    parent);
        }

        metaFile = fileRepository.save(metaFile);

        // Add physical file to storage
        if (!action.equals(Action.MAKE_DIRECTORY)) {
            storageService.uploadFile(metaFile.getFileId(), options, bytes);
        }

        // Add tabular file info to DB
        if (options.isFinal() && FileTypes.isTabular(metaFile.getType())) {
            metaFile = addTabularInformation(metaFile);
        }

        return metaFileToFileModel(metaFile);
    }

    @Override
    public FileModel createOrUpdateFile(String project_name, int file_id, String action, FileRequestOptions options, byte[] bytes) {
        return createOrUpdateFile(project_name, fileRepository.getPathFromId(file_id), action, options, bytes);
    }

    private void deleteRecursively(MetaFile parent) {
        // Delete children
        if (parent.getType().equals(FileTypes.DIR)) {
            parent.getChildren().forEach(this::deleteRecursively);
        }

        // Delete parent
        fileRepository.delete(parent.getFileId());
        if (!parent.getType().equals(FileTypes.DIR)) storageService.deleteFile(parent.getFileId());
    }

    /**
     * Deletes a specific file
     *
     * @param projectName the name of the project to delete
     * @param filePath    the path of the file to delete
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
    public void deleteFile(String projectName, int file_id) {
        MetaFile metaFile = fileRepository.findByFileId(file_id);

        deleteRecursively(metaFile);
    }

    private MetaFile updateMetaFile(MetaFile metaFile, MetaDataModel metaDataModel) {
        if (metaFile.getMetadata().getVersion() != metaDataModel.getVersion()-1) throw new InvalidMetadataException();

        // Map <String,Object> to jsonObject, to string for database
        MetaData metaData = metaFile.getMetadata();
        JSONObject jsonObject = new JSONObject();
        metaDataModel.getNamespaces().forEach(jsonObject::put);
        metaData.setNamespaces(jsonObject.toString());
        metaData.setVersion(metaData.getVersion()+1);
        metaFile.setMetadata(metaData);

        return metaFile;
    }

    @Override
    @Transactional
    public FileModel updateFileMetaData(String project_name, String path, MetaDataModel metaDataModel) {
        MetaFile metaFile = getMetaFileFromPath(project_name, path);

        return metaFileToFileModel(updateMetaFile(metaFile, metaDataModel));
    }

    @Override
    public FileModel updateFileMetaData(String project_name, int file_id, MetaDataModel metaDataModel) {
        MetaFile metaFile = fileRepository.findByFileId(file_id);

        return metaFileToFileModel(updateMetaFile(metaFile, metaDataModel));
    }

    @Override
    @Transactional
    public FileModel moveFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel) {

        if (path.equals(ROOT_FILE_NAME)) throw new RootFileDeletionException();

        String new_path = getPathFromMoveFileRequest(moveFileRequestModel);

        // if new path is the same, do nothing
        MetaFile original = getMetaFileFromPath(project_name, path);
        if (new_path.equals(path)) {
            return metaFileToFileModel(original);
        }

        // change parent to new directory
        MetaFile destParent = getParentFromPath(project_name, new_path);
        if (!destParent.getType().equals(FileTypes.DIR)) throw new InvalidParentDirectoryException();
        else original.setParent(destParent);

        // check for cycles
        if (checkForCycles(original)) throw new InvalidParentDirectoryException();

        // delete file at that location if it exists
        try {
            MetaFile dest = getMetaFileFromPath(project_name, new_path);
            if (dest != null) deleteRecursively(dest);
        } catch (FileNotFoundException ignore) {
        }

        // check for name change
        String new_name = getFilenameFromPath(new_path);
        if (new_name.equals(ROOT_FILE_NAME)) throw new InvalidFileNameException();
        if (!new_name.equals(original.getFile_name())) original.setFile_name(new_name);


        // persist
        original = fileRepository.save(original);
        return metaFileToFileModel(original);
    }

    @Override
    public FileModel moveFile(String project_name, int file_id, MoveFileRequestModel moveFileRequestModel) {
        return moveFile(project_name, fileRepository.getPathFromId(file_id), moveFileRequestModel);
    }

    private boolean checkForCycles(MetaFile original) {
        int original_id = original.getFileId();
        if (original.getType().equals(FileTypes.DIR)) {
            // iterate up from original, detecting duplicate
            MetaFile parent = original.getParent();
            while (parent.getParent() != null) {
                if (parent.getFileId() == original_id) return true;
                else parent = parent.getParent();
            }
        }
        return false;
    }

    private static Set<Header> deepCopyHeaders(MetaFile dest, Set<Header> headers) {
        return headers.stream()
                .map(header-> new Header(new Header.HeaderPK(dest, header.getId().getIndex()), header.getName(), header.getType())
        ).collect(Collectors.toSet());
    }

    private MetaFile deepCopy(MetaFile original, MetaFile newParent, String newName) {
        final MetaFile metaFile;

        if (original.getType().equals(FileTypes.DIR)) {

            metaFile = fileRepository.save(
                    MetaFile.createDirectory(
                            newName,
                            newParent
                    ));
            // Deep copy children
            List<MetaFile> children = original.getChildren()
                    .stream()
                    .map(child -> deepCopy(child, metaFile, child.getFile_name()))
                    .collect(Collectors.toList());
            metaFile.setChildren(children);
        } else {
            metaFile = fileRepository.save(
                    MetaFile.createFile(
                            newName,
                            original.getType(),
                            original.getStatus(),
                            original.getLength(),
                            original.getSupported_views(),
                            newParent));

            // Add tabular file info to DB
            if (FileTypes.isTabular(metaFile.getType())) {
                Set<Header> headers = deepCopyHeaders(metaFile, original.getHeaders());
                metaFile.setHeaders(headers);
                metaFile.setRowCount(new RowCount(metaFile, original.getRowCount().getRows()));
            }

            // Copy physical file
            storageService.copyFile(original.getFileId(), metaFile.getFileId());
        }

        return metaFile;
    }

    @Override
    public FileModel copyFile(String project_name, String path, MoveFileRequestModel moveFileRequestModel) {
        String new_path = getPathFromMoveFileRequest(moveFileRequestModel);

        // Check new name is valid
        String new_name = getFilenameFromPath(new_path);
        if (new_name.equals(ROOT_FILE_NAME)) throw new InvalidFileNameException();

        // delete file at that location if it exists
        try {
            MetaFile dest = getMetaFileFromPath(project_name, new_path);
            if (dest != null) deleteRecursively(dest);
        } catch (FileNotFoundException ignore) {
        }

        MetaFile original = getMetaFileFromPath(project_name, path);

        // Check new parent exists
        MetaFile destParent = getParentFromPath(project_name, new_path);
        if (!destParent.getType().equals(FileTypes.DIR)) throw new InvalidParentDirectoryException();

        // deep copy meta file and its children
        MetaFile newFile = deepCopy(original, destParent, new_name);

        return metaFileToFileModel(newFile);
    }

    @Override
    public FileModel copyFile(String project_name, int file_id, MoveFileRequestModel moveFileRequestModel) {
        return copyFile(project_name,fileRepository.getPathFromId(file_id), moveFileRequestModel);
    }
}
