package BE.util;

import BE.entities.project.MetaFile;
import BE.entities.project.tabular.Header;
import BE.exceptions.FileOperationException;
import BE.models.file.FileRequestOptions;
import com.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;

public class TabularParser {

    /**
     * Converts string array representation of row to single string with only requested columns.
     *
     * @param row  csv as string array
     * @param cols indices of columns to keep. Returns all columns if null.
     * @return csv string with only requested columns.
     */
    private static String parseRow(String[] row, List<Integer> cols) {

        // Return all if not specified
        if (cols == null) return String.join(",", row);

        // Filter rows otherwise
        StringBuilder rowBuilder = new StringBuilder();
        for (Integer col : cols) {
            rowBuilder.append(row[col]);
            rowBuilder.append(',');
        }
        rowBuilder.deleteCharAt(rowBuilder.length() - 1); // remove last comma
        return rowBuilder.toString();
    }

    /**
     * Parses a csv file using the given request options. For example, the number of rows to provide,
     * which row to start from, and which columns to include. To be converted to a stream, the rows must be stored as one
     * String, which limits the length of the response to Integer.MAX_VALUE.
     *
     * @param fileInputStream    input stream of the csv file
     * @param fileRequestOptions number of rows, start row, and column indices to be used
     * @return an input stream that only includes the requested information as a csv
     */
    public static InputStream applyTabularSettings(InputStream fileInputStream, FileRequestOptions fileRequestOptions) {
        List<String> rows = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream))) {

            // Get header row
            rows.add(parseRow(reader.readNext(), fileRequestOptions.getColsIndices()));

            String[] row;
            // Read all if 0
            if (fileRequestOptions.getRowCount() == 0) {
                while ((row = reader.readNext()) != null) {
                    rows.add(parseRow(row, fileRequestOptions.getColsIndices()));
                }
            } else {
                boolean reachedEndOfFile = false;
                // Skip to start row
                for (int i = 0; i < fileRequestOptions.getRowStart(); i++) {
                    if (reader.readNext() == null) {
                        reachedEndOfFile = true;
                        break;
                    }
                }
                // Read n rows if not at end
                if (!reachedEndOfFile) {
                    for (int i = 0; i < fileRequestOptions.getRowCount(); i++) {
                        if ((row = reader.readNext()) == null) break;
                        rows.add(parseRow(row, fileRequestOptions.getColsIndices()));
                    }
                }
            }

        } catch (IOException e) {
            throw new FileOperationException(e);
        }

        return new ByteArrayInputStream(rows
                .parallelStream()
                .collect(Collectors.joining("\n"))
                .getBytes(StandardCharsets.UTF_8)
        );
    }

    public static List<Header> parseHeaders(MetaFile metaFile, InputStream inputStream) {

        List<Header> headers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] headerLine = reader.readNext();
            String[] firstValueLine = reader.readNext();
            for (int i = 0; i < headerLine.length; i++) {
                String type;
                // Determine type
                if (isNumber(firstValueLine[i])) {
                    type = Header.NUMBER;
                } else {
                    type = Header.STRING;
                }
                headers.add(new Header(new Header.HeaderPK(metaFile.getFileId(), i), headerLine[0], type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return headers;
    }

    /**
     * Efficient solution taken from https://stackoverflow.com/a/14411695/4364101
     *
     * @param inputStream
     * @return
     */
    public static int getNumberOfRows( InputStream inputStream) {

        int count = 0;

        try {
            byte[] c = new byte[1024];
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = inputStream.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
        } catch (IOException e) {
            throw new FileOperationException(e);
        }

        return count;
    }
}
