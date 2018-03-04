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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
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

    // Creates the initial directory that stores the files that the database references.
    // Exists for testing the project locally.
    private static File setUpDirectory() throws IOException {
        File projects = new File(PROJECTS_DIRECTORY);
        if (projects.mkdir()) {
            System.out.println("Directory created.");
        } else {
            System.out.println("Create directory failed.");
        }
        return projects;
    }

    // Deletes the initial directory and all of its contents.
    // Exists for testing the project locally.
    private static void removeDirectory(File projects) {
        File[] allContents = projects.listFiles();
        if (allContents != null) {
            for (File file : allContents) { removeDirectory(file);
            }
        }
                if (projects.delete()) {
            System.out.println("Directory deleted.");
        } else {
            System.out.println("Remove directory failed.");
        }
    }
}
