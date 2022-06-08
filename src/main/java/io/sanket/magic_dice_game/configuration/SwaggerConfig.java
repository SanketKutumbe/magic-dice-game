package io.sanket.magic_dice_game.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex("/api.*"))
                .build()
                .groupName("Magic Dice Game")
                .apiInfo(gameInfoCustom());
    }

    private ApiInfo gameInfoCustom()
    {
        return new ApiInfoBuilder().title("Magic Dice Game")
                .description("Family friendly game, where top scorer wins the game")
                .license("io.sanket").build();
    }
}
