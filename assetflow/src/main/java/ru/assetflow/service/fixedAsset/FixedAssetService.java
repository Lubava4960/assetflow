package ru.assetflow.service.fixedAsset;

import ru.assetflow.dto.fixedAsset.FixedAssetRequest;
import ru.assetflow.dto.fixedAsset.FixedAssetResponse;

import java.util.List;

public interface FixedAssetService {
    FixedAssetResponse getByInventoryNumber(String inventoryNumber);

    List<FixedAssetResponse> getAllByOrderByInventoryNumberAsc();

    FixedAssetResponse create(FixedAssetRequest request);

    FixedAssetResponse update(Long id, FixedAssetRequest request);
}
