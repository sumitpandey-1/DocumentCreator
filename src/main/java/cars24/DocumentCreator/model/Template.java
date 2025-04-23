package cars24.DocumentCreator.model;

import cars24.DocumentCreator.dto.Table;
import cars24.DocumentCreator.enums.DocFormat;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "template_table")
public class Template {
    @Id
    private String templateId;
    private String templateName;
    private String templateS3Path;
    private String documentS3BasePath;
    private Map<String,Object> expectedJsonFormat;
    private String htmlTemplate;
    private String tenantId;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private DocFormat docFormat;
    private List<Table> tables;

}
