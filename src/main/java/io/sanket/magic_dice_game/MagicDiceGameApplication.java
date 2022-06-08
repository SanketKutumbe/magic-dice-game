package io.sanket.magic_dice_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MagicDiceGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicDiceGameApplication.class, args);
    }

    @Bean
    public Docket gameApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .paths(PathSelectors.ant("/api/*"))
                    .apis(RequestHandlerSelectors.basePackage("io.sanket.magic_dice_game"))
                    .build();
    }

}
