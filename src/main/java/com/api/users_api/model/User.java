package com.api.users_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String name;
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // <- aquí
    private String createdAt;

    private List<Address> addresses;

    public User(Integer id, String email, String name, String password, List<Address> addresses) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = generateUkTimestamp(); // ← se genera automáticamente
        this.addresses = addresses;
    }

    private String generateUkTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return ZonedDateTime.now(ZoneId.of("Europe/London")).format(formatter);
    }
}
