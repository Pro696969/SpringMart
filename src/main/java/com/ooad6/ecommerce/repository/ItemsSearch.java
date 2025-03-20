package com.ooad6.ecommerce.repository;

import com.ooad6.ecommerce.model.Items;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemsSearch extends MongoRepository<Items, String> {

}
