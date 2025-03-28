package cars24.DocumentCreator.service;

import cars24.DocumentCreator.dto.Table;
import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.repository.TemplateRepository;
import cars24.DocumentCreator.utility.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

            if (requestType.equals(Constants.REQUEST_TYPE.CREATE_TEMPLATE)){
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

        template.setTemplateId(generateTemplateID());
        template.setTemplateName((String) request.get("templateName"));
//        String schemaString = "{\n" +
//                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
//                "  \"type\": \"object\",\n" +
//                "  \"properties\": {\n" +
//                "    \"SenderName\": { \"type\": \"string\" },\n" +
//                "    \"SenderAddress\": { \"type\": \"string\" },\n" +
//                "    \"SenderCity\": { \"type\": \"string\" },\n" +
//                "    \"CompanyName\": { \"type\": \"string\" },\n" +
//                "    \"ReceiverName\": { \"type\": \"string\" },\n" +
//                "    \"ReceiverEmail\": { \"type\": \"string\", \"format\": \"email\" },\n" +
//                "    \"PaymentMethod\": { \"type\": \"string\" },\n" +
//                "    \"Details\": { \"type\": \"string\" },\n" +
//                "    \"InvoiceItems\": {\n" +
//                "      \"type\": \"array\",\n" +
//                "      \"items\": {\n" +
//                "        \"type\": \"object\",\n" +
//                "        \"properties\": {\n" +
//                "          \"itemName\": { \"type\": \"string\" },\n" +
//                "          \"itemQuantity\": { \"type\": \"integer\", \"minimum\": 1 },\n" +
//                "          \"itemPrice\": { \"type\": \"number\", \"minimum\": 0 }\n" +
//                "        },\n" +
//                "        \"required\": [\"itemName\", \"itemQuantity\", \"itemPrice\"]\n" +
//                "      }\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"required\": [\n" +
//                "    \"SenderName\", \"SenderAddress\", \"SenderCity\",\n" +
//                "    \"CompanyName\", \"ReceiverName\", \"ReceiverEmail\",\n" +
//                "    \"PaymentMethod\", \"Details\", \"InvoiceItems\"\n" +
//                "  ]\n" +
//                "}";
        template.setExpectedJsonFormat(objectMapper.readValue((String)request.get(Constants.TEMPLATE_FIELDS.JSON_VALIDATOR),Map.class));
        List<Table> tableList = htmlParserService.getTablesFromTemplate((String)request.get(Constants.TEMPLATE_FIELDS.HTML_TEMPLATE));
        template.setTables(tableList);
        template.setHtmlTemplate((String) request.get(Constants.TEMPLATE_FIELDS.HTML_TEMPLATE));
        return templateRepository.save(template);
    }

    public Template getTemplate(String id){
        Optional<Template> response = templateRepository.findById(id);
        return response.get();
    }

    public Object getTemplatePayload(String templateId) {
        Optional<Template> response = templateRepository.findById(templateId);
        if (response.isEmpty()) return "Template ID is incorrect";
        return response.get().getExpectedJsonFormat();
    }
    public static String generateTemplateID() {
        return "Template_" + Instant.now().toEpochMilli();
    }
}
