package cars24.DocumentCreator.service;

import cars24.DocumentCreator.config.ISpringFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cars24.DocumentCreator.utility.Constants.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RequestResolverService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ISpringFactory iSpringFactory;

    private List<RequestProcessor> processors;

    public Object process(String request) throws Exception {
        Map userRequest = objectMapper.readValue(request,Map.class);
        String requestType = (String) userRequest.get(INPUT_FIELDS.REQUEST_TYPE);
        RequestProcessor processor = getProcessor(requestType);
        if (Objects.isNull(processor)){
            throw new Exception("Invalid Request Type!!");
        }
        return processor.process(request);
    }

    private RequestProcessor getProcessor(String requestType) {
        for(RequestProcessor processor : processors){
            if (processor.supportsDocumentRequestType(requestType)) return processor;
        }
        return null;
    }
}
