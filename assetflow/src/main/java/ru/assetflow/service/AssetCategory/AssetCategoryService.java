package ru.assetflow.service.AssetCategory;

import ru.assetflow.dto.assetCategory.AssetCategoryRequest;
import ru.assetflow.dto.assetCategory.AssetCategoryResponse;
import ru.assetflow.dto.assetCategory.AssetCategoryUpdateRequest;

import java.util.List;

public interface AssetCategoryService {
    // Получить все активные категории
    List<AssetCategoryResponse> getAllActive();

    // Найти категорию по коду
    AssetCategoryResponse getByCode(String code);

    // Создать новую категорию
    AssetCategoryResponse create(AssetCategoryRequest request);

    // Изменить категорию
    AssetCategoryResponse update(
            Long id,
            AssetCategoryUpdateRequest request
    );

    // Деактивировать категорию
    void deactivate(Long id);
}
