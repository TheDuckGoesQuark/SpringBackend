package BE.util;

import BE.entities.project.FileTypes;
import BE.entities.project.MetaFile;
import BE.entities.project.SupportedView;
import BE.entities.project.tabular.Header;
import BE.exceptions.UnsupportedFileViewException;
import BE.models.file.FileMetaDataModel;
import BE.models.file.FileModel;
import BE.models.file.supportedviewinfoobjects.RawViewInfoModel;
import BE.models.file.supportedviewinfoobjects.BaseSupportedViewInfo;
import BE.models.file.supportedviewinfoobjects.TabularViewInfoModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class MetaFileUtil {
    /**
     * Converts a specific meta file to a file model
     *
     * @param metaFile the meta file to be converted
     * @return file model
     */
    public static FileModel metaFileToFileModel(MetaFile metaFile) {

        Map<String, BaseSupportedViewInfo> supportedViewList = new HashMap<>();

        metaFile.getSupported_views().forEach(supportedView -> {
            switch (supportedView.getView()) {
                case SupportedView.RAW_VIEW:
                    RawViewInfoModel rawViewInfoModel = new RawViewInfoModel(metaFile.getLength());
                    supportedViewList.put(rawViewInfoModel.getName(), rawViewInfoModel);
                    break;
                case SupportedView.TABULAR_VIEW:
                    TabularViewInfoModel tabularViewInfoModel = new TabularViewInfoModel();
                    Set<Header> columns = metaFile.getHeaders();
                    columns.forEach(column -> tabularViewInfoModel.addColumn(column.getName(), column.getType()));
                    tabularViewInfoModel.setRows(metaFile.getRowCount().getRows());
                    supportedViewList.put(tabularViewInfoModel.getName(), tabularViewInfoModel);
                    break;
            }
        });

        return new FileModel(
                metaFile.getPath(),
                metaFile.getFile_name(),
                metaFile.getFileId(),
                supportedViewList,
                new FileMetaDataModel(metaFile.getLast_modified(), metaFile.getLength()),
                metaFile.getType(),
                metaFile.getStatus()
        );
    }

    public static String getFilenameFromPath(String path) {
        File file = new File(path);
        return file.getName();
    }

    public static String getTypeFromFilename(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        } else {
            return FileTypes.UNKNOWN;
        }

        if (FileTypes.isTabular(extension)) return FileTypes.TABULAR;
        else return extension;
    }

    public static FileModel getFileModelWithChildren(MetaFile root) {
        if (!root.getType().equals(FileTypes.DIR)) throw new UnsupportedFileViewException();
        FileModel fileModel = metaFileToFileModel(root);
        fileModel.setChildren(
                root.getChildren().stream()
                        .map(MetaFileUtil::metaFileToFileModel)
                        .collect(Collectors.toList()));
        return fileModel;
    }
}
