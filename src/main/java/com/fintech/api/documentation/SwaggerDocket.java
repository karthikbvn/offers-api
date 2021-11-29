package com.fintech.api.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Controller
public class SwaggerDocket {

    final ApiInfo apiInfo = new ApiInfo("Api Documentation", null, "1.0", null, null, null, null, new ArrayList<>());

    @Bean Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.fintech.api.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    @RequestMapping("/api-docs")
    public String greeting() {
        return "redirect:/swagger-ui/";
    }
}
