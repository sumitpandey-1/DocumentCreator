package cars24.papermill.service;

public interface RequestProcessor {

    Object process(String request) throws Exception;

    Boolean supportsDocumentRequestType(String requestType);
}
