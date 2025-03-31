package cars24.DocumentCreator.service;

import cars24.DocumentCreator.config.ISpringFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cars24.DocumentCreator.utility.Constants.*;

import java.util.Map;
import java.util.Objects;

@Service
public class RequestResolverService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ISpringFactory iSpringFactory;

    public Object process(String request) throws Exception {
        Map userRequest = objectMapper.readValue(request,Map.class);
        String requestType = (String) userRequest.get(INPUT_FIELDS.REQUEST_TYPE);
        GenericService processor = getProcessor(requestType);
        if (Objects.isNull(processor)){
            throw new Exception("Invalid Request Type!!");
        }
        return processor.process(request);
    }

    private GenericService getProcessor(String requestType) {
        switch (requestType){
            case REQUEST_TYPE.CREATE_DOCUMENT:
                return iSpringFactory.getBean(DocumentService.class);
            case REQUEST_TYPE.GET_TEMPLATE_PAYLOAD:
            case REQUEST_TYPE.CREATE_TEMPLATE:
                return iSpringFactory.getBean(TemplateService.class);
            default:
                return null;
        }
    }
}
