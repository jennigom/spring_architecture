package com.architecture.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger 설정 파일
 * @author JENNI
 * @version 1.0
 * @since 2022.05.10
 */

@Configuration
public class SwaggerConfig {
    // Swagger 설정의 핵심
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                // docket group명
                .groupName("spring architecture")
                // ApiSelectorBuilder
                .select()
                // api 스펙
                .apis(RequestHandlerSelectors.any())
                // api 중에 path 조건에 맞는 api를 필터링하여 문서화
                .paths(PathSelectors.any())
                .build()
                // api 정보
                .apiInfo(apiInfo());
    }

    // api 정보
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SPRING ARCHITECTURE")
                .description("")
                .version("1.0")
                .build();
    }
}
