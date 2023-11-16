package com.awecommerce.productservice.controller;

import com.awecommerce.productservice.dto.CreateProductRequest;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

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
        createProduct(makeValidProductRequest())
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testCreateProductReturnsValidResponseWhenValidRequestProvided() throws Exception {
        createProduct(makeValidProductRequest())
            .andExpect(
                MockMvcResultMatchers.jsonPath("id").isString()
            ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isNotEmpty()
            ).andExpect(
                MockMvcResultMatchers.jsonPath("name").value("product-name")
            ).andExpect(
                MockMvcResultMatchers.jsonPath("description").value("product-description")
            ).andExpect(
                MockMvcResultMatchers.jsonPath("variants").isNotEmpty()
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("variants[0].price").value(100.0)
            );
    }

    @Test
    void testCreateProductReturnsHTTPStatusBadRequestWhenInvalidRequestProvided() throws Exception {
        CreateProductRequest createProductRequest = CreateProductRequest.builder().build();
        createProduct(createProductRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        createProductRequest.setName("");
        createProduct(createProductRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        var variant = CreateProductRequest.Variant
            .builder()
            .price(-10.0)
            .build();
        createProductRequest.setVariants(List.of(variant));
        createProduct(createProductRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateProductReturnsCorrectErrorMessageWhenInvalidRequestProvided() throws Exception {
        CreateProductRequest createProductRequest = CreateProductRequest.builder().build();
        createProduct(createProductRequest)
            .andExpect(
                MockMvcResultMatchers.jsonPath("name").value("must not be blank")
            ).andExpect(
                MockMvcResultMatchers.jsonPath("variants").value("must not be empty")
            );

        createProductRequest.setName("");
        createProduct(createProductRequest)
            .andExpect(
                MockMvcResultMatchers.jsonPath("name").value("must not be blank")
            ).andExpect(
                MockMvcResultMatchers.jsonPath("variants").value("must not be empty")
            );
    }

    //<editor-fold description="Helpers">

    private ResultActions createProduct(CreateProductRequest createProductRequest) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeProductRequestJSON(createProductRequest))
        );
    }

    private CreateProductRequest makeValidProductRequest() {
        var variant = CreateProductRequest.Variant
            .builder()
            .price(100.0)
            .build();
        return CreateProductRequest
            .builder()
            .name("product-name")
            .description("product-description")
            .variants(List.of(variant))
            .build();
    }

    private String makeProductRequestJSON(CreateProductRequest createProductRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createProductRequest);
    }
    
    //</editor-fold>
}
