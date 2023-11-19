package com.awecommerce.inventoryservice.service;

import com.awecommerce.inventoryservice.domain.Inventory;
import com.awecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> findBySkuIn(List<String> skus) {
        return inventoryRepository.findBySkuIn(skus);
    }
}
