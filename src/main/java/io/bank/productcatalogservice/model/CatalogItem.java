package io.bank.productcatalogservice.model;

public class CatalogItem {

    private String productId;
    private String name;
    private String description;
    private String rating;

    public CatalogItem(String productId, String name, String description, String rating) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public CatalogItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
