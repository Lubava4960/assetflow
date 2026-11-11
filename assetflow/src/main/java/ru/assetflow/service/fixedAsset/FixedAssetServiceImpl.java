package ru.assetflow.service.fixedAsset;

import lombok.RequiredArgsConstructor;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.assetflow.db.assetfiow.entity.AssetCategory;
import ru.assetflow.db.assetfiow.entity.FixedAsset;
import ru.assetflow.dto.fixedAsset.FixedAssetRequest;
import ru.assetflow.dto.fixedAsset.FixedAssetResponse;
import ru.assetflow.mapper.FixedAssetMapper;
import ru.assetflow.repository.AssetCategoryRepository;
import ru.assetflow.repository.FixedAssetRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FixedAssetServiceImpl implements FixedAssetService {
    private final FixedAssetRepository fixedAssetRepository;
    private final FixedAssetMapper mapper;
    private final AssetCategoryRepository categoryRepository;

    @Override
    public FixedAssetResponse getByInventoryNumber(String inventoryNumber) {
        FixedAsset fixedAsset = fixedAssetRepository
                .findByInventoryNumber(inventoryNumber)
                .orElseThrow(() -> new IllegalArgumentException("Основное средство не найдено: " + inventoryNumber));

        return mapper.toResponse(fixedAsset);
    }
   @Override
   public List<FixedAssetResponse> getAllByOrderByInventoryNumberAsc(){
       return fixedAssetRepository.findAllByOrderByInventoryNumberAsc()
               .stream()
               .map(mapper::toResponse)
               .toList();

    }
    @Transactional
    @Override
    public FixedAssetResponse create(FixedAssetRequest request) {

        if (fixedAssetRepository.existsByInventoryNumber(
                request.getInventoryNumber())) {
            throw new IllegalArgumentException(
                    "Такой инвентарный номер уже существует"
            );
        }

        AssetCategory category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new NoSuchElementException("Категория не найдена")
                );

        FixedAsset fixedAsset = mapper.toEntity(request, category);

        FixedAsset savedAsset = fixedAssetRepository.save(fixedAsset);

        return mapper.toResponse(savedAsset);
    }
}
