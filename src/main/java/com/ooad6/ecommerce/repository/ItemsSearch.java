package com.ooad6.ecommerce.repository;

import com.ooad6.ecommerce.model.Items;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemsSearch extends MongoRepository<Items, String> {
    List<Items> findAll();

}
