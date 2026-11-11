package ru.assetflow.dto.fixedAsset;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class FixedAssetResponse {
    private Long id;

    private String inventoryNumber;

    private String name;

    private Long categoryId;

    private String categoryCode;

    private String categoryName;

    private String okofCode;

    private Integer taxDepreciationGroup;

    private BigDecimal initialCost;

    private LocalDate acquisitionDate;

    private LocalDate commissioningDate;

    private Integer accountingUsefulLifeMonths;

    private Integer taxUsefulLifeMonths;

    private AssetTechnicalInfoResponse technicalInfo;
}
