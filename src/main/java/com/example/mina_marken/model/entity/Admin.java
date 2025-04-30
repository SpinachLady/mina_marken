package com.example.mina_marken.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String role;
}
