package cars24.papermill;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentCreatorApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(DocumentCreatorApplication.class, args);
	}

}
