package pl.grzywacz.jakub;

import org.aspectj.apache.bcel.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;
import pl.grzywacz.jakub.model.Abonent;
import pl.grzywacz.jakub.model.Address;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Component
    public class ExposeEntityIdRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Abonent.class);
        }
    }
}
