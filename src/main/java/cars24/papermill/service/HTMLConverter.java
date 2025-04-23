package cars24.papermill.service;

import cars24.papermill.enums.DocFormat;

public interface HTMLConverter {
    byte[] process(String htmlContent , DocFormat format );
    boolean canConvert(String requestType);
}
