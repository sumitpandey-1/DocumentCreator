package cars24.papermill.enums;

import java.util.Objects;

public enum DocFormat {
    A2("A2"),
    A3("A3"),
    A4("A4");

    private final String value;

    DocFormat(String value) {
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
