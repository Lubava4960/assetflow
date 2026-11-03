package ru.assetflow.mapper;

import org.springframework.stereotype.Component;
import ru.assetflow.db.assetfiow.entity.AssetCategory;
import ru.assetflow.dto.AssetCategoryRequest;
import ru.assetflow.dto.AssetCategoryResponse;


@Component
public class AssetCategoryMapper {
    //Request DTO в Entity
    public AssetCategory toEntity(AssetCategoryRequest request) {

        return AssetCategory.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .defaultUsefulLifeMonths(
                        request.getDefaultUsefulLifeMonths()
                )
                .build();

    }

    //Entity в Response DTO
    public AssetCategoryResponse toResponse(AssetCategory category) {

        return AssetCategoryResponse.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .defaultUsefulLifeMonths(
                        category.getDefaultUsefulLifeMonths()
                )
                .active(category.isActive())
                .build();
    }
}
