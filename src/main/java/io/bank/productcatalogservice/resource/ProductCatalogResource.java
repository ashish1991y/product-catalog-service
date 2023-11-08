package io.bank.productcatalogservice.resource;

import com.netflix.discovery.DiscoveryClient;
import io.bank.productcatalogservice.model.CatalogItem;
import io.bank.productcatalogservice.model.Product;
import io.bank.productcatalogservice.model.Rating;
import io.bank.productcatalogservice.model.UserRating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class ProductCatalogResource {

    @Autowired
    @Lazy
    RestTemplate restTemplate;

    public static final String GET_PRODUCT_SERVICE="getUserService";

    @RequestMapping("/{productId}")
    @CircuitBreaker(name=GET_PRODUCT_SERVICE,fallbackMethod = "getProductFallBack")
    public CatalogItem getProduct(@PathVariable("productId") String productId) {
        Product product = restTemplate.getForObject("http://PRODUCT-INFO-SERVICE/products/" + productId, Product.class);
        Rating rating = restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/" + productId, Rating.class);
        return new CatalogItem(product.getProductId(),product.getName(), product.getDescription(), rating.getRating());
    }
    public CatalogItem getProductFallBack(Exception e)
    {
        return new CatalogItem("N/A","N/A","N/A","N/A");
    }

    @RequestMapping("/all")
    public List<CatalogItem> getAllProducts() {
        ResponseEntity<List<Product>> ProductsResponse = restTemplate.exchange("http://PRODUCT-INFO-SERVICE/products/all", HttpMethod.GET,null, new ParameterizedTypeReference<List<Product>>(){
        });
        List<Product> products = ProductsResponse.getBody();
        return products.stream().map(product -> {
            //for every product id ,calling rating service and getting ratings
            Rating rating = restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/" + product.getProductId(), Rating.class);
            //putting all together
            return new CatalogItem(product.getProductId(),product.getName(), product.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
    }
}
