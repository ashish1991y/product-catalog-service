package io.bank.productcatalogservice.model;

public class Rating {
    private String productId;
    private String rating;

    public Rating() {
    }

    public Rating(String productId, String rating) {
        this.productId = productId;
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
