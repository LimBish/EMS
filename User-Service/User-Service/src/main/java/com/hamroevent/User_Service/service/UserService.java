package com.hamroevent.User_Service.service;

import java.util.List;
import java.util.Optional;

import com.hamroevent.User_Service.dto.UserRoleDTO;
import com.hamroevent.User_Service.entity.User;
import com.hamroevent.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a single user by ID
        public User getUserById(Long id) {
            Optional<User> isUserPresent = userRepository.findById(id);
            if (!isUserPresent.isPresent()) {
                throw new RuntimeException("User not found");
            }

            return isUserPresent.get();
        }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update existing user details
    public User updateUser(Long id, User userDetails) {

        User existingUser =getUserById(id);

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
//        existingUser.setRole(userDetails.getRole());

        return userRepository.save(existingUser);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String updateRole(UserRoleDTO roleDTO) {

        Optional<User> user = userRepository.findById(roleDTO.getId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User existingUser = user.get();
        existingUser.setRole(roleDTO.getRole());

        userRepository.save(existingUser);

        return "Role Updated Successfully.";
    }
}

