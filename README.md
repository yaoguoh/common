## common

### pom

```xml

<dependencies>
    <dependency>
        <groupId>com.github.yaoguoh</groupId>
        <artifactId>common</artifactId>
        <version>3.3.4</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
```

## common-util

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-util</artifactId>
</dependency>
```

- **`Result`** 返回对象
- **`ResultGenerator`** 返回对象构造器

### example

```java
public class ExampleController {
    /**
     * 查询用户角色列表
     *
     * @param realm  the realm
     * @param userId the user id
     * @return the result
     */
    @Operation(summary = "查询用户角色列表")
    @GetMapping(value = "/{userId}/roles")
    public Result<List<IdNameVO>> findUserRoles(@RequestHeader("realm") String realm, @Parameter(name = "userId", description = "用户ID（唯一标识）", required = true) @PathVariable("userId") String userId) {
        log.debug("findUserRoles - 查询用户角色列表. realm = [{}] username = [{}]", realm, userId);

        return ResultGenerator.ok(userService.findUserRoles(realm, userId));
    }
}
```

## common-elasticsearch

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-elasticsearch</artifactId>
</dependency>
```

- **`ElasticsearchProperties`**  `RestHighLevelClient` 客户端连接信息
- **`RestHighLevelClientConfiguration`** 注册 `restHighLevelClient` `Bean`处理`elasticsearch server`访问协议为`https`

### Spring Boot Application  `@Configuration`配置类 引入 `RestHighLevelClientConfiguration.class`

```java
/**
 * 应用配置置类
 */
@Configuration
@Import(RestHighLevelClientConfiguration.class)
public class ProviderConfiguration {

}
```

### 在`yaml`中添加配置信息

```yaml
elasticsearch:
  xpack-username: 'elastic'
  xpack-password: password'
  keystore: example.elasticsearch.com.jks
  keystore-password: password
  host: example.elasticsearch.com
  port: 443
  scheme: https
```

## common-jpa

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-jpa</artifactId>
</dependency>
```

- **IService** 通用接口
- **BaseService** 通用接口实现

### example

```java
/**
 * User repository
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
```

```java
/**
 * The interface User service.
 */
public interface UserService extends IService<User, Long> {

}
```

```java
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

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-redis</artifactId>
</dependency>
```

## common-spring-cloud

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-spring-cloud</artifactId>
</dependency>
```

### `Spring Cloud`服务基础依赖`pom`包含内容如下

```xml

<dependencies>
    <!-- Consul -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-config</artifactId>
    </dependency>
    <!-- Bootstrap -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    <!-- Actuator -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

## common-exception

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-exception</artifactId>
</dependency>
```

## common-doc

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-doc</artifactId>
</dependency>
```

### Configuration

```java

@Configuration
@Import({DocConfiguration.class})
public class ProviderConfiguration {
}
```

### example yaml

```yaml
doc:
  info:
    title: '应用服务'
    description: '应用服务 RESTFUL API'
    serverUrl: gateway.example.com
```

## common-job

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-job</artifactId>
</dependency>
```

### Configuration

```java

@Configuration
@Import({JobConfiguration.class})
public class ProviderConfiguration {
}
```

### example yaml

```yaml
job:
  admin-addresses: http://127.0.0.1:8080
  access-token:
  executor:
    app-name: example-executor
    address:
    ip:
    port: 0
    auditLog-path: /tmp/data
    auditLog-retention-days: 30
```

## common-log

### pom

```xml

<dependency>
    <groupId>com.github.yaoguoh</groupId>
    <artifactId>common-log</artifactId>
</dependency>
```

### example @Log

```java
public class ExampleController {
    /**
     * 用户创建
     *
     * @param realm   the realm
     * @param userDTO the user dto
     * @return the result
     */
    @Log(module = "用户管理", businessType = BusinessType.INSERT)
    @Operation(summary = "用户创建")
    @PostMapping("/create")
    public Result<Object> create(@RequestHeader("realm") String realm, @RequestBody UserDTO userDTO) {
        log.debug("create - 用户创建. realm=[{}] UserDTO=[{}]", realm, userDTO);

        userService.create(realm, userDTO);
        return ResultGenerator.ok();
    }
}
```