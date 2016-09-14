package com.products

class TestController {

    ApiPreciosClarosService apiPreciosClarosService

    def viewProduct() {
        Product prod = Product.findByProductId(params.id);
        println prod;
        render(view: "main", model: [product: prod]);
    }

    def categories() {
        def categories = apiPreciosClarosService.getAllCategories();

        render categories as grails.converters.JSON
    }

    def productsFromCategory() {
        def products = [];
        Category cate = Category.findByCategoryId(params.categoryId ?: "001");
        if(cate){
            products = apiPreciosClarosService.getAllProductsFromCategoryId(cate.categoryId);
        }
        render products as grails.converters.JSON
    }

    def allProducts() {
        List<Category> cate = Category.getAll();
        cate.each { Category category ->
            apiPreciosClarosService.getAllProductsFromCategoryId(category.categoryId);
        }
        render "Listo :D";
    }

}
