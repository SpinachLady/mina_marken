package com.example.mina_marken.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoutGroup {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int minAge;

    private int maxAge;

}
