package com.api.users_api.controller;

import com.api.users_api.model.Address;
import com.api.users_api.model.User;
import com.api.users_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(required = false) String sortedBy) {
        if (sortedBy != null && sortedBy.trim().isEmpty()) {
            sortedBy = null;
        }
        return userService.getUsers(sortedBy);
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getAddressesByUserId(userId));
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Integer userId,
                                           @PathVariable Integer addressId,
                                           @RequestBody Address address) {
        boolean updated = userService.updateAddress(userId, addressId, address);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody Map<String, Object> updates) {
        boolean updated = userService.updateUser(userId, updates);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        boolean deleted = userService.deleteUser(userId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
