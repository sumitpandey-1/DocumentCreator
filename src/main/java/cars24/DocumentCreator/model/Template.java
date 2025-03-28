package cars24.DocumentCreator.model;

import cars24.DocumentCreator.dto.Table;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
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
    private Date createdOn;
    private Date updatedOn;
    private String docFormat;
    private List<Table> tables;

}
