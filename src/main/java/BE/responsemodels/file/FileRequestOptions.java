package BE.responsemodels.file;

public class FileRequestOptions {
    public static final String OVERWRITE = "overwrite";
    public static final String OFFSET = "offset";
    public static final String TRUNCATE = "truncate";
    public static final String FINAL = "final";

    private boolean overwrite;
    private int offset;
    private boolean truncate;
    private boolean isFinal;

    public FileRequestOptions() {
    }

    public FileRequestOptions(boolean overwrite, int offset, boolean truncate, boolean isFinal) {
        this.overwrite = overwrite;
        this.offset = offset;
        this.truncate = truncate;
        this.isFinal = isFinal;
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
}
