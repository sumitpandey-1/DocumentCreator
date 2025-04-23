package cars24.papermill.enums;

import lombok.Getter;

@Getter
public enum S3Operation {
    UPLOAD("Uploading file: {}", "UPLOAD"),
    DOWNLOAD("Downloading file: {}", "DOWNLOAD"),
    DELETE("Deleting file: {}", "DELETE"),
    COPY("Copying file from {} to {}", "COPY");

    private final String logMessage;
    private final String name;

    S3Operation(String logMessage, String name) {
        this.logMessage = logMessage;
        this.name = name;
    }
}
