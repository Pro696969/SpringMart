package com.ooad6.ecommerce.repository;

import com.ooad6.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User,String> {
}
