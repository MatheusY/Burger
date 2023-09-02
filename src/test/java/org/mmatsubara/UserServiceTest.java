package org.mmatsubara;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mmatsubara.model.Product;
import org.mmatsubara.model.User;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class UserServiceTest extends AbstractTest {

    @Test
    public void testFindUserById() {
        given()
                .when().get("/api/users/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Matheus"),
                        "id", equalTo(1),
                        "email", equalTo("matheus.matsubara@gmail.com"));
    }

    @Test
    public void testFindUserByIdNotFound() {
        given()
                .when().get("/api/users/10")
                .then()
                .statusCode(404)
                .body("message", equalTo("User doesn't exist!"));
    }

    @SneakyThrows
    @Test
    public void testCreateUser() {
        var user = getUser();
        var response = given()
                .when()
                .body(mapper.writeValueAsString(user))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/users");
        var id = response.getBody().as(Long.class);

        response.then()
                .statusCode(201);

        given()
                .when().get("/api/users/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(user.name),
                        "email", equalTo(user.email),
                        "id", equalTo(id.intValue()));

        removeUser(id);
    }

    @SneakyThrows
    @Test
    public void testCreateUserWhenEmailExists() {
        var user = getUser();
        user.email = "matheus.matsubara@gmail.com";
        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/users")
                .then()
                .statusCode(400)
                .body("message", equalTo("This email already exists!"));
    }

    @SneakyThrows
    @Test
    public void testCreateUserWhenEmailExistsInactiveAccount() {
        var persistedUser = getPersistedUser(false);
        var user = getUser();
        user.email = persistedUser.email;
        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post("/api/users")
                .then()
                .statusCode(400)
                .body("message", equalTo("This account has been inactivated, please contact the administrator!"));

        removeUser(persistedUser.id);
    }

    @SneakyThrows
    @Test
    public void testUpdateUser() {
        var persistedUser = getPersistedUser(true);
        var user = getUser();
        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/api/users/" + persistedUser.id)
                .then()
                .statusCode(204);

        given()
                .when().get("/api/users/" + persistedUser.id)
                .then()
                .statusCode(200)
                .body("name", equalTo(user.name),
                        "email", equalTo(persistedUser.email),
                        "id", equalTo(persistedUser.id.intValue()));

        removeUser(persistedUser.id);
    }

    @SneakyThrows
    @Test
    public void testUpdateUserNotFound() {
        var user = getUser();
        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/api/users/100")
                .then()
                .statusCode(404)
                .body("message" , equalTo("User doesn't exist!"));
    }

    @Transactional
    public static void removeUser(Long id) {
        User.deleteById(id);
    }

    @Transactional
    public User getPersistedUser(boolean isActive) {
        var user = getUser();
        user.createdDate = LocalDateTime.now();
        user.isActive = isActive;
        User.persist(user);
        return user;
    }

    private User getUser() {
        var user = new User();
        user.name = faker.funnyName().name();
        user.email = faker.internet().emailAddress();
        return user;
    }

}