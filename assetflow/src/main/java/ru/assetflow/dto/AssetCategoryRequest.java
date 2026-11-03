package ru.assetflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class AssetCategoryRequest {
    @NotBlank
    @Size(max = 30)
    String code;

    @NotBlank
    @Size(max = 150)
    String name;

    @Size(max = 500)
    String description;

    @Positive
    Integer defaultUsefulLifeMonths;


    }