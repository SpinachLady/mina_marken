package com.example.mina_marken.model.entity;

import com.example.mina_marken.model.Term;
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
public class PatchOrder {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patch_id")
    private Patch patch;

    @ManyToOne
    @JoinColumn(name = "scout_group_id")
    private ScoutGroup scoutGroup;

    @Enumerated(EnumType.STRING)
    private Term term;

    private int year;

    private boolean isArchived;

}
