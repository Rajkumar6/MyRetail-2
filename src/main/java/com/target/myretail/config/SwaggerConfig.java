package com.target.myretail.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	 public static final Contact DEFAULT_CONTACT = new Contact(
		      "Saji", "https://github.com/sajivijaysadas/MyRetail", "sajivijaysadas@yahoo.com");
		  
		  public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
				  "MyRetail Product APIs", "Product Details API for MyRetail Application", "1.0",
		      "", DEFAULT_CONTACT, 
		      "", "");

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2) 
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.target.myretail.controller"))
                .paths(regex("/myretail.*"))
                .build()
                .apiInfo(DEFAULT_API_INFO);
    }  


}
