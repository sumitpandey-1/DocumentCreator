package cars24.DocumentCreator.service;

import cars24.DocumentCreator.config.ISpringFactory;
import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.repository.TemplateRepository;
import cars24.DocumentCreator.service.validator.GenericValidation;
import cars24.DocumentCreator.service.validator.JsonValidator;
import cars24.DocumentCreator.utility.Constants;
import cars24.DocumentCreator.utility.Constants.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class DocumentService implements GenericService{
    @Autowired
    HTMLParserService htmlParserService;

    @Autowired
    HTMLToPDFConverter htmlToPDFConverter;

    @Autowired
    JsonValidator jsonValidator;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    GenericValidation genericValidation;

    @Autowired
    ISpringFactory iSpringFactory;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String  process(String request){
        try {
            Map userRequest = objectMapper.readValue(request,Map.class);
            genericValidation.validate(userRequest);
            Optional<Template> optionalTemplate = templateRepository.findById((String)userRequest.get("templateId"));
            if (optionalTemplate.isEmpty()) return "Template Not Found";
            Template template = optionalTemplate.get();
            String data = objectMapper.writeValueAsString(userRequest.get("data"));
            boolean isValidJson =
                    jsonValidator.validateJsonDocument(objectMapper.writeValueAsString(template.getExpectedJsonFormat()),data);
            if (!isValidJson){
                return "JSON format doesnot match with the expected JSON";
            }
            String processedHtml =
                    htmlParserService.replacePlaceholders(data,template);
            String defaultDocType = "pdf";
            if (userRequest.containsKey(INPUT_FIELDS.DOC_TYPE)){
                defaultDocType = (String) userRequest.get(INPUT_FIELDS.DOC_TYPE);
            }
            HTMLConverter converter = getConverter(defaultDocType);
            converter.process(processedHtml,(String) userRequest.get("format"));
            return "Doc Path returned";
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return "Document Creation Failed: JSON Processing Error";
        } catch (Exception e) {
            return "Document Creation Failed: " + e.getMessage();
        }
    }
    private HTMLConverter getConverter(String requestType) {
       if (DOCUMENT_TYPE.IMAGE.contains(requestType.toLowerCase())){
           return iSpringFactory.getBean(HTMLToImageConverter.class);
       }
       if (DOCUMENT_TYPE.PDF.contains(requestType.toLowerCase())){
           return iSpringFactory.getBean(HTMLToPDFConverter.class);
       }
       return null;
    }
}
