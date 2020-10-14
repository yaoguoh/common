package com.github.yaoguoh.common.swagger.config;

import com.github.yaoguoh.common.swagger.properties.SwaggerProperties;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Swagger auto configuration.
 * on @{@link EnableOpenApi} @EnableOpenApi  Indicates that Swagger support should be enabled. （启用swagger）
 *
 * @author WYG
 */
@Slf4j
@Profile({"dev", "test"})
@EnableOpenApi
@AllArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

    private final SwaggerProperties swaggerProperties;

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .groupName(swaggerProperties.getGroupName())
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(swaggerProperties.getEnable())
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 接口调试地址
                .host(swaggerProperties.getHost())
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                //配置自定义参数
                .globalRequestParameters(this.globalOperationParameters())
                //配置鉴权信息
                .securitySchemes(this.securityScheme())
                .securityContexts(this.securityContext());
    }


    /**
     * 文档的描述
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(this.contact())
                .version(swaggerProperties.getVersion())
                .build();
    }

    /**
     * 认证方式配置
     */
    private List<SecurityScheme> securityScheme() {
        final SwaggerProperties.Authorization            authorization       = swaggerProperties.getAuthorization();
        final List<SwaggerProperties.AuthorizationScope> authorizationScopes = authorization.getAuthorizationScopes();
        if (authorizationScopes.isEmpty()) {
            return Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header"));
        }
        List<AuthorizationScope> authorizationAuthorizationScopeList = authorizationScopes.stream()
                .map(authorizationScope -> new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription()))
                .collect(Collectors.toList());
        List<GrantType> grantTypes = authorization.getTokenUrls().stream()
                .map(ResourceOwnerPasswordCredentialsGrant::new)
                .collect(Collectors.toList());
        return Collections.singletonList(new OAuth(authorization.getName(), authorizationAuthorizationScopeList, grantTypes));
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     */
    private List<SecurityContext> securityContext() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(this.defaultAuth()))
                        .build()
        );
    }

    /**
     * 默认的全局鉴权策略
     */
    private SecurityReference defaultAuth() {
        final SwaggerProperties.Authorization            authorization = swaggerProperties.getAuthorization();
        final List<SwaggerProperties.AuthorizationScope> scopes        = authorization.getAuthorizationScopes();
        if (scopes.isEmpty()) {
            AuthorizationScope   authorizationScope               = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationAuthorizationScopes = new AuthorizationScope[1];
            authorizationAuthorizationScopes[0] = authorizationScope;
            return SecurityReference.builder()
                    .reference(HttpHeaders.AUTHORIZATION)
                    .scopes(authorizationAuthorizationScopes)
                    .build();
        }
        AuthorizationScope[] authorizationScopes = scopes.stream()
                .map(authorizationScope -> new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription()))
                .toArray(AuthorizationScope[]::new);
        return SecurityReference.builder()
                .reference(authorization.getName())
                .scopes(authorizationScopes)
                .build();
    }

    /**
     * 配置自定义全局参数
     */
    private List<RequestParameter> globalOperationParameters() {
        final List<SwaggerProperties.RequestParameter> parameterList = swaggerProperties.getRequestParameters();
        return parameterList.stream().map(parameter -> {
            RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
            return parameterBuilder
                    .name(parameter.getName())
                    .description(parameter.getDescription())
                    .required(parameter.getRequired())
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 联系方式配置
     */
    private Contact contact() {
        final SwaggerProperties.Contact contact = swaggerProperties.getContact();
        return new Contact(contact.getName(), contact.getPhone(), contact.getEmail());
    }

}
