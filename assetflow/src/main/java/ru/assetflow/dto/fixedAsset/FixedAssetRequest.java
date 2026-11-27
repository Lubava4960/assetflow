package ru.assetflow.dto.fixedAsset;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FixedAssetRequest {
    @NotBlank
    @Size(max = 50)
    private String inventoryNumber;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Positive
    private Long categoryId;

    @Size(max = 20)
    private String okofCode;

    @Min(1)
    @Max(10)
    private Integer taxDepreciationGroup;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 17, fraction = 2)
    private BigDecimal initialCost;

    private LocalDate acquisitionDate;

    private LocalDate commissioningDate;

    @Positive
    private Integer accountingUsefulLifeMonths;

    @Positive
    private Integer taxUsefulLifeMonths;

    @Valid
    private AssetTechnicalInfoRequest technicalInfo;
}


