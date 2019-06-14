package com.github.yaoguoh.common.elasticsearch.config;

import com.github.yaoguoh.common.elasticsearch.properties.ElasticsearchProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * The type Rest high level client config.
 *
 * @author WYG
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
@AllArgsConstructor
public class RestHighLevelClientConfiguration {

    private final ElasticsearchProperties elasticsearchProperties;

    /**
     * Client rest high level client.
     *
     * @return the rest high level client
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchProperties.getXpackUsername(), elasticsearchProperties.getXpackPassword()));

        try {
            final SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(new ClassPathResource(elasticsearchProperties.getKeystore()).getFile(), elasticsearchProperties.getKeystorePassword().toCharArray(), new TrustSelfSignedStrategy())
                    .build();

            RestClientBuilder restClientBuilder =
                    RestClient.builder(
                            new HttpHost(elasticsearchProperties.getHost(), elasticsearchProperties.getPort(), elasticsearchProperties.getScheme()))
                            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                    .setDefaultCredentialsProvider(credentialsProvider)
                                    .setSSLContext(sslContext)
                            );
            return new RestHighLevelClient(restClientBuilder);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException: [{}]", e.getMessage());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            log.error("KeyManagementException: [{}]", e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            log.error("KeyStoreException: [{}]", e.getMessage());
            e.printStackTrace();
        } catch (CertificateException e) {
            log.error("CertificateException: [{}]", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException: [{}]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
