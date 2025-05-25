package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserWithWishlist(Long id) {
        return null;
    }

    @Override
    public User getUserWithRoles(Long id) {
        return null;
    }

    @Override
    public User getUserWithOrders(Long id) {
        return null;
    }

    @Override
    public User getUserWithReviews(Long id) {
        return null;
    }

    @Override
    public User getUserWithAllRelations(Long id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void deleteAllUsers() {

    }

}
