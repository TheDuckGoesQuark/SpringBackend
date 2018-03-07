package BE.models.file;

import java.util.Map;

public class FileRequestOptions {
    public static final String OVERWRITE = "overwrite";
    public static final String OFFSET = "offset";
    public static final String TRUNCATE = "truncate";
    public static final String FINAL = "final";
    public static final String LENGTH = "length";

    private boolean overwrite;
    private int offset;
    private boolean truncate;
    private boolean isFinal;
    private int length;

    public FileRequestOptions() {
    }

    public static FileRequestOptions readOptions(Map<String, String> mapOptions) {
        FileRequestOptions options = new FileRequestOptions();
        //Final
        options.setFinal(mapOptions.containsKey(FileRequestOptions.FINAL));
        //Offset
        if (mapOptions.containsKey(FileRequestOptions.OFFSET))
            options.setOffset(Integer.parseInt(mapOptions.get(FileRequestOptions.OFFSET)));
        else options.setOffset(0);
        //Overwrite
        options.setOverwrite(mapOptions.containsKey(FileRequestOptions.OVERWRITE));
        //Truncate
        options.setTruncate(mapOptions.containsKey(FileRequestOptions.TRUNCATE));
        //
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
}
