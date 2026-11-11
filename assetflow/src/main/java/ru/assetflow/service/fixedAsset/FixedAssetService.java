package ru.assetflow.service.fixedAsset;

import org.springframework.transaction.annotation.Transactional;
import ru.assetflow.db.assetfiow.entity.FixedAsset;
import ru.assetflow.dto.fixedAsset.FixedAssetRequest;
import ru.assetflow.dto.fixedAsset.FixedAssetResponse;

import java.util.List;

public interface FixedAssetService {
    FixedAssetResponse getByInventoryNumber(String inventoryNumber);

    List<FixedAssetResponse> getAllByOrderByInventoryNumberAsc();

    @Transactional
    FixedAssetResponse create(FixedAssetRequest request);
}
