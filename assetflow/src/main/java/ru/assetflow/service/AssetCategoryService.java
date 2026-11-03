package ru.assetflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.assetflow.db.assetfiow.entity.AssetCategory;
import ru.assetflow.dto.AssetCategoryRequest;
import ru.assetflow.dto.AssetCategoryResponse;
import ru.assetflow.mapper.AssetCategoryMapper;
import ru.assetflow.repository.AssetCategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetCategoryService {
    private final AssetCategoryRepository repository;
    private final AssetCategoryMapper mapper;

    // Получить все активные категории
    public List<AssetCategoryResponse> getAllActive() {
        return repository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // Найти категорию по коду
    public AssetCategoryResponse getByCode(String code) {
        AssetCategory category = repository.findByCode(code)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Категория не найдена: " + code
                        )
                );

        return mapper.toResponse(category);
    }

    // Создать новую категорию
    @Transactional
    public AssetCategoryResponse create(AssetCategoryRequest request) {

        if (repository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException(
                    "Категория с таким кодом уже существует: "
                            + request.getCode()
            );
        }

        AssetCategory category = mapper.toEntity(request);

        AssetCategory savedCategory = repository.save(category);

        return mapper.toResponse(savedCategory);
    }

}
