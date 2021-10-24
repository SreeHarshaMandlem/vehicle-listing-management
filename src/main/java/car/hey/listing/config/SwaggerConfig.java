package car.hey.listing.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("car.hey.listing"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Vehicle Listing Management API",
        "Vehicle Listings Management API provides various APIs to can import the listings from the dealers through different providers, and make them available as searchable Listings.\n"
            + "Different providers send data in different formats, like CSV, JSON. ",
        "1.0",
        "Terms of service",
        new Contact("John Doe", "www.listing.hey.car", "contact@hey.car"),
        "License of API", "API license URL", Collections.emptyList());
  }
}
