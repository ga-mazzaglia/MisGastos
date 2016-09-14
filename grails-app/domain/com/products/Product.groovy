package com.products

class Product {
    Long id;

    String productId;
    String productName;
    String productBrand;
    Double productPriceMax;
    Double productPriceMin;
    String productPresentation;
    Integer productNumberOfBranches;
    String productImage;

    static constraints = {
        productId(nullable: false, blank: false);
        productName(nullable: false, blank: false);
        productBrand(nullable: false, blank: false);
        productPriceMax(nullable: false);
        productPriceMin(nullable: false);
        productPresentation(nullable: false, blank: false);
        productNumberOfBranches(nullable: false);
        productImage(nullable: false, blank: false);
    }

    static mapping = {
        version false;
        productName type: "text"
        productPresentation type: "text"
        productImage type: "text"
    }

    @Override
    public String toString() {
        return [
                id                     : id,
                productId              : productId,
                productName            : productName,
                productBrand           : productBrand,
                productPriceMax        : productPriceMax,
                productPriceMin        : productPriceMin,
                productPresentation    : productPresentation,
                productNumberOfBranches: productNumberOfBranches,
        ] as grails.converters.JSON;
    }
}
