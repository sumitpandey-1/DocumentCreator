package cars24.DocumentCreator.service;

import cars24.DocumentCreator.dto.Table;
import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.repository.TemplateRepository;
import cars24.DocumentCreator.utility.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TemplateService implements GenericService{

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private HTMLParserService htmlParserService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object process(String request) {
        try {
            Map userRequest = objectMapper.readValue(request,Map.class);
            String requestType = (String) userRequest.get(Constants.INPUT_FIELDS.REQUEST_TYPE);
            String templateId = (String) userRequest.get(Constants.INPUT_FIELDS.TEMPLATE_ID);

            if (requestType.equals(Constants.REQUEST_TYPE.CREATE_TEMPLATE)
                    || requestType.equals(Constants.REQUEST_TYPE.UPDATE_TEMPLATE)){
                return createTemplate(userRequest);
            }
            if (requestType.equals(Constants.REQUEST_TYPE.GET_TEMPLATE_PAYLOAD)
                    && Objects.nonNull(templateId) && templateId.isEmpty()){
                return getTemplatePayload(templateId);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "- Invalid Request! Check Request Input. -";
    }

    public Template createTemplate(Map request) throws JsonProcessingException {

        Template template = new Template();
        if(request.containsKey(Constants.INPUT_FIELDS.TEMPLATE_ID)){
            template = getTemplate((String) request.get(Constants.INPUT_FIELDS.TEMPLATE_ID));
            if (Objects.isNull(template)) template =  new Template();
        }
        if (request.containsKey(Constants.TEMPLATE_FIELDS.TEMPLATE_NAME)) {
            template.setTemplateId((String) request.get(Constants.TEMPLATE_FIELDS.TEMPLATE_NAME));
        }
        if (request.containsKey(Constants.TEMPLATE_FIELDS.JSON_VALIDATOR)) {
            template.setExpectedJsonFormat(objectMapper.readValue((String) request.get(Constants.TEMPLATE_FIELDS.JSON_VALIDATOR), Map.class));
        }
        if (request.containsKey(Constants.TEMPLATE_FIELDS.HTML_TEMPLATE)) {
            List<Table> tableList = htmlParserService.getTablesFromTemplate((String) request.get(Constants.TEMPLATE_FIELDS.HTML_TEMPLATE));
            template.setTables(tableList);
            template.setHtmlTemplate((String) request.get(Constants.TEMPLATE_FIELDS.HTML_TEMPLATE));
        }
        template.setUpdatedOn(LocalDateTime.now());
        if (Objects.isNull(template.getCreatedOn())){
            template.setCreatedOn(LocalDateTime.now());
        }
        return templateRepository.save(template);
    }

    public Object getTemplatePayload(String templateId) {
        Optional<Template> response = templateRepository.findById(templateId);
        if (response.isEmpty()) return "Template ID is incorrect";
        return response.get().getExpectedJsonFormat();
    }
    public static String generateTemplateID() {
        return "Template_" + Instant.now().toEpochMilli();
    }

    public Template getTemplate(String templateId){
        Optional<Template> response = templateRepository.findById(templateId);
        if(response.isPresent())return response.get();
        return null;
    }
}
