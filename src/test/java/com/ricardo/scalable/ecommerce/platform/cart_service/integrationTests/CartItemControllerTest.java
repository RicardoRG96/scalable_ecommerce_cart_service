package com.ricardo.scalable.ecommerce.platform.cart_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;
import static com.ricardo.scalable.ecommerce.platform.cart_service.integrationTests.testData.CartItemControllerTestData.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CartItemControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private Environment env;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetCartItemById() {
        client.get()
            .uri("/cart-items/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals(1L, json.path("id").asLong()),
                        () -> assertEquals(1L, json.path("cart").path("id").asLong()),
                        () -> assertEquals(1L, json.path("productSku").path("id").asLong()),
                        () -> assertEquals(1, json.path("quantity").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(2)
    void testGetCartItemByIdNotFound() {
        client.get()
            .uri("/cart-items/100")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetCartItemsByCartId() {
        client.get()
            .uri("/cart-items/cart/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(3, json.size()),
                        () -> assertEquals(1L, json.get(0).path("cart").path("id").asLong()),
                        () -> assertEquals(1L, json.get(0).path("productSku").path("id").asLong()),
                        () -> assertEquals(1, json.get(0).path("quantity").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(4)
    void testGetCartItemsByCartIdNotFound() {
        client.get()
            .uri("/cart-items/cart/100")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetCartItemsByProductSkuId() {
        client.get()
            .uri("/cart-items/product-sku/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(2, json.size()),
                        () -> assertEquals(1L, json.get(0).path("cart").path("id").asLong()),
                        () -> assertEquals(1L, json.get(0).path("productSku").path("id").asLong()),
                        () -> assertEquals(1, json.get(0).path("quantity").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(6)
    void testGetCartItemsByProductSkuIdNotFound() {
        client.get()
            .uri("/cart-items/product-sku/100")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetAllCartItems() {
        client.get()
            .uri("/cart-items")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(5, json.size())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(8)
    void testCreateCartItem() {
        CartItemDto requestBody = createCartItemDto();

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals(6L, json.path("id").asLong()),
                        () -> assertEquals(2L, json.path("cart").path("id").asLong()),
                        () -> assertEquals(6L, json.path("productSku").path("id").asLong()),
                        () -> assertEquals(2, json.path("quantity").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(9)
    void testCreateCartItemWithNoProductSkuId() {
        CartItemDto requestBody = createCartItemDto();
        requestBody.setProductSkuId(null);

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(10)
    void testCreateCartItemWithNoCartId() {
        CartItemDto requestBody = createCartItemDto();
        requestBody.setCartId(null);

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    void testCreateCartItemWithNoQuantity() {
        CartItemDto requestBody = createCartItemDto();
        requestBody.setQuantity(0);

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    void tesCreateCartItemNotFoundCartId() {
        CartItemDto requestBody = createCartItemDto();
        requestBody.setCartId(9999L);

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testCreateCartItemNotFoundProductSkuId() {
        CartItemDto requestBody = createCartItemDto();
        requestBody.setProductSkuId(9999L);

        client.post()
            .uri("/cart-items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(14)
    void testUpdateCartItem() {
        CartItemDto requestBody = createCartItemDtoToUpdate();

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals(1L, json.path("id").asLong()),
                        () -> assertEquals(1L, json.path("cart").path("id").asLong()),
                        () -> assertEquals(1L, json.path("productSku").path("id").asLong()),
                        () -> assertEquals(3, json.path("quantity").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(15)
    void testUpdateCartItemNotFoundCartItemId() {
        CartItemDto requestBody = createCartItemDtoToUpdate();

        client.put()
            .uri("/cart-items/9999")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(16)
    void testUpdateCartItemNotFoundCartId() {
        CartItemDto requestBody = createCartItemDtoToUpdate();
        requestBody.setCartId(9999L);

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(17)
    void testUpdateCartItemNotFoundProductSkuId() {
        CartItemDto requestBody = createCartItemDtoToUpdate();
        requestBody.setProductSkuId(9999L);

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(18)
    void testUpdateCartItemWithNoProductSkuId() {
        CartItemDto requestBody = createCartItemDtoToUpdate();
        requestBody.setProductSkuId(null);

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(19)
    void testUpdateCartItemWithNoCartId() {
        CartItemDto requestBody = createCartItemDtoToUpdate();
        requestBody.setCartId(null);

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(20)
    void testUpdateCartItemWithNoQuantity() {
        CartItemDto requestBody = createCartItemDtoToUpdate();
        requestBody.setQuantity(0);

        client.put()
            .uri("/cart-items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(21)
    void testDeleteCartItem() {
        client.delete()
            .uri("/cart-items/1")
            .exchange()
            .expectStatus().isNoContent();

        client.get()
            .uri("/cart-items")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(5, json.size())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(22)
    void testGetDeletedCartItem() {
        client.get()
            .uri("/cart-items/1")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertArrayEquals(new String[] { "test" }, activeProfiles);
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
