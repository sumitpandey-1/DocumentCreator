package cars24.DocumentCreator.function;

import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class Documents {

    @Autowired
    private RequestResolverService requestResolverService;

/*
* {
    "template_id" : 11,
    "format" : "A3",
    "user" : admin,
    "data": { (Vary from template to template)
        "recipient_name": "Alice Brown",
        "course_name": "Machine Learning",
        "grade": "A",
        "program_name": "MSc in AI",
        "institution_name": "AI University",
        "location": "California, USA",
        "date": "April 10, 2025",
        "duration": "4 Months",
        "certificate_id": "CERT-20250410-002",
        "issuer_name": "Dr. John Smith",
        "instructor_name": "Prof. Emily Rose",
        "dean_name": "Dr. Kevin White",
        "director_name": "Dr. Laura Green"
    }
}
*/

    @Bean
    public Function<String, Object> requestHa() {
        return (jsonString) -> {
            try {
                return requestResolverService.process(jsonString);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

    }
}
