package com.github.yaoguoh.common.swagger.config;

import com.github.yaoguoh.common.swagger.properties.SwaggerProperties;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.ApiOperation;
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
        return new Docket(DocumentationType.OAS_30)
                .groupName(swaggerProperties.getGroupName())
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo())
                .select()
                //扫描所有有注解的api，
//                .apis(RequestHandlerSelectors.any()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .paths(PathSelectors.any())
                .build()
                //配置自定义参数
                .globalRequestParameters(this.globalOperationParameters())
                //配置鉴权信息
                .securitySchemes(Collections.singletonList(this.securityScheme()))
                .securityContexts(Collections.singletonList(this.securityContext()))
                .pathMapping("/");
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
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(this.defaultAuth()))
                .build();
    }

    /**
     * 默认的全局鉴权策略
     */
    private SecurityReference defaultAuth() {

        final SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();

        final List<SwaggerProperties.Scope> scopes = authorization.getScopeList();

        if (scopes.isEmpty()) {
            AuthorizationScope   authorizationScope  = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return SecurityReference.builder()
                    .reference(HttpHeaders.AUTHORIZATION)
                    .scopes(authorizationScopes)
                    .build();
        }

        AuthorizationScope[] authorizationScopes = scopes.stream()
                .map(scope -> new AuthorizationScope(scope.getScope(), scope.getDescription()))
                .toArray(AuthorizationScope[]::new);

        return SecurityReference.builder()
                .reference(authorization.getName())
                .scopes(authorizationScopes)
                .build();
    }

    /**
     * 认证方式配置
     */
    private SecurityScheme securityScheme() {

        final SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();

        final List<SwaggerProperties.Scope> scopes = authorization.getScopeList();

        if (scopes.isEmpty()) {
            return new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
        }

        List<AuthorizationScope> authorizationScopeList = scopes.stream()
                .map(scope -> new AuthorizationScope(scope.getScope(), scope.getDescription()))
                .collect(Collectors.toList());

        List<GrantType> grantTypes = authorization.getTokenUrlList().stream()
                .map(ResourceOwnerPasswordCredentialsGrant::new)
                .collect(Collectors.toList());

        return new OAuth(authorization.getName(), authorizationScopeList, grantTypes);
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
