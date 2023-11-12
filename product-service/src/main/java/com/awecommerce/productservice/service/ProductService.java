package com.awecommerce.productservice.service;

import com.awecommerce.productservice.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Product save(Product product) {
        mongoTemplate.insert(product);
        log.info("Product {} saved.", product);
        return product;
    }

    public Page<Product> find(
        String name,
        String description,
        Double minPrice,
        Double maxPrice,
        Pageable pageable
    ) {
        Query query = new Query().with(pageable);

        List<Criteria> criteria = new ArrayList<>();
        if(name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if(description != null && !description.isEmpty()) {
            criteria.add(Criteria.where("description").regex(description, "i"));
        }
        if(minPrice != null) {
            criteria.add(Criteria.where("price").gte(minPrice));
        }
        if(maxPrice != null) {
            criteria.add(Criteria.where("price").lte(maxPrice));
        }

        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return PageableExecutionUtils.getPage(
            mongoTemplate.find(query, Product.class),
            pageable,
            () -> mongoTemplate.count(query.skip(0).limit(0), Product.class)
        );
    }
}
