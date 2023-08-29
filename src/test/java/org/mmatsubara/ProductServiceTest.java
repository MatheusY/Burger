package org.mmatsubara;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mmatsubara.model.Product;
import org.mmatsubara.model.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProductServiceTest extends AbstractTest {

    @Test
    public void testFindProducts() {
        given()
                .when().get("/api/products")
                .then()
                .statusCode(200)
                .body("size", equalTo(3));
    }

    @Test
    public void testFindProductsById() {
        given()
                .when().get("/api/products/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Hamburger"),
                        "price", equalTo(15.0F),
                        "productType", equalTo(ProductType.FOOD.toString()),
                        "description", equalTo("Delicioso hamburger tradicional!"));
    }

    @Test
    public void testFindProductsByIdNotFound() {
        given()
                .when().get("/api/products/10")
                .then()
                .statusCode(404)
                .body("message", equalTo("Product doesn't not exist!"));
    }

    @SneakyThrows
    @Test
    public void testCreateProduct() {
        var product = getProduct();
        var response = given()
                .when()
                .body(mapper.writeValueAsString(product))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/products");
        var id = response.getBody().as(Long.class);

        response.then()
                .statusCode(201);

        given()
                .when().get("/api/products/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(product.name),
                        "price", equalTo(product.price.floatValue()),
                        "productType", equalTo(product.productType.toString()),
                        "description", equalTo(product.description));

        removeProduct(id);
    }

    @SneakyThrows
    @Test
    public void testUpdateProduct() {
        var id = getPersistedProduct().id;
        var product = getProduct();
        given()
                .when()
                .body(mapper.writeValueAsString(product))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/api/products/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/api/products/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(product.name),
                        "price", equalTo(product.price.floatValue()),
                        "productType", equalTo(product.productType.toString()),
                        "description", equalTo(product.description));

        removeProduct(id);
    }

    @SneakyThrows
    @Test
    public void testUpdateProductNotFound() {
        var id = 100;
        Product product = getProduct();
        given()
                .when()
                .body(mapper.writeValueAsString(product))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/api/products/" + id)
                .then()
                .statusCode(404)
                .body("message", equalTo("Product doesn't not exist!"));
    }

    @SneakyThrows
    @Test
    public void testDeleteProduct() {
        var id = getPersistedProduct().id;
        given()
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete("/api/products/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/api/products/" + id)
                .then()
                .statusCode(404)
                .body("message", equalTo("Product doesn't not exist!"));
    }

    @SneakyThrows
    @Test
    public void testDeleteProductNotFound() {
        var id = 100;
        given()
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete("/api/products/" + id)
                .then()
                .statusCode(404)
                .body("message", equalTo("Product doesn't not exist!"));
    }

    @Transactional
    public static void removeProduct(Long id) {
        Product.deleteById(id);
    }

    @Transactional
    public Product getPersistedProduct() {
        var product = getProduct();
        product.createdDate = LocalDateTime.now();
        product.isActive = true;
        Product.persist(product);
        return product;
    }

    private Product getProduct() {
        var product = new Product();
        product.name = faker.funnyName().name();
        product.description = faker.chuckNorris().fact();
        product.productType = faker.options().option(ProductType.class);
        product.price = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000));
        return product;
    }

}