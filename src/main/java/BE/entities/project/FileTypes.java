package BE.entities.project;

public final class FileTypes {
    public static final String UNKNOWN = "generic";
    public static final String DIR = "directory";
    public static final String TABULAR = "tabular";

    public static boolean isTabular(String fileType) {
        return fileType.equals("csv") || fileType.equals(TABULAR);
    }
}
