package io.bank.productcatalogservice.resource;

import com.netflix.discovery.DiscoveryClient;
import io.bank.productcatalogservice.model.CatalogItem;
import io.bank.productcatalogservice.model.Product;
import io.bank.productcatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class ProductCatalogResource {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getProducts(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users/" + userId, UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {
            //for every product id ,calling product info service and getting details
            Product product = restTemplate.getForObject("http://PRODUCT-INFO-SERVICE/products/" + rating.getProductId(), Product.class);
            //putting all together
            return new CatalogItem(product.getName(), "product desc", rating.getRating());
        }).collect(Collectors.toList());
    }
}
