package BE.controllers;

import java.nio.charset.Charset;
import java.util.Map;

public final class MediaType extends org.springframework.http.MediaType {

    public static final String TEXT_CSV_VALUE = "text/csv";
    public static final MediaType TEXT_CSV = new MediaType(TEXT_CSV_VALUE);

    public MediaType(String type) {
        super(type);
    }

    public MediaType(String type, String subtype) {
        super(type, subtype);
    }

    public MediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public MediaType(String type, String subtype, double qualityValue) {
        super(type, subtype, qualityValue);
    }

    public MediaType(org.springframework.http.MediaType other, Charset charset) {
        super(other, charset);
    }

    public MediaType(org.springframework.http.MediaType other, Map<String, String> parameters) {
        super(other, parameters);
    }

    public MediaType(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }
}
