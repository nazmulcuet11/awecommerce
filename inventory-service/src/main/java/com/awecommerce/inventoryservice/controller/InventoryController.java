package com.awecommerce.inventoryservice.controller;

import com.awecommerce.inventoryservice.dto.InventoryDto;
import com.awecommerce.inventoryservice.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> findBySkuIn(
        @RequestParam("sku") List<String> skus
    ) {
        return inventoryService
            .findBySkuIn(skus)
            .stream()
            .map(inventory -> modelMapper.map(inventory, InventoryDto.class))
            .toList();
    }
}
