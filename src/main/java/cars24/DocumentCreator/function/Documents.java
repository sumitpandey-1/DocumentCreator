package cars24.DocumentCreator.function;

import cars24.DocumentCreator.exceptions.CustomException;
import cars24.DocumentCreator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

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
                Object response = requestResolverService.process(jsonString);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(response);
            } catch (CustomException e) {
                return ResponseEntity
                        .status(e.getStatus())
                        .body(e.getMessage());
            }
        };

    }
}
