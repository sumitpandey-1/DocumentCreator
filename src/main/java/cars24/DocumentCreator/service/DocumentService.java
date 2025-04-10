package cars24.DocumentCreator.service;


import cars24.DocumentCreator.enums.DocFormat;
import cars24.DocumentCreator.exceptions.CustomException;
import cars24.DocumentCreator.filesystem.S3Service;
import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.repository.TemplateRepository;
import cars24.DocumentCreator.service.validator.UserValidation;
import cars24.DocumentCreator.service.validator.JsonValidator;
import cars24.DocumentCreator.utility.Constants;
import cars24.DocumentCreator.utility.Constants.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentService implements RequestProcessor {
    private final HTMLParserService htmlParserService;

    private HTMLToPDFConverter htmlToPDFConverter;

    private JsonValidator jsonValidator;

    private TemplateRepository templateRepository;

    private UserValidation userValidation;

    private S3Service s3Service;

    private final List<HTMLConverter> htmlConverters;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object process(String request) throws CustomException{
        try {
            Map userRequest = objectMapper.readValue(request,Map.class);
          //  genericValidation.validate(userRequest);
            Optional<Template> optionalTemplate = templateRepository.findById((String)userRequest.get(INPUT_FIELDS.TEMPLATE_ID));
            if (optionalTemplate.isEmpty()) {
                throw new CustomException(HttpStatus.NO_CONTENT,"Template not found for templateId : ".concat((String)userRequest.get(INPUT_FIELDS.TEMPLATE_ID)));
            }
            Template template = optionalTemplate.get();
            String data = objectMapper.writeValueAsString(userRequest.get("data"));
            jsonValidator.validateJsonDocument(objectMapper.writeValueAsString(template.getExpectedJsonFormat()),data);
            String processedHtml =
                    htmlParserService.replacePlaceholders(data,template);
            String defaultDocType = "pdf";
            if (userRequest.containsKey(INPUT_FIELDS.DOC_TYPE)){
                defaultDocType = (String) userRequest.get(INPUT_FIELDS.DOC_TYPE);
            }
            HTMLConverter converter = getConverter(defaultDocType);
            if (Objects.isNull(converter)){
                throw new CustomException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid DocType. Only support Image and PDF.");
            }
            DocFormat format = template.getDocFormat();
            if(userRequest.containsKey(INPUT_FIELDS.FORMAT)){
                format = DocFormat.fromValue((String) userRequest.get(INPUT_FIELDS.FORMAT));
            }
            byte[] byteFile = converter.process(processedHtml,format);

            return saveFileInS3(byteFile,template,defaultDocType);
        } catch (JsonProcessingException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Unable to Map request.");
        }
    }

    @Override
    public Boolean supportsDocumentRequestType(String requestType) {
        return REQUEST_TYPE.CREATE_DOCUMENT.equals(requestType);
    }

    private URI saveFileInS3(byte[] byteFile, Template template, String docType) {
        StringBuilder stringBuilder = new StringBuilder(template.getTemplateId())
                        .append("-")
                        .append(Instant.now().toEpochMilli())
                        .append(Constants.DOC_TYPE_TO_FILE_EXT.get(docType.toLowerCase()));

        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                stringBuilder.toString(),
                Constants.DOC_TYPE_TO_FORMAT.get(docType.toLowerCase()),
                byteFile
        );
        URI path = s3Service.uploadFile(multipartFile, createPrefixForDocPath() + "/" + template.getDocumentS3BasePath() + "/" + stringBuilder.toString(),"academy24-media");
        return path;
    }

    private HTMLConverter getConverter(String requestType) {
      for (HTMLConverter converter : htmlConverters){
          if (converter.canConvert(requestType))return converter;
      }
       return null;
    }

    private String createPrefixForDocPath(){
        return new StringBuilder(S3_FOLDER.S3_PARENT_FOLDER)
                .append("/")
                .append(S3_FOLDER.S3_TEMPLATE_DOCUMENT_FOLDER)
                .append("/")
                .toString();
    }
}
