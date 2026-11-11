package ru.assetflow.dto.assetCategory;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean active;
}
