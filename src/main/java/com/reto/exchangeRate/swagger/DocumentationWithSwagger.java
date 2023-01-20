package com.reto.exchangeRate.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class DocumentationWithSwagger {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {

        return new ApiInfo("Exchange Rate Microservice", "This microservice is in charge of managing the exchange rate.",
                "1.0", "Terms of Service",
                new Contact("Lucero", "https://github.com/garychavez/mc_exchange_rate",
                        "chjalberto96@gmail.com"),
				"License of Spring boot Restfull", "Spring boot license URL", Collections.emptyList());
    }
}
