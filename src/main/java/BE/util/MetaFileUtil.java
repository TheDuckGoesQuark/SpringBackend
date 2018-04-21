package BE.util;

import BE.entities.project.FileTypes;
import BE.entities.project.MetaFile;
import BE.entities.project.SupportedView;
import BE.entities.project.tabular.Header;
import BE.exceptions.GenericInternalServerException;
import BE.exceptions.UnsupportedFileViewException;
import BE.models.MetaDataModel;
import BE.models.file.FileModel;
import BE.models.file.supportedviewinfoobjects.RawViewInfoModel;
import BE.models.file.supportedviewinfoobjects.BaseSupportedViewInfo;
import BE.models.file.supportedviewinfoobjects.TabularViewInfoModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class MetaFileUtil {

    private static Map<String, Object> jsonStringToMap(String json) {

        Map<String, Object> map = new HashMap<>();

        try {
            map = new ObjectMapper().readValue(json, new TypeReference<HashMap<String,Object>>() {});
        } catch (IOException e) {
            throw new GenericInternalServerException(e);
        }

        return map;

    }

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

        Map<String,Object> namespaces = jsonStringToMap(metaFile.getMetadata().getNamespaces());


        return new FileModel(
                metaFile.getPath(),
                metaFile.getFile_name(),
                metaFile.getFileId(),
                supportedViewList,
                new MetaDataModel(metaFile.getMetadata().getVersion(), namespaces), // TODO String to map<String, Object>
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
