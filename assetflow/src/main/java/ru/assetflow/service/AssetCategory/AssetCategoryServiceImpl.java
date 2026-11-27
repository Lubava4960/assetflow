package ru.assetflow.service.AssetCategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.assetflow.db.assetfiow.entity.AssetCategory;
import ru.assetflow.dto.assetCategory.AssetCategoryRequest;
import ru.assetflow.dto.assetCategory.AssetCategoryResponse;
import ru.assetflow.dto.assetCategory.AssetCategoryUpdateRequest;
import ru.assetflow.mapper.AssetCategoryMapper;
import ru.assetflow.repository.AssetCategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetCategoryServiceImpl implements AssetCategoryService {
    private final AssetCategoryRepository repository;
    private final AssetCategoryMapper mapper;

    @Override
    public List<AssetCategoryResponse> getAllActive() {
        return repository.findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AssetCategoryResponse getByCode(String code) {
        AssetCategory category = repository.findByCode(code)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Категория не найдена: " + code
                        )
                );

        return mapper.toResponse(category);
    }

    @Transactional
    @Override
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

    @Transactional
    @Override
    public AssetCategoryResponse update(
            Long id,
            AssetCategoryUpdateRequest request) {

        AssetCategory category = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Категория не найдена: " + id
                        )
                );

        category.updateDetails(
                request.getName().trim(),
                request.getDescription()
        );

        return mapper.toResponse(category);
    }

    @Transactional
    @Override
    public void deactivate(Long id) {

        AssetCategory category = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Категория не найдена: " + id
                        )
                );

        category.deactivate();
    }

}
