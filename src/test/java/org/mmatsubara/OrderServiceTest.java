package org.mmatsubara;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mmatsubara.dto.OrderDTO;
import org.mmatsubara.model.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class OrderServiceTest extends AbstractTest {

    @Test
    public void testFindOrdersByCustomerId() {
        given()
                .when().get("/api/orders/customer/1")
                .then()
                .statusCode(200)
                .body("size", equalTo(2));
    }

    @Test
    public void testFindOrdersById() {
        given()
                .when().get("/api/orders/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1),
                        "items", hasSize(2),
                        "items[0].id", equalTo(1),
                        "items[0].product.id", equalTo(1),
                        "items[0].quantity", equalTo(2),
                        "items[0].subtotal", equalTo(30.0F),
                        "orderStatus", equalTo("CONCLUDED"));
    }

    @Test
    public void testFindOrdersByIdNotFound() {
        given()
                .when().get("/api/orders/10")
                .then()
                .statusCode(404)
                .body("message", equalTo("Order doesn't exist!"));
    }

    @SneakyThrows
    @Test
    public void testCreateOrder() {
        var order = getOrder();
        var orderDTO = modelMapper.map(order, OrderDTO.class);
        var response = given()
                .when()
                .body(mapper.writeValueAsString(orderDTO))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/orders");
        var id = response.getBody().as(Long.class);

        response.then()
                .statusCode(201);

        var item = order.items.get(0);
        var product = item.product;

        given()
                .when().get("/api/orders/" + id)
                .then()
                .statusCode(200)
                .body("items", hasSize(order.items.size()),
                        "items.product.id", hasItem(equalTo(product.id.intValue())),
                        "items.quantity", hasItem(equalTo(item.quantity.intValue())),
                        "items.subtotal", hasItem(equalTo(item.subtotal.floatValue())),
                        "orderStatus", equalTo(order.orderStatus.toString()));

        removeOrder(id);
    }

    @SneakyThrows
    @Test
    public void testUpdateOrderStatus() {
        var persistedOrder = getPersistedOrder();
        var orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(faker.options().option(OrderStatus.class).toString());
        given()
                .when()
                .body(mapper.writeValueAsString(orderDTO))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .patch("/api/orders/" + persistedOrder.id)
                .then()
                .statusCode(204);

        var item = persistedOrder.items.get(0);
        var product = item.product;

        given()
                .when().get("/api/orders/" + persistedOrder.id)
                .then()
                .statusCode(200)
                .body("items", hasSize(persistedOrder.items.size()),
                        "items.product.id", hasItem(equalTo(product.id.intValue())),
                        "items.quantity", hasItem(equalTo(item.quantity.intValue())),
                        "items.subtotal", hasItem(equalTo(item.subtotal.floatValue())),
                        "orderStatus", equalTo(orderDTO.getOrderStatus()));

        removeOrder(persistedOrder.id);
    }

    @SneakyThrows
    @Test
    public void testUpdateOrderStatusNotFound() {
        var orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(faker.options().option(OrderStatus.class).toString());
        given()
                .when()
                .body(mapper.writeValueAsString(orderDTO))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .patch("/api/orders/100")
                .then()
                .statusCode(404)
                .body("message", equalTo("Order doesn't exist!"));
    }

    @SneakyThrows
    @Test
    public void testUpdateOrderStatusInvalidStatus() {
        var persistedOrder = getPersistedOrder();
        var orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(faker.company().name());
        given()
                .when()
                .body(mapper.writeValueAsString(orderDTO))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .patch("/api/orders/" + persistedOrder.id)
                .then()
                .statusCode(400)
                .body("message", equalTo("Invalid status!"));

        removeOrder(persistedOrder.id);
    }

    @Transactional
    public Order getPersistedOrder() {
        var order = getOrder();
        Order.persist(order);
        return order;
    }

    private Order getOrder() {
        var order = new Order();
        order.items = getItems(order, faker.number().numberBetween(1, 4));
        order.orderedDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        order.orderStatus = faker.options().option(OrderStatus.class);
        order.customer = getPersistedCustomer();
        return order;
    }

    private List<Item> getItems(Order order, int quantity) {
        List<Item> items = new ArrayList<>();
        for (int cont = 0; cont < quantity; cont++) {
            var item = new Item();
            item.order = order;
            var isProductValid = false;
            while (!isProductValid) {
                var product = getPersistedProduct();
                isProductValid = items.stream().noneMatch(i -> i.product.id == product.id);
                if (isProductValid) {
                    item.product = product;
                }
            }
            item.quantity = faker.number().numberBetween(1, 90);
            item.subtotal = item.product.price.multiply(BigDecimal.valueOf(item.quantity));
            items.add(item);
        }
        return items;
    }

    @Transactional
    public static void removeOrder(Long id) {
        Order.deleteById(id);
    }

    private User getPersistedCustomer() {
        return User.findById(faker.number().numberBetween(1, 5));
    }

    public Product getPersistedProduct() {
        return Product.findById(faker.number().numberBetween(1, 4));
    }

}
