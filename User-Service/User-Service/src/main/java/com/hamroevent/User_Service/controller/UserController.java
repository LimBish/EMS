package com.hamroevent.User_Service.controller;

import java.util.List;

import com.hamroevent.User_Service.dto.UserRoleDTO;
import com.hamroevent.User_Service.entity.User;
import com.hamroevent.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping({"", "/"})
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Update existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Create new user
    @PostMapping(("/update/role"))
    public String  updateUserRole(@RequestBody UserRoleDTO roleDTO) {
        return userService.updateRole(roleDTO);
    }

}
