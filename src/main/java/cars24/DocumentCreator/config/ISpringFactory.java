package cars24.DocumentCreator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ISpringFactory {

    private final ApplicationContext context;

    public ISpringFactory(ApplicationContext context) {
        this.context = context;
    }

    public <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
