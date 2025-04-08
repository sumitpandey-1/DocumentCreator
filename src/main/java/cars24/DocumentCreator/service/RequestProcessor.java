package cars24.DocumentCreator.service;

public interface RequestProcessor {

    Object process(String request);

    Boolean supportsDocumentRequestType(String requestType);
}
