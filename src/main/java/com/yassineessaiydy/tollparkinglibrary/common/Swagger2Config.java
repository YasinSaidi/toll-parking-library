package com.yassineessaiydy.tollparkinglibrary.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private ApiInfo buildApiInfo(){
        Contact contact = new Contact("Yassine ES-SAIYDY", "http://www.yassine-essaiydy.com",
                "yassine.essaiydy@outlook.com");

        return new ApiInfo("Toll parking library API", "It manages a toll parking and cars of all" +
                " types that it receives and leaves.", "1.0.0", "",
                contact, "", "", Collections.emptyList());
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.yassineessaiydy")).build()
                .apiInfo(buildApiInfo()).useDefaultResponseMessages(false);
    }

    @Bean
    public UiConfiguration tryItOutDisable() {
        final String[] methodsWithButtonTry = {};

        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithButtonTry).build();
    }

}