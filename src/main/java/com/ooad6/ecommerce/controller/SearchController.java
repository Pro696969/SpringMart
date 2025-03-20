package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    ItemsSearch items;

    @GetMapping("/allitems")
    public List<Items> allItems() {
        return items.findAll();
    }
}
