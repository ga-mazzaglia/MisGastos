package com.products

class Category {
    Long id
    String categoryId
    String categoryName
    Integer categoryLevel

    static constraints = {
        categoryId(nullable: false, blank: false);
        categoryName(nullable: false, blank: false);
        categoryLevel(nullable: false);
    }

    static mapping = {
        version false;
        categoryName type: "text"
    }

    @Override
    public String toString() {
        return [
                id           : id,
                categoryId   : categoryId,
                categoryName : categoryName,
                categoryLevel: categoryLevel
        ] as grails.converters.JSON;
    }
}
