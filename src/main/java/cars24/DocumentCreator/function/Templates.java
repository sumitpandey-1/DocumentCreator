package cars24.DocumentCreator.function;

import cars24.DocumentCreator.model.Template;
import cars24.DocumentCreator.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class Templates {

    @Autowired
    private TemplateService templateService;

//    @Bean
//    public Supplier<Template> create(){
//        return() -> templateService.process();
//
//    }

//    @Bean
//    public Function<Integer,Template> get(){
//        System.out.println("Reached Here");
//        return(templateId) -> templateService.getTemplate(templateId);
 //   }
}
