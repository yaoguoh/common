package com.github.yaoguoh.common.swagger.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * The type Swagger properties.
 *
 * @author WYG
 */
@Data
@ConfigurationProperties("swagger.api-info")
public class SwaggerProperties {

    /**
     * 是否开启 swagger
     */
    private Boolean                enable            = true;
    /**
     * 分组名称
     */
    private String                 groupName         = "default";
    /**
     * 标题
     */
    private String                 title             = "API";
    /**
     * 描述
     */
    private String                 description       = "RESTFUL API";
    /**
     * 版本
     */
    private String                 version           = "3.0";
    /**
     * 执照
     */
    private String                 license           = "Apache 2.0";
    /**
     * 执照地址
     */
    private String                 licenseUrl        = "http://www.apache.org/licenses/LICENSE-2.0.html";
    /**
     * 服务条款网址
     */
    private String                 termsOfServiceUrl = "http://www.rsswork.com";
    /**
     * Host信息接口调用地址
     **/
    private String                 host;
    /**
     * swagger会解析的包路径
     **/
    private String                 basePackage;
    /**
     * swagger会解析的url规则
     **/
    private List<String>           basePath          = Lists.newArrayList();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String>           excludePath       = Lists.newArrayList();
    /**
     * 联系人信息
     */
    private Contact                contact           = new Contact();
    /**
     * 全局统一鉴权配置
     **/
    private Authorization          authorization     = new Authorization();
    /**
     * 自定义参数
     **/
    private List<RequestParameter> requestParameters = Lists.newArrayList();

    /**
     * The type Contact.
     */
    @Data
    @NoArgsConstructor
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "admin";
        /**
         * 联系人电话
         **/
        private String phone;
        /**
         * 联系人email
         **/
        private String email;
    }

    /**
     * The type Contact.
     */
    @Data
    @NoArgsConstructor
    public static class RequestParameter {
        /**
         * 参数名称
         **/
        private String  name;
        /**
         * 描述
         **/
        private String  description;
        /**
         * 是否必填
         **/
        private Boolean required = false;
    }


    /**
     * The type Authorization.
     */
    @Data
    @NoArgsConstructor
    public static class Authorization {

        /**
         * 鉴权策略ID，需要和SecurityReferences ID保持一致
         */
        private String                   name;
        /**
         * 需要开启鉴权URL的正则
         */
        private String                   authRegex           = "^.*$";
        /**
         * 鉴权作用域列表
         */
        private List<AuthorizationScope> authorizationScopes = Lists.newArrayList();
        /**
         * 令牌URL列表
         */
        private List<String>             tokenUrls           = Lists.newArrayList();
    }

    /**
     * The type Authorization scope.
     */
    @Data
    @NoArgsConstructor
    public static class AuthorizationScope {

        /**
         * 作用域名称
         */
        private String scope;
        /**
         * 作用域描述
         */
        private String description;
    }
}
