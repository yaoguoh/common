## common 
### pom
``` pom
<dependencies>
    <dependency>
        <groupId>com.github.yaoguoh</groupId>
        <artifactId>common</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
```

## common-util
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-util</artifactId>
</dependency>
```
- **`Result`** 返回对象
- **`ResultGenerator`** 返回对象构造器
### example
``` java
/**
 * 消息管理 REST API
 */
@Api(tags = "消息管理 REST API")
@Slf4j
@RestController
@RequestMapping(value = "/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * 根据消息ID查询消息信息
     *
     * @param messageId the message id
     * @return the result
     */
    @ApiOperation(value = "根据消息ID查询消息信息")
    @GetMapping(value = "/{messageId}")
    public Result<MessageVo> findByMessageId(@ApiParam(name = "messageId", value = "消息ID", example = "0") @PathVariable Long messageId) {
        log.info("findByMessageId - 根据消息ID查询消息信息. messageId={}", messageId);

        try {
            return ResultGenerator.ok(MessageVo.create(messageService.findById(messageId)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultGenerator.wrap(e);
        }
    }

}

```

## common-elasticsearch
### pom
``` 
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-elasticsearch</artifactId>
</dependency>
```
- **`ElasticsearchProperties`**  `RestHighLevelClient` 客户端连接信息
- **`RestHighLevelClientConfiguration`** 注册 `restHighLevelClient` `Bean`处理`elasticsearch server`访问协议为`https`
### Spring Boot Application  `@Configuration`配置类 引入 `RestHighLevelClientConfiguration.class`
``` java
/**
 * 应用配置置类
 */
@Configuration
@Import(RestHighLevelClientConfiguration.class)
public class ProviderConfiguration {
    
}
```
### 在`yaml`中添加配置信息
``` yaml
elasticsearch:
  xpack-username: 'elastic'
  xpack-password: password'
  keystore: demo.elasticsearch.com.jks
  keystore-password: password
  host: demo.elasticsearch.com
  port: 443
  scheme: https
```

## common-jpa
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-jpa</artifactId>
</dependency>
```
- **IService** 通用接口
- **BaseService** 通用接口实现
### example
``` java
/**
 * User repository
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
}
```
``` java
/**
 * The interface User service.
 */
public interface UserService extends IService<User, Long> {
  
}
```
``` java
/**
 * User service impl.
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseService<User, Long> implements UserService {
    
}
```

## common-redis
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-redis</artifactId>
</dependency>
```

## common-spring-cloud
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-spring-cloud</artifactId>
</dependency>
```
### `Spring Cloud`服务基础依赖`pom`包含内容如下
``` pom
<dependencies>
    <!--eureka 客户端-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!--监控-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--监控客户端-->
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-client</artifactId>
        <version>${spring-boot-admin.version}</version>
    </dependency>
    <!-- 配置中心客户端-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <!--服务链路追踪-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-sleuth-zipkin</artifactId>
    </dependency>
    <!--断路器依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
</dependencies>
```

## common-exception
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-exception</artifactId>
</dependency>
```

## common-swagger
### pom
```
<dependency>
    <groupId>com.github.yaoguoh.common</groupId>
    <artifactId>common-swagger</artifactId>
</dependency>
```
### example yaml 
``` yaml
swagger:
  api-info:
    title: '应用服务'
    description: '应用服务 RESTFUL API'
    host: gateway.demo.com
    parameters:
      - name: 'Identity'
        description: '设备标识'
        modelRef: 'string'
        parameterType: 'header'
```