package io.bank.productcatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProductCatalogServiceApplication {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        //setting timeout in Rest template(not preferred way)
      /*  HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        return new RestTemplate(clientHttpRequestFactory);*/
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogServiceApplication.class, args);
    }

}
