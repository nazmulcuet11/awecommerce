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
                MockMvcResultMatchers.jsonPath("price").value(100.0)
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

        createProductRequest.setPrice(-10.0);
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
                MockMvcResultMatchers.jsonPath("price").value("must not be null")
            );

        createProductRequest.setName("");
        createProduct(createProductRequest)
            .andExpect(
                MockMvcResultMatchers.jsonPath("name").value("must not be blank")
            ).andExpect(
                MockMvcResultMatchers.jsonPath("price").value("must not be null")
            );

        createProductRequest.setPrice(-10.0);
        createProduct(createProductRequest)
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("price")
                    .value("must be greater than or equal to 0")
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

    private CreateProductRequest makeProductRequest(
        String name,
        String description,
        Double price
    ) {
        return CreateProductRequest
            .builder()
            .name(name)
            .description(description)
            .price(price)
            .build();
    }

    private CreateProductRequest makeValidProductRequest() {
        return makeProductRequest(
            "product-name",
            "product-description",
            100.0
        );
    }

    private String makeProductRequestJSON(CreateProductRequest createProductRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createProductRequest);
    }
    
    //</editor-fold>
}
