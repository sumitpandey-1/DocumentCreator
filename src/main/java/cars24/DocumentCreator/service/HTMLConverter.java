package cars24.DocumentCreator.service;

import cars24.DocumentCreator.enums.DocFormat;

public interface HTMLConverter {
    byte[] process(String htmlContent , DocFormat format );
    boolean canConvert(String requestType);
}
