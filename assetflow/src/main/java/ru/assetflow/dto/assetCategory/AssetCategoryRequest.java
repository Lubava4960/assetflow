package ru.assetflow.dto.assetCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryRequest {
    @NotBlank
    @Size(max = 30)
    String code;

    @NotBlank
    @Size(max = 150)
    String name;

    @Size(max = 500)
    String description;




    }