package cars24.papermill.function;

import cars24.papermill.exceptions.CustomException;
import cars24.papermill.service.RequestResolverService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@AllArgsConstructor
public class MyHandler implements RequestHandler<String,Object> {

    private RequestResolverService requestResolverService;

    @Override
    public ResponseEntity<Object> handleRequest(String request, Context context) {
        try {
            Object response = requestResolverService.process(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (CustomException e) {
            return ResponseEntity
                    .status(e.getStatus())
                    .body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
