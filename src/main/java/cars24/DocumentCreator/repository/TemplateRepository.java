package cars24.DocumentCreator.repository;

import cars24.DocumentCreator.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {


    List<Template> findByTenantId(String tenantId);

    Template findByTemplateName(String templateName);

}
