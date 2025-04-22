package cars24.DocumentCreator.utility;

public interface DataUtils {
    public static boolean isFlatDataType(Object value) {
        return value instanceof String ||
                value instanceof Number ||
                value instanceof Boolean;
    }
}
