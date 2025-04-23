package cars24.DocumentCreator.enums;

import java.util.Objects;

public enum DocType {
    PDF("pdf"),
    IMAGE("image");


    private final String value;

    DocType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DocFormat fromValue(String value) {
        if (Objects.isNull(value)) return null;
        for (DocFormat format : DocFormat.values()) {
            if (format.getValue().equalsIgnoreCase(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + value);
    }
}
