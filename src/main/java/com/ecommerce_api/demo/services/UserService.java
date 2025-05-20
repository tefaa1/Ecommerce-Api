package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Role;
import com.ecommerce_api.demo.entity.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    User getUserWithWishlist(Long id);
    User getUserWithRoles(Long id);
    User getUserWithOrders(Long id);
    User getUserWithReviews(Long id);
    User getUserWithAllRelations(Long id);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    void deleteAllUsers();
} 