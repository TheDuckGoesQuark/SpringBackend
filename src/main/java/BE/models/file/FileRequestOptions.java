package BE.models.file;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileRequestOptions {
    // Upload/Download
    private static final String OVERWRITE = "overwrite";
    private static final String OFFSET = "offset";
    private static final String TRUNCATE = "truncate";
    private static final String FINAL = "final";
    private static final String LENGTH = "length";
    // Tabular
    private static final String ROWSTART = "rowstart";
    private static final String ROWCOUNT = "rowcount";
    private static final String COLS = "cols";

    private boolean overwrite;
    private int offset;
    private boolean truncate;
    private boolean isFinal;
    private int length;

    private int rowStart;
    private int rowCount;
    private List<Integer> colsIndices;

    private static List<Integer> parseIntegerList(String list) {
        return Arrays.stream(list.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static FileRequestOptions readOptions(Map<String, String> mapOptions) {
        FileRequestOptions options = new FileRequestOptions();

        //--Upload/Download--
        //Final
        options.setFinal(mapOptions.containsKey(FINAL));
        //Offset
        if (mapOptions.containsKey(OFFSET)) options.setOffset(Integer.parseInt(mapOptions.get(OFFSET)));
        else options.setOffset(0);
        //Overwrite
        options.setOverwrite(mapOptions.containsKey(OVERWRITE));
        //Truncate
        options.setTruncate(mapOptions.containsKey(TRUNCATE));

        //--Tabular--
        // Rowstart
        if (mapOptions.containsKey(ROWSTART)) options.setRowStart(Integer.parseInt(mapOptions.get(ROWSTART)));
        else options.setRowStart(0);
        // Rowcount
        if (mapOptions.containsKey(ROWCOUNT)) options.setRowCount(Integer.parseInt(mapOptions.get(ROWCOUNT)));
        else options.setRowCount(0);
        // Col indices
        if (mapOptions.containsKey(COLS)) options.setColsIndices(parseIntegerList(mapOptions.get(COLS)));

        return options;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isTruncate() {
        return truncate;
    }

    public void setTruncate(boolean truncate) {
        this.truncate = truncate;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRowStart() {
        return rowStart;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<Integer> getColsIndices() {
        return colsIndices;
    }

    public void setColsIndices(List<Integer> colsIndices) {
        this.colsIndices = colsIndices;
    }
}
