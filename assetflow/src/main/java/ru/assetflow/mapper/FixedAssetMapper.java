package ru.assetflow.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.assetflow.db.assetfiow.entity.AssetCategory;
import ru.assetflow.db.assetfiow.entity.FixedAsset;
import ru.assetflow.dto.fixedAsset.FixedAssetRequest;
import ru.assetflow.dto.fixedAsset.FixedAssetResponse;


@Component
@RequiredArgsConstructor
public class FixedAssetMapper {
    private final AssetTechnicalInfoMapper technicalInfoMapper;
    public FixedAsset toEntity(FixedAssetRequest request, AssetCategory category) {
        return FixedAsset.builder()
                .inventoryNumber(request.getInventoryNumber())
                .name(request.getName())
                .category(category)
                .okofCode(request.getOkofCode())
                .taxDepreciationGroup(request.getTaxDepreciationGroup())
                .initialCost(request.getInitialCost())
                .acquisitionDate(request.getAcquisitionDate())
                .commissioningDate(request.getCommissioningDate())
                .accountingUsefulLifeMonths(
                        request.getAccountingUsefulLifeMonths()
                )
                .taxUsefulLifeMonths(
                        request.getTaxUsefulLifeMonths()
                )
                .technicalInfo(
                        technicalInfoMapper.toEntity(request.getTechnicalInfo())
                )
                .build();

    }
    public FixedAssetResponse toResponse(FixedAsset fixedAsset) {
        return FixedAssetResponse.builder()
                .id(fixedAsset.getId())
                .inventoryNumber(fixedAsset.getInventoryNumber())
                .name(fixedAsset.getName())
                .categoryId(fixedAsset.getCategory().getId())
                .categoryCode(fixedAsset.getCategory().getCode())
                .categoryName(fixedAsset.getCategory().getName())
                .okofCode(fixedAsset.getOkofCode())
                .taxDepreciationGroup(fixedAsset.getTaxDepreciationGroup())
                .initialCost(fixedAsset.getInitialCost())
                .acquisitionDate(fixedAsset.getAcquisitionDate())
                .accountingUsefulLifeMonths(fixedAsset.getAccountingUsefulLifeMonths())
                .taxUsefulLifeMonths(fixedAsset.getTaxUsefulLifeMonths())
                .technicalInfo(technicalInfoMapper.toResponse(fixedAsset.getTechnicalInfo()))
                .build();
    }

}
