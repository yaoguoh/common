package com.github.yaoguoh.common.doc.config;

import com.github.yaoguoh.common.doc.properties.DocProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

/**
 * The type Doc configuration.
 *
 * @author WYG
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(DocProperties.class)
public class DocConfiguration {

    private final DocProperties docProperties;

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group(docProperties.getGroup())
                .pathsToMatch("/**")
                .build();
    }

    /**
     * Open api open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(docProperties.getTitle())
                        .description(docProperties.getDescription())
                        .version(docProperties.getVersion())
                        .license(new License().name(docProperties.getLicense()).url(docProperties.getLicenseUrl())))
                // 配置接口访问地址
                .servers(Collections.singletonList(new Server().url(docProperties.getServerUrl())))
                // 配置认证
                .security(Collections.singletonList(new SecurityRequirement().addList("Bearer Authorization")))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authorization", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                        .addSecuritySchemes("Basic Authorization", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}
