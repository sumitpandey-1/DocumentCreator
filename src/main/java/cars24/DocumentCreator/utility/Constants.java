package cars24.DocumentCreator.utility;

import java.util.*;

public interface Constants {

    interface INPUT_FIELDS{
        String REQUEST_TYPE = "requestType";
        String TEMPLATE_ID = "templateId";
        String DOC_TYPE = "docType";
        String FORMAT = "format";
        String USER = "user";
    }

    interface REQUEST_TYPE{
        String CREATE_DOCUMENT = "CreateDocument";
        String CREATE_TEMPLATE = "CreateTemplate";
        String UPDATE_TEMPLATE = "UpdateTemplate";
        String GET_TEMPLATE_PAYLOAD = "GetTemplatePayload";
    }
    interface TEMPLATE_FIELDS{
        String HTML_TEMPLATE = "htmlTemplate";
        String JSON_VALIDATOR = "jsonValidator";
        String TEMPLATE_NAME = "templateName";
        String DOC_FORMAT = "docFormat";
        String DOCUMENT_BASE_S3_PATH = "documentS3BasePath";
    }
    interface DOCUMENT_TYPE{
        List<String> IMAGE = List.of("image","png");
        List<String> PDF = List.of("pdf");
    }

    public final Map<String,String> DOC_TYPE_TO_FORMAT = Map.of(
            "pdf","application/pdf",
            "image","image/png"
    );

    public final Map<String,String> DOC_TYPE_TO_FILE_EXT = Map.of(
            "pdf",".pdf",
            "image",".png"
    );
    interface S3_FOLDER{
        String DOCUMENT = "document";
    }

}
