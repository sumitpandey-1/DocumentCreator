package cars24.DocumentCreator.function;

import cars24.DocumentCreator.service.RequestResolverService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class MyHandler implements RequestHandler<String,Object> {

    private RequestResolverService requestResolverService;

    @Override
    public Object handleRequest(String request, Context context) {
        try {
            return requestResolverService.process(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
