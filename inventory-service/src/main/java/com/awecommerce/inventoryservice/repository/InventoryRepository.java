package com.awecommerce.inventoryservice.repository;

import com.awecommerce.inventoryservice.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Optional<Inventory> findBySku(String sku);
}
