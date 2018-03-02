package BE;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;


@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
//        File projects = setUpDirectory();
        SpringApplication.run(Application.class, args);
//        removeDirectory(projects);
    }

    // Produces swagger documentation at http://localhost:8080/swagger-ui.html
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enableUrlTemplating(true)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JH BE")
                .version("0.1")
                .build();
    }

//    private static File setUpDirectory() {
//        new File("/projects").mkdirs();
//        File projects = new File("/projects");
//        return projects;
//    }
//
//    private static void removeDirectory(File projects) {
//        projects.delete();
//    }
}
