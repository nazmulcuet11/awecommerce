package com.awecommerce.productservice.controller;

import com.awecommerce.productservice.dto.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductControllerTests {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testCreateProductReturnsHttpStatusCreatedWhenValidRequestProvided() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeProductRequestJSON())
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void testCreateProductReturnsValidResponseWhenValidRequestProvided() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeProductRequestJSON())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("name").value("product-name")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("description").value("product-description")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("price").value(100.0)
        );
    }

    ProductRequest makeProductRequest() {
        return ProductRequest
                .builder()
                .name("product-name")
                .description("product-description")
                .price(100.0)
                .build();
    }

    String makeProductRequestJSON() throws JsonProcessingException {
        return objectMapper.writeValueAsString(makeProductRequest());
    }
}
