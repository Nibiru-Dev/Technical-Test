package com.api.users_api.service;

import com.api.users_api.model.Address;
import com.api.users_api.model.User;
import com.api.users_api.util.Sha1Util;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private final List<User> users = new CopyOnWriteArrayList<>();



    @PostConstruct
    public void initUsers() {
        // Usuario inicial de prueba
        users.add(new User(
                1,
                "user1@mail.com",
                "user1",
                Sha1Util.sha1Hex("12345"),
                "01-01-2024 00:00:00",
                Arrays.asList(
                        new Address(1, "workaddress", "street No. 1", "UK"),
                        new Address(2, "homeaddress", "street No. 2", "AU")
                )
        ));
    }

    // Retorna los usuarios ordenados o tal cual si no se indica
    public List<User> getUsers(String sortBy) {
        Stream<User> userStream = users.stream();

        if ("email".equals(sortBy)) {
            userStream = userStream.sorted(Comparator.comparing(User::getEmail, Comparator.nullsLast(String::compareTo)));
        } else if ("id".equals(sortBy)) {
            userStream = userStream.sorted(Comparator.comparing(User::getId, Comparator.nullsLast(Integer::compareTo)));
        } else if ("name".equals(sortBy)) {
            userStream = userStream.sorted(Comparator.comparing(User::getName, Comparator.nullsLast(String::compareTo)));
        } else if ("created_at".equals(sortBy)) {
            userStream = userStream.sorted(Comparator.comparing(User::getCreatedAt, Comparator.nullsLast(String::compareTo)));
        }

        return userStream
                .map(u -> new User(
                        u.getId(),
                        u.getEmail(),
                        u.getName(),
                        null,
                        u.getCreatedAt(),
                        u.getAddresses()
                ))
                .collect(Collectors.toList());
    }


    // Devuelve direcciones por ID de usuario
    public List<Address> getAddressesByUserId(Integer userId) {
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .map(User::getAddresses)
                .orElse(Collections.emptyList());
    }

    // Actualiza una dirección específica de un usuario
    public boolean updateAddress(Integer userId, Integer addressId, Address updatedAddress) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                List<Address> addresses = user.getAddresses();
                for (int i = 0; i < addresses.size(); i++) {
                    if (addresses.get(i).getId().equals(addressId)) {
                        addresses.set(i, updatedAddress);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Crea un nuevo usuario y lo agrega a la lista
    public void createUser(User user) {
        User newUser = new User(
                user.getId(),
                user.getEmail(),
                user.getName(),
                Sha1Util.sha1Hex(user.getPassword()),
                user.getAddresses()
        );
        users.add(newUser);
    }

    // Actualiza atributos sueltos del usuario por su ID (PATCH)
    public boolean updateUser(Integer userId, Map<String, Object> updates) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                updates.forEach((k, v) -> {
                    switch (k) {
                        case "email" -> user.setEmail((String) v);
                        case "name" -> user.setName((String) v);
                        case "password" -> user.setPassword(Sha1Util.sha1Hex((String) v));
                    }
                });
                return true;
            }
        }
        return false;
    }

    // Elimina usuario por ID
    public boolean deleteUser(Integer userId) {
        return users.removeIf(u -> u.getId().equals(userId));
    }

}
