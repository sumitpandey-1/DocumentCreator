package cars24.DocumentCreator;

import cars24.DocumentCreator.service.HTMLParserService;
import cars24.DocumentCreator.service.HTMLToPDFConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentCreatorApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(DocumentCreatorApplication.class, args);
	}

}
