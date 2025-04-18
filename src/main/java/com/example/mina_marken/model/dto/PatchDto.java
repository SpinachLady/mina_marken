package com.example.mina_marken.model.dto;

import com.example.mina_marken.model.entity.PatchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchDto {

    private Long id;

    private String name;

    private String imgUrl;

    private PatchType type;

}
