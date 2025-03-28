package cars24.DocumentCreator.service;

import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.repository.TemplateRepository;
import cars24.DocumentCreator.service.validator.GenericValidation;
import cars24.DocumentCreator.service.validator.JsonValidator;
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
            htmlToPDFConverter.process(processedHtml,(String) userRequest.get("format"));
            return "PDF Path returned";
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return "PDF Creation Failed: JSON Processing Error";
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return "PDF Creation Failed: " + e.getMessage();
        }
    }
}
