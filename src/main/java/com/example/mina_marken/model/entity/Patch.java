package com.example.mina_marken.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patch {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PatchType type;
}
