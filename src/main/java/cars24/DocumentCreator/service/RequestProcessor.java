package cars24.DocumentCreator.service;

public interface RequestProcessor {

    Object process(String request) throws Exception;

    Boolean supportsDocumentRequestType(String requestType);
}
