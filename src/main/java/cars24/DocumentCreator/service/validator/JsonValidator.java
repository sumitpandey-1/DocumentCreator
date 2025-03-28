package cars24.DocumentCreator.service.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/*
EXPECTED JSON EXAMPLE
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "recipient_name": { "type": "string" },
    "course_name": { "type": "string" },
    "grade": { "type": "string", "enum": ["A", "B", "C", "D", "E", "F"] },
    "program_name": { "type": "string" },
    "institution_name": { "type": "string" },
    "location": { "type": "string" },
    "date": {
      "type": "string",
      "pattern": "^(January|February|March|April|May|June|July|August|September|October|November|December) \\d{1,2}, \\d{4}$"
    },
    "duration": {
      "type": "string",
      "pattern": "^[0-9]+ (Month|Months|Year|Years)$"
    },
    "certificate_id": {
      "type": "string",
      "pattern": "^CERT-\\d{8}-\\d{3}$"
    },
    "issuer_name": { "type": "string" },
    "instructor_name": { "type": "string" },
    "dean_name": { "type": "string" },
    "director_name": { "type": "string" }
  },
  "required": [
    "recipient_name", "course_name", "grade", "program_name",
    "institution_name", "location", "date", "duration",
    "certificate_id", "issuer_name", "instructor_name",
    "dean_name", "director_name"
  ],
  "additionalProperties": false
}

*/

@Service
public class JsonValidator {

    public boolean validateJsonDocument(String definition, String payload){
        try {

            JSONObject rawSchema = new JSONObject(new JSONTokener(definition));
            Schema schema = SchemaLoader.load(rawSchema);
            JSONObject json = new JSONObject(new JSONTokener(payload));
            schema.validate(json);
            return true;

        } catch (Exception e) {
            return false;
        }
    }


}