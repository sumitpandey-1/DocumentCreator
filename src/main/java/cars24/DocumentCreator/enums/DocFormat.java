package cars24.DocumentCreator.enums;

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
}
