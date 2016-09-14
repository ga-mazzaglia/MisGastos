package com.products

import com.products.rest.RestBuilder
import com.products.rest.RestClient
import org.apache.http.HttpStatus

class ApiPreciosClarosService {

    /**
     * Obtenemos todas las categorias desde la api y solo guardamos las padres
     *
     * @return
     */
    public List<Category> getAllCategories() {
        List<Category> categories = []
        try {
            RestClient restAPI = new RestBuilder()
                    .withUrl("https://8kdx6rx8h4.execute-api.us-east-1.amazonaws.com")
                    .withUriPath("/prod/categorias")
                    .withHeaders([
                    "x-api-key": "qfcNgctUb27Qw5w07u0sA5pNfp51Q9mo9XhIuZpw"
            ]).build();
            def response = restAPI.doGet();

            if (response.status >= HttpStatus.SC_OK && response.status <= HttpStatus.SC_MULTI_STATUS) {
                response.response.categorias.each { category ->
                    try {
                        Category cat = Category.findByCategoryId(category.id);
                        if (cat == null) {
                            cat = new Category();
                        }
                        cat.categoryId = category.id;
                        cat.categoryName = category.nombre;
                        cat.categoryLevel = category.nivel;
                        cat.save(flush: true, failOnError: true);
                        categories << cat;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categories;
    }

    /**
     * Obtenemos todos los productos de una categoria
     *
     * @param catId
     * @param offset
     * @param limit
     * @param products
     * @return
     */
    public List<Product> getAllProductsFromCategoryId(String catId, Integer offset = 0, Integer limit = 100, List<Product> products = []) {
        println "---"
        println "getAllProductsFromCategoryId ${catId} ${limit} ${offset}"
        try {
            Map queryString = [
                    id_categoria    : catId,
                    array_sucursales: "16-1-102,1-1-1,19-1-02102,9-2-468,7-1-10,19-1-03263,9-2-29,10-2-295,19-1-03264,9-1-475,16-2-4304,19-1-02060,19-1-02101,7-1-4,8-1-3,9-2-60,16-2-4704,9-2-436,9-1-114,19-1-02110,16-2-4604,23-1-6253,16-2-4204,7-1-33,7-1-44,10-1-50,11-2-1097,9-2-33,8-1-2,9-2-709",
                    offset          : offset,
                    limit           : limit,
                    sort            : "-cant_sucursales_disponible"
            ];
            RestClient restAPI = new RestBuilder()
                    .withUrl("https://8kdx6rx8h4.execute-api.us-east-1.amazonaws.com")
                    .withUriPath("/prod/productos")
                    .withQuery(queryString)
                    .withHeaders([
                    "x-api-key": "qfcNgctUb27Qw5w07u0sA5pNfp51Q9mo9XhIuZpw"
            ]).build();
            def response = restAPI.doGet();

            if (response.status >= HttpStatus.SC_OK && response.status <= HttpStatus.SC_MULTI_STATUS) {
                response.response.productos.each { product ->
                    try {
                        Product prod = Product.findByProductId(product.id);
                        if (prod == null) {
                            prod = new Product();
                        }
                        prod.productId = product.id;
                        prod.productName = product.nombre;
                        prod.productBrand = product.marca;
                        prod.productPresentation = product.presentacion;
                        prod.productNumberOfBranches = product.cantSucursalesDisponible;
                        prod.productPriceMax = Double.parseDouble(product.precioMax.toString());
                        prod.productPriceMin = Double.parseDouble(product.precioMin.toString());
                        prod.productImage = "https://imagenes.preciosclaros.gob.ar/productos/${product.id}.jpg"
                        prod.save(flush: true, failOnError: true);
                        products << prod;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (response.response.totalPagina >= limit) {
                    products = this.getAllProductsFromCategoryId(catId, (offset + limit), limit, products);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }

}
